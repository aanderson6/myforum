package com.myforum.comments;

import com.myforum.login.repositories.UserRepo;
import com.myforum.comments.entities.*;
import com.myforum.comments.repositories.*;
import com.myforum.comments.dtos.*;

import com.myforum.login.entities.UserEntity;

import com.myforum.subforum.entities.SubforumEntity;
import com.myforum.subforum.repositories.SubforumRepo;
import com.myforum.posts.entities.PostEntity;
import com.myforum.posts.repositories.PostRepo;

import com.myforum.messaging.entities.FilterEntity;
import com.myforum.messaging.repositories.FilterRepo;

import com.myforum.messaging.entities.SettingsEntity;
import com.myforum.messaging.repositories.SettingsRepo;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

public class CommentService implements CommentInterface {

  public CommentService() {  }

  UserRepo userRepo = new UserRepo();
  SubforumRepo subforumRepo = new SubforumRepo();
  PostRepo postRepo = new PostRepo();
  CommentRepo commentRepo = new CommentRepo();
  CommentKarmaRepo commentKarmaRepo = new CommentKarmaRepo();
  FilterRepo filterRepo = new FilterRepo();
  SettingsRepo settingsRepo = new SettingsRepo();

// parentCommentId == null if this is a top level comment in post
  public int createComment(String username, int postId, String subforumName, String content, Integer parentCommentId) throws IllegalArgumentException {

    if(username == null || subforumName == null || content == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    PostEntity post = postRepo.getById(postId);

    if(post == null) {
      throw new IllegalArgumentException("No such post");
    }

    CommentEntity parentComment;
    if(parentCommentId != null) {
      parentComment = commentRepo.getById(parentCommentId);
    } else {
      parentComment = null;
    }

    int commentDepth = 1;
    if(!(parentComment == null)) {
      parentComment.setHasChildren(true);
      commentRepo.update(parentComment);
      commentDepth = parentComment.getCommentDepth() + 1;
    }

    CommentEntity comment = new CommentEntity(Instant.now(), user, post, subforum, 0, content, parentComment, commentDepth, false);

    comment = commentRepo.add(comment);

    upvoteComment(comment.getId(), user.getUsername());

    return comment.getId();

  }

  public void updateCommentContent(int id, String content, String username) throws IllegalArgumentException, IllegalStateException {

    if(content == null || username == null) {
      throw new NullPointerException();
    }

    CommentEntity comment = commentRepo.getById(id);
    if(comment == null) {
      throw new IllegalArgumentException("No such comment");
    }
    if(!comment.getUser().getUsername().equals(username)) {
      throw new IllegalStateException("Not Authorized to update comment");
    }

    comment.setContent(content);
    commentRepo.update(comment);

  }

  public void upvoteComment(int id, String username) throws IllegalArgumentException {

    if(username == null) {
      throw new NullPointerException();
    }

    CommentEntity comment = commentRepo.getById(id);
    if(comment == null) {
      throw new IllegalArgumentException("No such comment");
    }
    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    if(commentKarmaRepo.hasVoted(user, comment)) {
      CommentKarmaEntity commentKarma = commentKarmaRepo.getByUserAndComment(user, comment);
      if(!commentKarma.getUpOrDown()) {

        commentKarma.setUpOrDown(true);
        commentKarma.setVotedDate(Instant.now());
        commentKarmaRepo.update(commentKarma);

        UserEntity commentUser = comment.getUser();
        commentUser.setKarma(commentUser.getKarma() + 2);
        userRepo.update(commentUser);

        comment.setKarma(comment.getKarma() + 2);
        commentRepo.update(comment);

      }
    } else {

      CommentKarmaEntity commentKarma = new CommentKarmaEntity(Instant.now(), user, comment, true);
      commentKarmaRepo.add(commentKarma);

      UserEntity commentUser = comment.getUser();
      commentUser.setKarma(commentUser.getKarma() + 1);
      userRepo.update(commentUser);

      comment.setKarma(comment.getKarma() + 1);
      commentRepo.update(comment);

    }

  }

  public void downvoteComment(int id, String username) throws IllegalArgumentException {

    if(username == null) {
      throw new NullPointerException();
    }

    CommentEntity comment = commentRepo.getById(id);
    if(comment == null) {
      throw new IllegalArgumentException("No such comment");
    }
    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    if(commentKarmaRepo.hasVoted(user, comment)) {
      CommentKarmaEntity commentKarma = commentKarmaRepo.getByUserAndComment(user, comment);
      if(commentKarma.getUpOrDown()) {

        commentKarma.setUpOrDown(true);
        commentKarma.setVotedDate(Instant.now());
        commentKarmaRepo.update(commentKarma);

        UserEntity commentUser = comment.getUser();
        commentUser.setKarma(commentUser.getKarma() - 2);
        userRepo.update(commentUser);

        comment.setKarma(comment.getKarma() - 2);
        commentRepo.update(comment);

      }
    } else {

      CommentKarmaEntity commentKarma = new CommentKarmaEntity(Instant.now(), user, comment, true);
      commentKarmaRepo.add(commentKarma);

      UserEntity commentUser = comment.getUser();
      commentUser.setKarma(commentUser.getKarma() - 1);
      userRepo.update(commentUser);

      comment.setKarma(comment.getKarma() - 1);
      commentRepo.update(comment);

    }

  }

  public void removeVoteOnComment(int id, String username) throws IllegalArgumentException {

    if(username == null) {
      throw new NullPointerException();
    }

    CommentEntity comment = commentRepo.getById(id);
    if(comment == null) {
      throw new IllegalArgumentException("No such comment");
    }
    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    if(commentKarmaRepo.hasVoted(user, comment)) {
      CommentKarmaEntity commentKarma = commentKarmaRepo.getByUserAndComment(user, comment);

      if(commentKarma.getUpOrDown()) {

        UserEntity commentUser = comment.getUser();
        commentUser.setKarma(commentUser.getKarma() - 1);
        userRepo.update(commentUser);

        commentKarmaRepo.remove(commentKarma);

        comment.setKarma(comment.getKarma() - 1);
        commentRepo.update(comment);

      } else {

        UserEntity commentUser = comment.getUser();
        commentUser.setKarma(commentUser.getKarma() + 1);
        userRepo.update(commentUser);

        commentKarmaRepo.remove(commentKarma);

        comment.setKarma(comment.getKarma() + 1);
        commentRepo.update(comment);

      }
    }

  }

  public void removeUserFromComment(int id, String username) throws IllegalArgumentException, IllegalStateException {

    if(username == null) {
      throw new NullPointerException();
    }

    CommentEntity comment = commentRepo.getById(id);
    if(comment == null) {
      throw new IllegalArgumentException("No such comment");
    }
    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    if(!comment.getUser().getUsername().equals(username)) {
      throw new IllegalStateException("Not Authorized to Delete comment");
    }

    List<CommentKarmaEntity> commentKarmaList = commentKarmaRepo.getByComment(comment);
    user.setKarma(user.getKarma() - 1);

    for(CommentKarmaEntity commentKarma : commentKarmaList) {
      if(commentKarma.getUpOrDown()) {
        user.setKarma(user.getKarma() - 1);
      } else {
        user.setKarma(user.getKarma() + 1);
      }
    }

    comment.setUser(null);
    commentRepo.update(comment);

  }

  public CommentDTO getById(int id) throws IllegalArgumentException {

    CommentEntity commentEnt = commentRepo.getById(id);
    if(commentEnt == null) {
      throw new IllegalArgumentException("No such comment");
    }

    String commentUser = "[deleted]";
    if(commentEnt.getUser() != null) {
      commentUser = commentEnt.getUser().getUsername();
    }

    String subforumName = "[deleted]";
    if(commentEnt.getSubforum() != null) {
      subforumName = commentEnt.getSubforum().getName();
    }

    Integer parentCommentId = null;
    if(commentEnt.getParentComment() != null) {
      parentCommentId = commentEnt.getParentComment().getId();
    }

    CommentDTO comment = new CommentDTO(commentEnt.getId(), commentEnt.getCommentDate(), commentUser, commentEnt.getPost().getId(), subforumName, commentEnt.getKarma(), commentEnt.getContent(), parentCommentId, commentEnt.getCommentDepth(), commentEnt.getHasChildren());
    return comment;
  }

  public List<CommentDTO> getByUserOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException {
    if(username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    List<CommentEntity> commentEntList = new ArrayList<>();

    int count = commentRepo.getCountByUser(user);
    if(start + returnCount > count) {
      throw new IllegalArgumentException("Out of Range");
    }

    commentEntList = commentRepo.getByUserOrderedPaginated(user, start, returnCount);

    List<CommentDTO> commentList = new ArrayList<>();

    for(CommentEntity commentEnt : commentEntList) {
      String commentUser = "[deleted]";
      if(commentEnt.getUser() != null) {
        commentUser = commentEnt.getUser().getUsername();
      }

      String subforumName = "[deleted]";
      if(commentEnt.getSubforum() != null) {
        subforumName = commentEnt.getSubforum().getName();
      }

      Integer parentCommentId = null;
      if(commentEnt.getParentComment() != null) {
        parentCommentId = commentEnt.getParentComment().getId();
      }

      commentList.add(new CommentDTO(commentEnt.getId(), commentEnt.getCommentDate(), commentUser, commentEnt.getPost().getId(), subforumName, commentEnt.getKarma(), commentEnt.getContent(), parentCommentId, commentEnt.getCommentDepth(), commentEnt.getHasChildren()));

    }

    return commentList;

  }

  public List<CommentDTO> getByPost(int postId, String requestingUsername, int start, int returnCount, int depth) throws IllegalArgumentException {

    List<CommentEntity> commentEntList = new ArrayList<>();
    PostEntity post = postRepo.getById(postId);

    if(requestingUsername != null) {
      UserEntity user = userRepo.getByUsername(requestingUsername);
      if(user == null) {
        throw new IllegalArgumentException("No such user");
      }
      SettingsEntity settings = settingsRepo.getByUser(user);

      if (settings.getAllOrWhitelist()) {
        // whitelisted only
        List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        int count = commentRepo.getCountByPostFiltered(post, userList, settings.getAllOrWhitelist());
        if(start + returnCount > count) {
          throw new IllegalArgumentException("Out of Range");
        }

        commentEntList = commentRepo.getByPostOrderedPaginatedFiltered(post, start, returnCount, userList, settings.getAllOrWhitelist());

        List<CommentEntity> tempParentList = new ArrayList<>();
        tempParentList.addAll(commentEntList);

        for(int i = 1; i < depth; i++) {
          tempParentList = commentRepo.getByCommentOrderedFiltered(tempParentList, userList, settings.getAllOrWhitelist());

          commentEntList.addAll(tempParentList);
        }

      } else {
        // all but blocked
        List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        int count = commentRepo.getCountByPostFiltered(post, userList, settings.getAllOrWhitelist());
        if(start + returnCount > count) {
          throw new IllegalArgumentException("Out of Range");
        }

        commentEntList = commentRepo.getByPostOrderedPaginatedFiltered(post, start, returnCount, userList, settings.getAllOrWhitelist());

        List<CommentEntity> tempParentList = new ArrayList<>();
        tempParentList.addAll(commentEntList);

        for(int i = 1; i < depth; i++) {
          tempParentList = commentRepo.getByCommentOrderedFiltered(tempParentList, userList, settings.getAllOrWhitelist());

          commentEntList.addAll(tempParentList);
        }

      }
    } else {
      int count = commentRepo.getCountByPost(post);
      if(start + returnCount > count) {
        throw new IllegalArgumentException("Out of Range");
      }

      commentEntList = commentRepo.getByPostOrderedPaginated(post, start, returnCount);

      List<CommentEntity> tempParentList = new ArrayList<>();
      tempParentList.addAll(commentEntList);

      for(int i = 1; i < depth; i++) {
        tempParentList = commentRepo.getByComment(tempParentList);

        commentEntList.addAll(tempParentList);
      }
    }

    List<CommentDTO> commentList = new ArrayList<>();

    for(CommentEntity commentEnt : commentEntList) {
      String commentUser = "[deleted]";
      if(commentEnt.getUser() != null) {
        commentUser = commentEnt.getUser().getUsername();
      }

      String subforumName = "[deleted]";
      if(commentEnt.getSubforum() != null) {
        subforumName = commentEnt.getSubforum().getName();
      }

      Integer parentCommentId = null;
      if(commentEnt.getParentComment() != null) {
        parentCommentId = commentEnt.getParentComment().getId();
      }

      commentList.add(new CommentDTO(commentEnt.getId(), commentEnt.getCommentDate(), commentUser, commentEnt.getPost().getId(), subforumName, commentEnt.getKarma(), commentEnt.getContent(), parentCommentId, commentEnt.getCommentDepth(), commentEnt.getHasChildren()));

    }

    return commentList;
  }

  public List<CommentDTO> getChildrenOf(int commentId, String requestingUsername, int start, int returnCount, int depth) throws IllegalArgumentException {
    List<CommentEntity> commentEntList = new ArrayList<>();
    List<CommentEntity> parentCommentList = new ArrayList<>();
    parentCommentList.add(commentRepo.getById(commentId));

    if(requestingUsername != null) {
      UserEntity user = userRepo.getByUsername(requestingUsername);
      if(user == null) {
        throw new IllegalArgumentException("No such user");
      }
      SettingsEntity settings = settingsRepo.getByUser(user);

      if (settings.getAllOrWhitelist()) {
        // whitelisted only
        List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        int count = commentRepo.getCountByCommentFiltered(parentCommentList, userList, settings.getAllOrWhitelist());
        if(start + returnCount > count) {
          throw new IllegalArgumentException("Out of Range");
        }

        commentEntList = commentRepo.getByCommentOrderedPaginatedFiltered(parentCommentList, start, returnCount, userList, settings.getAllOrWhitelist());

        List<CommentEntity> tempParentList = new ArrayList<>();
        tempParentList.addAll(commentEntList);

        for(int i = 1; i < depth; i++) {
          tempParentList = commentRepo.getByCommentOrderedFiltered(tempParentList, userList, settings.getAllOrWhitelist());

          commentEntList.addAll(tempParentList);
        }

      } else {
        // all but blocked
        List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        int count = commentRepo.getCountByCommentFiltered(parentCommentList, userList, settings.getAllOrWhitelist());
        if(start + returnCount > count) {
          throw new IllegalArgumentException("Out of Range");
        }

        commentEntList = commentRepo.getByCommentOrderedPaginatedFiltered(parentCommentList, start, returnCount, userList, settings.getAllOrWhitelist());

        List<CommentEntity> tempParentList = new ArrayList<>();
        tempParentList.addAll(commentEntList);

        for(int i = 1; i < depth; i++) {
          tempParentList = commentRepo.getByCommentOrderedFiltered(tempParentList, userList, settings.getAllOrWhitelist());

          commentEntList.addAll(tempParentList);
        }

      }
    } else {
      int count = commentRepo.getCountByComment(parentCommentList);
      if(start + returnCount > count) {
        throw new IllegalArgumentException("Out of Range");
      }

      commentEntList = commentRepo.getByCommentOrderedPaginated(parentCommentList, start, returnCount);

      List<CommentEntity> tempParentList = new ArrayList<>();
      tempParentList.addAll(commentEntList);

      for(int i = 1; i < depth; i++) {
        tempParentList = commentRepo.getByComment(tempParentList);

        commentEntList.addAll(tempParentList);
      }
    }

    List<CommentDTO> commentList = new ArrayList<>();

    for(CommentEntity commentEnt : commentEntList) {
      String commentUser = "[deleted]";
      if(commentEnt.getUser() != null) {
        commentUser = commentEnt.getUser().getUsername();
      }

      String subforumName = "[deleted]";
      if(commentEnt.getSubforum() != null) {
        subforumName = commentEnt.getSubforum().getName();
      }

      Integer parentCommentId = null;
      if(commentEnt.getParentComment() != null) {
        parentCommentId = commentEnt.getParentComment().getId();
      }

      commentList.add(new CommentDTO(commentEnt.getId(), commentEnt.getCommentDate(), commentUser, commentEnt.getPost().getId(), subforumName, commentEnt.getKarma(), commentEnt.getContent(), parentCommentId, commentEnt.getCommentDepth(), commentEnt.getHasChildren()));

    }

    return commentList;
  }


  public int getCountByUser(String username) throws IllegalArgumentException {
    if(username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    int count = commentRepo.getCountByUser(user);

    return count;
  }

  public int getCountByPostFiltered(int postId, String requestingUsername) throws IllegalArgumentException {

    PostEntity post = postRepo.getById(postId);
    int count = 0;

    if(requestingUsername != null) {
      UserEntity user = userRepo.getByUsername(requestingUsername);
      if(user == null) {
        throw new IllegalArgumentException("No such user");
      }
      SettingsEntity settings = settingsRepo.getByUser(user);

      if (settings.getAllOrWhitelist()) {
        // whitelisted only
        List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        count = commentRepo.getCountByPostFiltered(post, userList, settings.getAllOrWhitelist());

      } else {
        // all but blocked
        List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        count = commentRepo.getCountByPostFiltered(post, userList, settings.getAllOrWhitelist());

      }
    } else {

      count = commentRepo.getCountByPost(post);
    }

    return count;
  }

  public int getCountChildrenOf(int commentId, String requestingUsername) throws IllegalArgumentException {

    List<CommentEntity> parentCommentList = new ArrayList<>();
    parentCommentList.add(commentRepo.getById(commentId));
    int count = 0;

    if(requestingUsername != null) {
      UserEntity user = userRepo.getByUsername(requestingUsername);
      if(user == null) {
        throw new IllegalArgumentException("No such user");
      }
      SettingsEntity settings = settingsRepo.getByUser(user);

      if (settings.getAllOrWhitelist()) {
        // whitelisted only
        List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        count = commentRepo.getCountByCommentFiltered(parentCommentList, userList, settings.getAllOrWhitelist());

      } else {
        // all but blocked
        List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        count = commentRepo.getCountByCommentFiltered(parentCommentList, userList, settings.getAllOrWhitelist());

      }
    } else {
      count = commentRepo.getCountByComment(parentCommentList);

    }

    return count;
  }

}

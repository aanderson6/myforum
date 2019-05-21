package com.myforum.posts;

import com.myforum.posts.entities.*;
import com.myforum.posts.repositories.*;
import com.myforum.posts.dtos.*;
import com.myforum.login.repositories.UserRepo;
import com.myforum.login.entities.UserEntity;
import com.myforum.subforum.entities.SubforumEntity;
import com.myforum.subforum.repositories.SubforumRepo;
import com.myforum.messaging.entities.FilterEntity;
import com.myforum.messaging.repositories.FilterRepo;
import com.myforum.messaging.entities.SettingsEntity;
import com.myforum.messaging.repositories.SettingsRepo;
import com.myforum.subforum.entities.SubscriptionEntity;
import com.myforum.subforum.repositories.SubscriptionRepo;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

import com.myforum.util.Validator;

public class PostService implements PostInterface {

  public PostService() {  }

  UserRepo userRepo = new UserRepo();
  SubforumRepo subforumRepo = new SubforumRepo();
  PostRepo postRepo = new PostRepo();
  PostKarmaRepo postKarmaRepo = new PostKarmaRepo();
  FilterRepo filterRepo = new FilterRepo();
  SettingsRepo settingsRepo = new SettingsRepo();
  SubscriptionRepo subscriptionRepo = new SubscriptionRepo();

  public int createPost(String username, String subforumName, String title, String content) throws IllegalArgumentException {

    if(username == null || subforumName == null || title == null || content == null) {
      throw new NullPointerException();
    }

    if(!Validator.validateTitle(title)) {
      throw new IllegalArgumentException("Invalid Title");
    } else if(!Validator.validateContent(content)) {
      throw new IllegalArgumentException("Invalid Content");
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No Such Subforum");
    }

    PostEntity post = new PostEntity(Instant.now(), user, subforum, 0, title, content);

    post = postRepo.add(post);

    upvotePost(post.getId(), user.getUsername());

    return post.getId();

  }

  public void updatePostTitle(int id, String title, String username) throws IllegalArgumentException, IllegalStateException {

    if(title == null || username == null) {
      throw new NullPointerException();
    }

    if(!Validator.validateTitle(title)) {
      throw new IllegalArgumentException("Invalid Title");
    }

    PostEntity post = postRepo.getById(id);
    if(post == null) {
      throw new IllegalArgumentException("No Such Post");
    }
    if(!post.getUser().getUsername().equals(username)) {
      throw new IllegalStateException("Not Authorized to Update");
    }

    post.setTitle(title);
    postRepo.update(post);

  }

  public void updatePostContent(int id, String content, String username) throws IllegalArgumentException, IllegalStateException {

    if(content == null || username == null) {
      throw new NullPointerException();
    }

    if(!Validator.validateContent(content)) {
      throw new IllegalArgumentException("Invalid Content");
    }

    PostEntity post = postRepo.getById(id);
    if(post == null) {
      throw new IllegalArgumentException("No Such Post");
    }
    if(!post.getUser().getUsername().equals(username)) {
      throw new IllegalStateException("Not Authorized to Update");
    }

    post.setContent(content);
    postRepo.update(post);

  }

  public void upvotePost(int id, String username) throws IllegalArgumentException {

    if(username == null) {
      throw new NullPointerException();
    }

    PostEntity post = postRepo.getById(id);
    if(post == null) {
      throw new IllegalArgumentException("No Such Post");
    }
    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    if(postKarmaRepo.hasVoted(user, post)) {
      PostKarmaEntity postKarma = postKarmaRepo.getByUserAndPost(user, post);
      if(!postKarma.getUpOrDown()) {

        postKarma.setUpOrDown(true);
        postKarma.setVotedDate(Instant.now());
        postKarmaRepo.update(postKarma);

        UserEntity postUser = post.getUser();
        if(postUser != null) {
          postUser.setKarma(postUser.getKarma() + 2);
          userRepo.update(postUser);
        }

        post.setKarma(post.getKarma() + 2);
        postRepo.update(post);

      }
    } else {

      PostKarmaEntity postKarma = new PostKarmaEntity(Instant.now(), user, post, true);
      postKarmaRepo.add(postKarma);

      UserEntity postUser = post.getUser();
      if(postUser != null) {
        postUser.setKarma(postUser.getKarma() + 1);
        userRepo.update(postUser);
      }

      post.setKarma(post.getKarma() + 1);
      postRepo.update(post);

    }

  }

  public void downvotePost(int id, String username) {

    if(username == null) {
      throw new NullPointerException();
    }

    PostEntity post = postRepo.getById(id);
    if(post == null) {
      throw new IllegalArgumentException("No Such Post");
    }
    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    if(postKarmaRepo.hasVoted(user, post)) {
      PostKarmaEntity postKarma = postKarmaRepo.getByUserAndPost(user, post);
      if(postKarma.getUpOrDown()) {

        postKarma.setUpOrDown(false);
        postKarma.setVotedDate(Instant.now());
        postKarmaRepo.update(postKarma);

        UserEntity postUser = post.getUser();
        if(postUser != null) {
          postUser.setKarma(postUser.getKarma() - 2);
          userRepo.update(postUser);
        }

        post.setKarma(post.getKarma() - 2);
        postRepo.update(post);

      }
    } else {

      PostKarmaEntity postKarma = new PostKarmaEntity(Instant.now(), user, post, false);
      postKarmaRepo.add(postKarma);

      UserEntity postUser = post.getUser();
      if(postUser != null) {
        postUser.setKarma(postUser.getKarma() - 1);
        userRepo.update(postUser);
      }

      post.setKarma(post.getKarma() - 1);
      postRepo.update(post);

    }

  }

  public void removeVoteOnPost(int id, String username) {

    if(username == null) {
      throw new NullPointerException();
    }

    PostEntity post = postRepo.getById(id);
    if(post == null) {
      throw new IllegalArgumentException("No Such Post");
    }
    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    if(postKarmaRepo.hasVoted(user, post)) {
      PostKarmaEntity postKarma = postKarmaRepo.getByUserAndPost(user, post);

      if(postKarma.getUpOrDown()) {

        UserEntity postUser = post.getUser();
        if(postUser != null) {
          postUser.setKarma(postUser.getKarma() - 1);
          userRepo.update(postUser);
        }

        postKarmaRepo.remove(postKarma);

        post.setKarma(post.getKarma() - 1);
        postRepo.update(post);

      } else {

        UserEntity postUser = post.getUser();
        if(postUser != null) {
          postUser.setKarma(postUser.getKarma() + 1);
          userRepo.update(postUser);
        }

        postKarmaRepo.remove(postKarma);

        post.setKarma(post.getKarma() + 1);
        postRepo.update(post);

      }
    }

  }

  public void removeUserFromPost(int id, String username) throws IllegalArgumentException, IllegalStateException {

    if(username == null) {
      throw new NullPointerException();
    }

    PostEntity post = postRepo.getById(id);
    if(post == null) {
      throw new IllegalArgumentException("No Such Post");
    }
    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    if(!post.getUser().getUsername().equals(username)) {
      throw new IllegalStateException("Not Authorized to Delete");
    }

    List<PostKarmaEntity> postKarmaList = postKarmaRepo.getByPost(post);

    for(PostKarmaEntity postKarma : postKarmaList) {
      if(!(postKarma.getUser() == user)) {
        if(postKarma.getUpOrDown()) {
          user.setKarma(user.getKarma() - 1);
        } else {
          user.setKarma(user.getKarma() + 1);
        }
      }
    }

    post.setUser(null);
    postRepo.update(post);

    removeVoteOnPost(id, username);


  }

  public List<PostDTO> getBySubforumOrderedPaginated(String subforumName, int start, int returnCount, String byUsername) throws IllegalArgumentException {

    if(subforumName == null) {
      throw new NullPointerException();
    }

    List<PostEntity> postList = new ArrayList<>();

    if(byUsername != null) {
      UserEntity user = userRepo.getByUsername(byUsername);
      if(user == null) {
        throw new IllegalArgumentException("No Such User");
      }
      SubforumEntity subforum = subforumRepo.getByName(subforumName);
      if(subforum == null) {
        throw new IllegalArgumentException("No Such Subforum");
      }
      SettingsEntity settings = settingsRepo.getByUser(user);

      if (settings.getAllOrWhitelist()) {
        // whitelisted only
        List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        int count = postRepo.getCountBySubforumOrderedFiltered(subforum, userList, settings.getAllOrWhitelist());
        if(start + returnCount > count) {
          throw new IllegalArgumentException("Out of Range");
        }

        postList = postRepo.getBySubforumOrderedFiltered(start, returnCount, subforum, userList, settings.getAllOrWhitelist());

      } else {
        // all but blocked
        List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        int count = postRepo.getCountBySubforumOrderedFiltered(subforum, userList, settings.getAllOrWhitelist());
        if(start + returnCount > count) {
          throw new IllegalArgumentException("Out of Range");
        }

        postList = postRepo.getBySubforumOrderedFiltered(start, returnCount, subforum, userList, settings.getAllOrWhitelist());

      }
    } else {
      SubforumEntity subforum = subforumRepo.getByName(subforumName);
      if(subforum == null) {
        throw new IllegalArgumentException("No Such Subforum");
      }

      int count = postRepo.getCountBySubforumOrdered(subforum);
      if(start + returnCount > count) {
        throw new IllegalArgumentException("Out of Range");
      }

      postList = postRepo.getBySubforumOrdered(start, returnCount, subforum);


    }

    List<PostDTO> postDTOList = new ArrayList<PostDTO>();

    for(PostEntity post : postList) {
      String username2 = "[deleted]";
      if(post.getUser() != null) {
        username2 = post.getUser().getUsername();
      }
      String subforumName2 = "[deleted]";
      if(post.getSubforum() != null) {
        subforumName2 = post.getSubforum().getName();
      }
      postDTOList.add(new PostDTO(post.getId(), post.getPostedDate(), username2, subforumName2, post.getKarma(), post.getTitle(), post.getContent()));
    }

    return postDTOList;

  }

  public List<PostDTO> getByUserOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException {

    if(username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    List<PostEntity> postList = new ArrayList<>();

    int count = postRepo.getCountByUserOrdered(user);
    if(start + returnCount > count) {
      throw new IllegalArgumentException("Out of Range");
    }

    postList = postRepo.getByUserOrdered(user, start, returnCount);

    List<PostDTO> postDTOList = new ArrayList<PostDTO>();

    for(PostEntity post : postList) {
      String username2 = "[deleted]";
      if(post.getUser() != null) {
        username2 = post.getUser().getUsername();
      }
      String subforumName2 = "[deleted]";
      if(post.getSubforum() != null) {
        subforumName2 = post.getSubforum().getName();
      }
      postDTOList.add(new PostDTO(post.getId(), post.getPostedDate(), username2, subforumName2, post.getKarma(), post.getTitle(), post.getContent()));
    }

    return postDTOList;

  }

  public List<PostDTO> getForUserSubscribedOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException {

    List<PostEntity> postList = new ArrayList<>();

    if(username != null) {
      UserEntity user = userRepo.getByUsername(username);
      if(user == null) {
        throw new IllegalArgumentException("No Such User");
      }

      //  get subscribed list by user, then get from db by subscribed list subforum filter, ordered by date, paginated

      List<SubscriptionEntity> subList = subscriptionRepo.getByUser(user);
      List<SubforumEntity> subforumList = new ArrayList<>();

      for(SubscriptionEntity subscription : subList) {
        subforumList.add(subscription.getSubforum());
      }

      SettingsEntity settings = settingsRepo.getByUser(user);

      if (settings.getAllOrWhitelist()) {
        // whitelisted only
        List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        int count = postRepo.getCountBySubforumListFiltered(subforumList, userList, settings.getAllOrWhitelist());
        if(start + returnCount > count) {
          throw new IllegalArgumentException("Out of Range");
        }

        postList = postRepo.getBySubforumListFilteredOrdered(subforumList, start, returnCount, userList, settings.getAllOrWhitelist());

      } else {
        // all but blocked
        List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        int count = postRepo.getCountBySubforumListFiltered(subforumList, userList, settings.getAllOrWhitelist());
        if(start + returnCount > count) {
          throw new IllegalArgumentException("Out of Range");
        }

        postList = postRepo.getBySubforumListFilteredOrdered(subforumList, start, returnCount, userList, settings.getAllOrWhitelist());

      }

    } else {
      int count = postRepo.getCountAll();
      if(start + returnCount > count) {
        throw new IllegalArgumentException("Out of Range");
      }

      postList = postRepo.getAllPaginated(start, returnCount);
    }

    List<PostDTO> postDTOList = new ArrayList<PostDTO>();

    for(PostEntity post : postList) {
      String username2 = "[deleted]";
      if(post.getUser() != null) {
        username2 = post.getUser().getUsername();
      }
      String subforumName2 = "[deleted]";
      if(post.getSubforum() != null) {
        subforumName2 = post.getSubforum().getName();
      }
      postDTOList.add(new PostDTO(post.getId(), post.getPostedDate(), username2, subforumName2, post.getKarma(), post.getTitle(), post.getContent()));
    }

    return postDTOList;

  }

  public PostDTO getById(int id) throws IllegalArgumentException {

    PostEntity postEnt = postRepo.getById(id);
    if(postEnt == null) {
      throw new IllegalArgumentException("No Such Post");
    }

    String username2 = "[deleted]";
    if(postEnt.getUser() != null) {
      username2 = postEnt.getUser().getUsername();
    }

    String subforumName2 = "[deleted]";
    if(postEnt.getSubforum() != null) {
      subforumName2 = postEnt.getSubforum().getName();
    }

    PostDTO post = new PostDTO(postEnt.getId(), postEnt.getPostedDate(), username2, subforumName2, postEnt.getKarma(), postEnt.getTitle(), postEnt.getContent());
    return post;
  }




  public int getCountBySubforumFiltered(String subforumName, String byUsername) throws IllegalArgumentException {

    int count = 0;

    if(subforumName == null) {
      throw new NullPointerException();
    }

    if(byUsername != null) {
      UserEntity user = userRepo.getByUsername(byUsername);
      if(user == null) {
        throw new IllegalArgumentException("No Such User");
      }
      SubforumEntity subforum = subforumRepo.getByName(subforumName);
      if(subforum == null) {
        throw new IllegalArgumentException("No Such Subforum");
      }
      SettingsEntity settings = settingsRepo.getByUser(user);

      if (settings.getAllOrWhitelist()) {
        // whitelisted only
        List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        count = postRepo.getCountBySubforumOrderedFiltered(subforum, userList, settings.getAllOrWhitelist());

      } else {
        // all but blocked
        List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        count = postRepo.getCountBySubforumOrderedFiltered(subforum, userList, settings.getAllOrWhitelist());

      }
    } else {
      SubforumEntity subforum = subforumRepo.getByName(subforumName);
      if(subforum == null) {
        throw new IllegalArgumentException("No Such Subforum");
      }

      count = postRepo.getCountBySubforumOrdered(subforum);

    }

    return count;
  }

  public int getCountByUser(String username) throws IllegalArgumentException {

    if(username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);
    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    int count = postRepo.getCountByUserOrdered(user);

    return count;
  }

  public int getCountForUserSubscribed(String username) throws IllegalArgumentException {

    int count = 0;

    if(username != null) {
      UserEntity user = userRepo.getByUsername(username);
      if(user == null) {
        throw new IllegalArgumentException("No Such User");
      }

      //  get subscribed list by user, then get from db by subscribed list subforum filter, ordered by date, paginated

      List<SubscriptionEntity> subList = subscriptionRepo.getByUser(user);
      List<SubforumEntity> subforumList = new ArrayList<>();

      for(SubscriptionEntity subscription : subList) {
        subforumList.add(subscription.getSubforum());
      }

      SettingsEntity settings = settingsRepo.getByUser(user);

      if (settings.getAllOrWhitelist()) {
        // whitelisted only
        List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        count = postRepo.getCountBySubforumListFiltered(subforumList, userList, settings.getAllOrWhitelist());

      } else {
        // all but blocked
        List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        for(FilterEntity filter : filterList) {
          userList.add(filter.getManagedUser());
        }

        count = postRepo.getCountBySubforumListFiltered(subforumList, userList, settings.getAllOrWhitelist());

      }

    } else {
      count = postRepo.getCountAll();

    }

    return count;
  }

}

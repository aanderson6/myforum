package com.myforum.comments;

import com.myforum.comments.dtos.*;
import java.util.List;

public interface CommentInterface {

  public int createComment(String username, int postId, String subforumName, String content, Integer parentCommentId) throws IllegalArgumentException;
  public void updateCommentContent(int id, String content, String username) throws IllegalArgumentException, IllegalStateException;
  public void upvoteComment(int id, String username) throws IllegalArgumentException;
  public void downvoteComment(int id, String username) throws IllegalArgumentException;
  public void removeVoteOnComment(int id, String username) throws IllegalArgumentException;
  public void removeUserFromComment(int id, String username) throws IllegalArgumentException, IllegalStateException;
  public CommentDTO getById(int id) throws IllegalArgumentException;
  public List<CommentDTO> getByUserOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException;
  public int getCountByUser(String username) throws IllegalArgumentException;
  public List<CommentDTO> getByPost(int postId, String requestingUsername, int start, int returnCount, int depth) throws IllegalArgumentException;
  public int getCountByPostFiltered(int postId, String requestingUsername) throws IllegalArgumentException;
  public List<CommentDTO> getChildrenOf(int commentId, String requestingUsername, int start, int returnCount, int depth) throws IllegalArgumentException;
  public int getCountChildrenOf(int commentId, String requestingUsername) throws IllegalArgumentException;

}

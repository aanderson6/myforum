package com.myforum.comments;

import com.myforum.comments.dtos.*;
import java.util.List;

public interface CommentInterface {

/*
IllegalArgumentException("Invalid Content");
IllegalArgumentException("No Such User");
IllegalArgumentException("No Such Subforum");
IllegalArgumentException("No Such Post");
*/
  public int createComment(String username, int postId, String subforumName, String content, Integer parentCommentId) throws IllegalArgumentException;

// IllegalArgumentException("No Such User");
  public void hasReadComment(int id, String username) throws IllegalArgumentException;
  /*
IllegalArgumentException("Invalid Content");
IllegalArgumentException("No Such Comment");
IllegalStateException("Not Authorized to Update");
  */
  public void updateCommentContent(int id, String content, String username) throws IllegalArgumentException, IllegalStateException;

  /*
IllegalArgumentException("No Such Comment");
IllegalArgumentException("No Such User");
  */
  public void upvoteComment(int id, String username) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Comment");
  IllegalArgumentException("No Such User");
  */
  public void downvoteComment(int id, String username) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Comment");
  IllegalArgumentException("No Such User");
  */
  public void removeVoteOnComment(int id, String username) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Comment");
  IllegalArgumentException("No Such User");
  IllegalStateException("Not Authorized to Delete");
  */
  public void removeUserFromComment(int id, String username) throws IllegalArgumentException, IllegalStateException;

  /*
IllegalArgumentException("No Such Comment");
  */
  public CommentDTO getById(int id) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
IllegalArgumentException("Out of Range");
  */
  public List<CommentDTO> getByUserOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
  */
  public int getCountByUser(String username) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
IllegalArgumentException("Out of Range");
  */
  public List<CommentDTO> getByPost(int postId, String requestingUsername, int start, int returnCount, int depth) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such User");
  */
  public int getCountByPostFiltered(int postId, String requestingUsername) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
IllegalArgumentException("Out of Range");
  */
  public List<CommentDTO> getChildrenOf(int commentId, String requestingUsername, int start, int returnCount, int depth) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
  */
  public int getCountChildrenOf(int commentId, String requestingUsername) throws IllegalArgumentException;

}

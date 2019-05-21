package com.myforum.posts;

import com.myforum.posts.dtos.*;
import java.util.List;

public interface PostInterface {

/*
IllegalArgumentException("Invalid Title");
IllegalArgumentException("Invalid Content");
IllegalArgumentException("No Such User");
IllegalArgumentException("No Such Subforum");
*/
  public int createPost(String username, String subforumName, String title, String content) throws IllegalArgumentException;

/*
IllegalArgumentException("Invalid Title");
IllegalArgumentException("No Such Post");
IllegalStateException("Not Authorized to Update");
*/
  public void updatePostTitle(int id, String title, String username) throws IllegalArgumentException, IllegalStateException;

/*
IllegalArgumentException("Invalid Content");
IllegalArgumentException("No Such Post");
IllegalStateException("Not Authorized to Update");
*/
  public void updatePostContent(int id, String content, String username) throws IllegalArgumentException, IllegalStateException;

/*
IllegalArgumentException("No Such Post");
IllegalArgumentException("No Such User");
*/
  public void upvotePost(int id, String username) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such Post");
IllegalArgumentException("No Such User");
*/
  public void downvotePost(int id, String username) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such Post");
IllegalArgumentException("No Such User");
*/
  public void removeVoteOnPost(int id, String username) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such Post");
IllegalArgumentException("No Such User");
IllegalStateException("Not Authorized to Delete");
*/
  public void removeUserFromPost(int id, String username) throws IllegalArgumentException, IllegalStateException;

/*
IllegalArgumentException("No Such User");
IllegalArgumentException("No Such Subforum");
IllegalArgumentException("Out of Range");
*/
  public List<PostDTO> getBySubforumOrderedPaginated(String subforumName, int start, int returnCount, String byUsername) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such User");
IllegalArgumentException("No Such Subforum");
*/
  public int getCountBySubforumFiltered(String subforumName, String byUsername) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such User");
llegalArgumentException("Out of Range");
*/
  public List<PostDTO> getByUserOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such User");
*/
  public int getCountByUser(String username) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such User");
IllegalArgumentException("Out of Range");
*/
  public List<PostDTO> getForUserSubscribedOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such User");
*/
  public int getCountForUserSubscribed(String username) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such Post");
*/
  public PostDTO getById(int id) throws IllegalArgumentException;

}

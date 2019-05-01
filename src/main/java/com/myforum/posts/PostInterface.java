package com.myforum.posts;

import com.myforum.posts.dtos.*;
import java.util.List;

public interface PostInterface {

  public int createPost(String username, String subforumName, String title, String content) throws IllegalArgumentException;
  public void updatePostTitle(int id, String title, String username) throws IllegalArgumentException, IllegalStateException;
  public void updatePostContent(int id, String content, String username) throws IllegalArgumentException, IllegalStateException;
  public void upvotePost(int id, String username) throws IllegalArgumentException;
  public void downvotePost(int id, String username) throws IllegalArgumentException;
  public void removeVoteOnPost(int id, String username) throws IllegalArgumentException;
  public void removeUserFromPost(int id, String username) throws IllegalArgumentException, IllegalStateException;
  public List<PostDTO> getBySubforumOrderedPaginated(String subforumName, int start, int returnCount, String byUsername) throws IllegalArgumentException;
  public int getCountBySubforumFiltered(String subforumName, String byUsername) throws IllegalArgumentException;
  public List<PostDTO> getByUserOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException;
  public int getCountByUser(String username) throws IllegalArgumentException;
  public List<PostDTO> getForUserSubscribedOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException;
  public int getCountForUserSubscribed(String username) throws IllegalArgumentException;
  public PostDTO getById(int id) throws IllegalArgumentException;

}

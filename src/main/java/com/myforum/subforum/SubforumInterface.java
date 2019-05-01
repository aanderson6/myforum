package com.myforum.subforum;

import com.myforum.subforum.dtos.*;
import java.util.List;

public interface SubforumInterface {

  public void createSubforum(String username, String name, String description, String flair, String subforumRules) throws IllegalArgumentException;
  public void updateSubforumDescription(String subforumName, String description) throws IllegalArgumentException;
  public void updateSubforumFlair(String subforumName, String flair) throws IllegalArgumentException;
  public void updateSubforumRules(String subforumName, String rules) throws IllegalArgumentException;
  public void subscribeToSubforum(String username, String subforumName) throws IllegalArgumentException;
  public void unsubscribeFromSubforum(String username, String subforumName) throws IllegalArgumentException;
  public void addModToSubforum(String username, String subforumName) throws IllegalArgumentException;
  public void removeModFromSubforum(String username, String subforumName) throws IllegalArgumentException;
  public Boolean isMod(String subforumName, String username) throws IllegalArgumentException;
  public Boolean isSubscribed(String subforumName, String username) throws IllegalArgumentException;
  public List<SubforumDTO> getSubforumSubscriptionsByUser(String username) throws IllegalArgumentException;
  public List<SubforumDTO> getSubforumsOrderedPaginated(int start, int returnCount);
  public int getSubforumsCount();
  public List<ModDTO> getModsBySubforumOrderedByRank(String subforumName) throws IllegalArgumentException;
  public Boolean subforumExists(String subforumName);
  public SubforumDTO getSubforumByName(String subforumName) throws IllegalArgumentException;
  public SubSettingsDTO getSubSettingsByName(String subforumName) throws IllegalArgumentException;


}

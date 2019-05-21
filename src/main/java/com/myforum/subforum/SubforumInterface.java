package com.myforum.subforum;

import com.myforum.subforum.dtos.*;
import java.util.List;

public interface SubforumInterface {

  /*
    IllegalArgumentException("Invalid Subforum Name");
    IllegalArgumentException("Invalid Description");
    IllegalArgumentException("Invalid Flair");
    IllegalArgumentException("Invalid Rules");
    IllegalArgumentException("No Such User");
    IllegalArgumentException("Subforum Name Taken");
  */
  public void createSubforum(String username, String name, String description, String flair, String subforumRules) throws IllegalArgumentException;

  /*
    IllegalArgumentException("Invalid Description");
    IllegalArgumentException("No Such Subforum");
  */
  public void updateSubforumDescription(String subforumName, String description) throws IllegalArgumentException;

  /*
    IllegalArgumentException("Invalid Flair");
    IllegalArgumentException("No Such Subforum");
  */
  public void updateSubforumFlair(String subforumName, String flair) throws IllegalArgumentException;

  /*
    IllegalArgumentException("Invalid Rules");
    IllegalArgumentException("No Such Subforum");
  */
  public void updateSubforumRules(String subforumName, String rules) throws IllegalArgumentException;

  /*
    IllegalArgumentException("No Such Subforum");
    IllegalArgumentException("No Such User");
  */
  public void subscribeToSubforum(String username, String subforumName) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Subforum");
  IllegalArgumentException("No Such User");
  */
  public void unsubscribeFromSubforum(String username, String subforumName) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Subforum");
  IllegalArgumentException("No Such User");
  */
  public void addModToSubforum(String username, String subforumName) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Subforum");
  IllegalArgumentException("No Such User");
  */
  public void removeModFromSubforum(String username, String subforumName) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Subforum");
  IllegalArgumentException("No Such User");
  */
  public Boolean isMod(String subforumName, String username) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Subforum");
  IllegalArgumentException("No Such User");
  */
  public Boolean isSubscribed(String subforumName, String username) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such User");
  */
  public List<SubforumDTO> getSubforumSubscriptionsByUser(String username) throws IllegalArgumentException;

  /*
  IllegalArgumentException("Out of Range");
  */
  public List<SubforumDTO> getSubforumsOrderedPaginated(int start, int returnCount) throws IllegalArgumentException;

  public int getSubforumsCount();

  /*
  IllegalArgumentException("No Such Subforum");
  */
  public List<ModDTO> getModsBySubforumOrderedByRank(String subforumName) throws IllegalArgumentException;

  public Boolean subforumExists(String subforumName);

  /*
  IllegalArgumentException("No Such Subforum");
  */
  public SubforumDTO getSubforumByName(String subforumName) throws IllegalArgumentException;

  /*
  IllegalArgumentException("No Such Subforum");
  */
  public SubSettingsDTO getSubSettingsByName(String subforumName) throws IllegalArgumentException;


}

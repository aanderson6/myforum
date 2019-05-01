package com.myforum.subforum;

import com.myforum.subforum.entities.*;
import com.myforum.subforum.repositories.*;
import com.myforum.subforum.dtos.*;
import com.myforum.login.entities.UserEntity;
import java.time.Instant;
import com.myforum.login.repositories.UserRepo;
import java.util.List;
import java.util.ArrayList;

public class SubforumService implements SubforumInterface {

  public SubforumService() {  }

  UserRepo userRepo = new UserRepo();
  ModListRepo modRepo = new ModListRepo();
  SubforumRepo subforumRepo = new SubforumRepo();
  SubforumSettingsRepo subforumSettingsRepo = new SubforumSettingsRepo();
  SubscriptionRepo subscriptionRepo = new SubscriptionRepo();

  // private internal methods

  private void increaseSubforumSubscribers(SubforumEntity subforum) {
    if(subforum == null) {
      throw new NullPointerException();
    }
    subforum.setSubscriberCount(subforum.getSubscriberCount() + 1);
    subforumRepo.update(subforum);
  }

  private void decreaseSubforumSubscribers(SubforumEntity subforum) {
    if(subforum == null) {
      throw new NullPointerException();
    }
    subforum.setSubscriberCount(subforum.getSubscriberCount() - 1);
    subforumRepo.update(subforum);
  }

  private void reduceModRankByOne(ModListEntity mod) {
    if(mod == null) {
      throw new NullPointerException();
    }
    mod.setRank(mod.getRank() - 1);
    modRepo.update(mod);
  }

  // list of functions I will need to build:

  public void createSubforum(String username, String name, String description, String flair, String subforumRules) throws IllegalArgumentException {

    if(username == null || name == null || description == null || flair == null || subforumRules == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    SubforumEntity tempSubforum = subforumRepo.getByName(name);
    if(!(tempSubforum == null)) {
      throw new IllegalArgumentException("Subforum name taken");
    }

    SubforumEntity subforum = new SubforumEntity(name, description, flair, 0, Instant.now(), user);

    subforum = subforumRepo.add(subforum);
    subforumSettingsRepo.add(new SubforumSettingsEntity(subforum, subforumRules));
    subscriptionRepo.add(new SubscriptionEntity(subforum, user));
    increaseSubforumSubscribers(subforum);
    modRepo.add(new ModListEntity(user, subforum, 1));

  }

  public void updateSubforumDescription(String subforumName, String description) throws IllegalArgumentException {

    if(subforumName == null || description == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    subforum.setDescription(description);
    subforumRepo.update(subforum);

  }

  public void updateSubforumFlair(String subforumName, String flair) throws IllegalArgumentException {

    if(subforumName == null || flair == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    subforum.setFlair(flair);
    subforumRepo.update(subforum);

  }

  public void updateSubforumRules(String subforumName, String rules) throws IllegalArgumentException {

    if(subforumName == null || rules == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    SubforumSettingsEntity subforumSettings = subforumSettingsRepo.getBySubforum(subforum);
    subforumSettings.setRules(rules);
    subforumSettingsRepo.update(subforumSettings);

  }

  public void subscribeToSubforum(String username, String subforumName) throws IllegalArgumentException {

    if(subforumName == null || username == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    if(subscriptionRepo.getIsSubscribedByUserAndSubforum(user, subforum)) {
      return;
    }

    subscriptionRepo.add(new SubscriptionEntity(subforum, user));
    increaseSubforumSubscribers(subforum);

  }

  public void unsubscribeFromSubforum(String username, String subforumName) throws IllegalArgumentException {

    if(subforumName == null || username == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    List<SubscriptionEntity> subscriptions = subscriptionRepo.getByUserAndSubforum(user, subforum);
    for(SubscriptionEntity subscription : subscriptions) {
      subscriptionRepo.remove(subscription);
      decreaseSubforumSubscribers(subforum);
    }

  }

  public void addModToSubforum(String username, String subforumName) throws IllegalArgumentException {

    if(subforumName == null || username == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    if(modRepo.getIsModByUserAndSubforum(user, subforum)) {
      return;
    }

    int count = modRepo.getCountBySubforum(subforum);
    modRepo.add(new ModListEntity(user, subforum, count + 1));

  }

  public void removeModFromSubforum(String username, String subforumName) throws IllegalArgumentException {

    if(subforumName == null || username == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    ModListEntity mod = modRepo.getByUserAndSubforum(user, subforum);
    int modRank = mod.getRank();
    modRepo.remove(mod);

    List<ModListEntity> modList = modRepo.getBySubforumAboveRank(subforum, modRank);
    for(ModListEntity modEnt : modList) {
      reduceModRankByOne(modEnt);
    }

  }

  public Boolean isMod(String subforumName, String username) throws IllegalArgumentException {

    if(subforumName == null || username == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    return modRepo.getIsModByUserAndSubforum(user, subforum);

  }

  public Boolean isSubscribed(String subforumName, String username) throws IllegalArgumentException {

    if(subforumName == null || username == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    return subscriptionRepo.getIsSubscribedByUserAndSubforum(user, subforum);

  }

  public List<SubforumDTO> getSubforumSubscriptionsByUser(String username) throws IllegalArgumentException {

    if(username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    List<SubscriptionEntity> subscriptionsList = subscriptionRepo.getByUser(user);

    List<SubforumDTO> subforums = new ArrayList<SubforumDTO>();

    for(SubscriptionEntity subscription : subscriptionsList) {
      String subCreator = "[deleted]";
      SubforumEntity subforum = subscription.getSubforum();
      if(subforum.getCreatedBy() != null) {
        subCreator = subforum.getCreatedBy().getUsername();
      }
      subforums.add(new SubforumDTO(subforum.getName(), subforum.getDescription(), subforum.getFlair(), subforum.getSubscriberCount(), subforum.getCreatedDate(), subCreator));
    }

    return subforums;

  }

  public List<SubforumDTO> getSubforumsOrderedPaginated(int start, int returnCount) {

    List<SubforumEntity> subforumEntList = subforumRepo.getOrderedPaginated(start, returnCount);
    List<SubforumDTO> subforums = new ArrayList<SubforumDTO>();

    for(SubforumEntity subforum : subforumEntList) {
      String subCreator = "[deleted]";
      if(subforum.getCreatedBy() != null) {
        subCreator = subforum.getCreatedBy().getUsername();
      }
      subforums.add(new SubforumDTO(subforum.getName(), subforum.getDescription(), subforum.getFlair(), subforum.getSubscriberCount(), subforum.getCreatedDate(), subCreator));
    }

    return subforums;

  }

  public int getSubforumsCount() {

    return subforumRepo.getCount();

  }

  public List<ModDTO> getModsBySubforumOrderedByRank(String subforumName) throws IllegalArgumentException {

    if(subforumName == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforum = subforumRepo.getByName(subforumName);

    if(subforum == null) {
      throw new IllegalArgumentException("No such subforum");
    }

    List<ModListEntity> modEntList = modRepo.getBySubforumOrderedByRank(subforum);
    List<ModDTO> modList = new ArrayList<ModDTO>();

    for(ModListEntity mod : modEntList) {
      modList.add(new ModDTO(mod.getUser().getUsername(), mod.getRank()));
    }

    return modList;

  }

  public Boolean subforumExists(String subforumName) {
    if(subforumName == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforumEnt = subforumRepo.getByName(subforumName);

    return !(subforumEnt == null);

  }

  public SubforumDTO getSubforumByName(String subforumName) throws IllegalArgumentException {
    if(subforumName == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforumEnt = subforumRepo.getByName(subforumName);

    if(subforumEnt == null) {
      throw new IllegalArgumentException("No such subforum");
    } else {
      String subCreator = "[deleted]";
      if(subforumEnt.getCreatedBy() != null) {
        subCreator = subforumEnt.getCreatedBy().getUsername();
      }
      SubforumDTO subforum = new SubforumDTO(subforumEnt.getName(), subforumEnt.getDescription(), subforumEnt.getFlair(), subforumEnt.getSubscriberCount(), subforumEnt.getCreatedDate(), subCreator);
      return subforum;
    }
  }

  public SubSettingsDTO getSubSettingsByName(String subforumName) throws IllegalArgumentException {
    if(subforumName == null) {
      throw new NullPointerException();
    }

    SubforumEntity subforumEnt = subforumRepo.getByName(subforumName);

    if(subforumEnt == null) {
      throw new IllegalArgumentException("No such subforum");
    } else {
      SubforumSettingsEntity subSettingsEnt = subforumSettingsRepo.getBySubforum(subforumEnt);
      SubSettingsDTO subSettings = new SubSettingsDTO(subSettingsEnt.getSubforum().getName(), subSettingsEnt.getRules());
      return subSettings;
    }
  }

}

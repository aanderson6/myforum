package com.myforum.messaging;

import com.myforum.login.entities.UserEntity;
import com.myforum.login.repositories.UserRepo;
import com.myforum.messaging.entities.*;
import com.myforum.messaging.repositories.*;
import com.myforum.messaging.dtos.*;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

public class MessagingService implements MessagingInterface {

  private UserRepo userRepo = new UserRepo();
  private ConversationRepo conversationRepo = new ConversationRepo();
  private FilterRepo filterRepo = new FilterRepo();
  private MembershipRepo membershipRepo = new MembershipRepo();
  private MessageRepo messageRepo = new MessageRepo();
  private NotificationRepo notificationRepo = new NotificationRepo();
  private SettingsRepo settingsRepo = new SettingsRepo();

  public void startConversation(String fromUsername, List<String> toUsernameList, String subject, String content) throws IllegalArgumentException {

    if (fromUsername == null || toUsernameList == null || subject == null || content == null) {
      throw new NullPointerException();
    }

    UserEntity fromUserEntity = userRepo.getByUsername(fromUsername);
    if(fromUserEntity == null) {
      throw new IllegalArgumentException("No such from user");
    }
    List<UserEntity> toUserEntityList = new ArrayList<UserEntity>();

    for(String toUsername : toUsernameList) {
      UserEntity tempUserEntity = userRepo.getByUsername(toUsername);
      if(tempUserEntity == null) {
      throw new IllegalArgumentException("No such to user: " + toUsername);
    }
      toUserEntityList.add(tempUserEntity);
    }

    ConversationEntity newConversation = conversationRepo.add(new ConversationEntity(fromUserEntity, Instant.now(), Instant.now(), 1));
    MessageEntity newMessage = messageRepo.add(new MessageEntity(newConversation, fromUserEntity, Instant.now(), subject, content, 1));

    for(UserEntity user : toUserEntityList) {
      membershipRepo.add(new MembershipEntity(newConversation, user, 1, true));
      notificationRepo.add(new NotificationEntity(user, newMessage.getId(), "Message"));
    }

  }

  public void replyMessage(String fromUsername, int conversationID, String subject, String content) throws IllegalArgumentException {

    if (fromUsername == null || subject == null || content == null) {
      throw new NullPointerException();
    }

    UserEntity fromUserEntity = userRepo.getByUsername(fromUsername);
    if(fromUserEntity == null) {
      throw new IllegalArgumentException("No such from user");
    }
    ConversationEntity conversation = conversationRepo.getById(conversationID);
    if(conversation == null) {
      throw new IllegalArgumentException("No such conversation");
    }

    conversation.setMostRecent(Instant.now());
    conversationRepo.update(conversation);

    List<MembershipEntity> membershipList = membershipRepo.getByConversation(conversation);

    Boolean isMember = false;
    for(MembershipEntity membership : membershipList) {
      if (membership.getUser().getUsername().equals(fromUserEntity.getUsername())) {
        isMember = true;
      }
    }

    if(!isMember) {
      throw new IllegalArgumentException("User not member of conversation");
    }


    MessageEntity newMessage = messageRepo.add(new MessageEntity(conversation, fromUserEntity, Instant.now(), subject, content, conversation.getChainLength() + 1));

    conversation.setChainLength(conversation.getChainLength() + 1);
    conversationRepo.update(conversation);

    for(MembershipEntity membership : membershipList) {
      SettingsEntity settings = settingsRepo.getByUser(membership.getUser());
      if(settings.getNotifyMessages()) {
        notificationRepo.add(new NotificationEntity(membership.getUser(), newMessage.getId(), "Message"));
      }
    }

  }

  // please call getConversationCount before calling this to verify start-end valid values - 0 start
  public List<ConversationDTO> getOrderedConversationsRange(String username, int start, int returnCount) throws IllegalArgumentException {

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    SettingsEntity settings = settingsRepo.getByUser(user);
    List<MembershipEntity> membershipList;

    if (settings.getAllOrWhitelist()) {
      // whitelisted only

      List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
      List<UserEntity> userList = new ArrayList<UserEntity>();
      for(FilterEntity filter : filterList) {
        userList.add(filter.getManagedUser());
      }

      int count = membershipRepo.getCountByUserOrderedVisibleOnlyFiltered(user, userList, settings.getAllOrWhitelist());

      if(start + returnCount > count) {
        throw new IllegalArgumentException("Out of Range");
      }

      membershipList = membershipRepo.getByUserOrderedVisibleOnlyFiltered(user, start, returnCount, userList, settings.getAllOrWhitelist());

    } else {
      // all but blocked

      List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
      List<UserEntity> userList = new ArrayList<UserEntity>();
      for(FilterEntity filter : filterList) {
        userList.add(filter.getManagedUser());
      }

      int count = membershipRepo.getCountByUserOrderedVisibleOnlyFiltered(user, userList, settings.getAllOrWhitelist());

      if(start + returnCount > count) {
        throw new IllegalArgumentException("Out of Range");
      }

      membershipList = membershipRepo.getByUserOrderedVisibleOnlyFiltered(user, start, returnCount, userList, settings.getAllOrWhitelist());

    }

    List<ConversationDTO> conversations = new ArrayList<ConversationDTO>();

    for(MembershipEntity membership : membershipList) {
      ConversationEntity conversation = membership.getConversation();
      List<MessageDTO> messages = new ArrayList<MessageDTO>();
      List<MessageEntity> messagesEntList = messageRepo.getByConversation(conversation);
      for(MessageEntity message : messagesEntList) {
        String messageUsername = "[deleted]";
        if(message.getFromUser() != null) {
          messageUsername = message.getFromUser().getUsername();
        }
        messages.add(new MessageDTO(message.getId(), messageUsername, message.getSent(), message.getSubject(), message.getContent(), message.getChainNum()));
      }

      conversations.add(new ConversationDTO(conversation.getId(), conversation.getFromUser().getUsername(), conversation.getStarted(), conversation.getMostRecent(), conversation.getChainLength(), messages));
    }

    return conversations;
  }

  public MessageDTO getMessage(int id) throws IllegalArgumentException {

    MessageEntity messageEnt = messageRepo.getById(id);
    if(messageEnt == null) {
      throw new IllegalArgumentException("No such message");
    }
    String messageUsername = "[deleted]";
    if(messageEnt.getFromUser() != null) {
      messageUsername = messageEnt.getFromUser().getUsername();
    }
    MessageDTO message = new MessageDTO(messageEnt.getId(), messageUsername, messageEnt.getSent(), messageEnt.getSubject(), messageEnt.getContent(), messageEnt.getChainNum());
    return message;

  }

  public void hideConversationByUser(String username, int conversationId) throws IllegalArgumentException {

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    ConversationEntity conversation = conversationRepo.getById(conversationId);

    if(conversation == null) {
      throw new IllegalArgumentException("No such conversation");
    }

    MembershipEntity membership = membershipRepo.getByUserAndConversation(user, conversation);

    if(membership == null) {
      throw new IllegalArgumentException("User not member of conversation");
    }

    membership.setVisible(false);
    membershipRepo.update(membership);

  }

  public void hasReadMessage(String username, int messageId) throws IllegalArgumentException {

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    NotificationEntity notification = notificationRepo.getByUserAndIdNum(user, messageId);

    if(!(notification == null)) {
      notificationRepo.remove(notification);
    }

    MessageEntity message = messageRepo.getById(messageId);

    if(message == null) {
      throw new IllegalArgumentException("No such message");
    }

    MembershipEntity membership = membershipRepo.getByUserAndConversation(user, message.getConversation());

    if(membership == null) {
      throw new IllegalArgumentException("User not member of conversation");
    }

    membership.setUnreadChainNum(message.getChainNum() + 1);
    membershipRepo.update(membership);

  }

  public void updateSettings(String username, Boolean collapseReadMessages, Boolean allOrWhiteList, Boolean notifyUnreadMessages, Boolean markReadIfOpened) throws IllegalArgumentException {

    if (username == null || collapseReadMessages == null || allOrWhiteList == null || notifyUnreadMessages == null || markReadIfOpened == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    SettingsEntity settings = settingsRepo.getByUser(user);
    settings.setCollapseReadMessages(collapseReadMessages);
    settings.setAllOrWhitelist(allOrWhiteList);
    settings.setNotifyMessages(notifyUnreadMessages);
    settings.setOpenMarkRead(markReadIfOpened);
    settingsRepo.update(settings);

  }

  public void addFilter(String username, String managedUsername, Boolean blockAllow) throws IllegalArgumentException {

    if (username == null || managedUsername == null || blockAllow == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    UserEntity managedUser = userRepo.getByUsername(managedUsername);

    if(managedUser == null) {
      throw new IllegalArgumentException("No such managed user");
    }

    filterRepo.add(new FilterEntity(user, managedUser, blockAllow));

  }

  public void removeFilter(String username, String managedUsername, Boolean blockAllow) throws IllegalArgumentException {

    if (username == null || managedUsername == null || blockAllow == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    UserEntity managedUser = userRepo.getByUsername(managedUsername);

    if(managedUser == null) {
      throw new IllegalArgumentException("No such managed user");
    }

    filterRepo.removeByUserManagedUserStatus(user, managedUser, blockAllow);

  }

  public SettingsDTO getSettings(String username) throws IllegalArgumentException {

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    SettingsEntity settings = settingsRepo.getByUser(user);

    return new SettingsDTO(settings.getCollapseReadMessages(), settings.getAllOrWhitelist(), settings.getNotifyMessages(), settings.getOpenMarkRead());

  }

  public List<FilterDTO> getFilters(String username) throws IllegalArgumentException {

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    List<FilterEntity> filterEntList = filterRepo.getByUser(user);

    List<FilterDTO> filterList = new ArrayList<FilterDTO>();

    for(FilterEntity filter : filterEntList) {
      filterList.add(new FilterDTO(filter.getUser().getUsername(), filter.getManagedUser().getUsername(), filter.getBlockAllow()));
    }

    return filterList;

  }

  public List<NotificationDTO> getNotifications(String username) throws IllegalArgumentException {

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    List<NotificationEntity> notificationEntList = notificationRepo.getByUser(user);
    List<NotificationDTO> notificationList = new ArrayList<NotificationDTO>();

    for(NotificationEntity notificationEnt : notificationEntList) {
      notificationList.add(new NotificationDTO(notificationEnt.getIdNumber(), notificationEnt.getIdNumberType()));
    }

    return notificationList;

  }

  public int getConversationCount(String username) throws IllegalArgumentException {

    if (username == null) {
      throw new NullPointerException();
    }

    int count = 0;
    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No such user");
    }

    SettingsEntity settings = settingsRepo.getByUser(user);

    if (settings.getAllOrWhitelist()) {
      // whitelisted
      List<FilterEntity> filterList = filterRepo.getAllowedByUser(user);
      List<UserEntity> userList = new ArrayList<UserEntity>();
      for(FilterEntity filter : filterList) {
        userList.add(filter.getManagedUser());
      }
      count = membershipRepo.getCountByUserOrderedVisibleOnlyFiltered(user, userList, settings.getAllOrWhitelist());
    } else {
      // blocked
      List<FilterEntity> filterList = filterRepo.getBlockedByUser(user);
      List<UserEntity> userList = new ArrayList<UserEntity>();
      for(FilterEntity filter : filterList) {
        userList.add(filter.getManagedUser());
      }
      count = membershipRepo.getCountByUserOrderedVisibleOnlyFiltered(user, userList, settings.getAllOrWhitelist());
    }
    return count;

  }

}

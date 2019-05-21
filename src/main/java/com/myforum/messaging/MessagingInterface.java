package com.myforum.messaging;

import com.myforum.messaging.dtos.*;
import java.util.List;

public interface MessagingInterface {

/*
IllegalArgumentException("Invalid Subject");
IllegalArgumentException("Invalid Content");
IllegalArgumentException("No Such From User");
IllegalArgumentException("No Such To User: " + toUsername);
*/
  // make sure you add fromUsername to toUsernameList on input
  public void startConversation(String fromUsername, List<String> toUsernameList, String subject, String content) throws IllegalArgumentException;

/*
IllegalArgumentException("Invalid Subject");
IllegalArgumentException("Invalid Content");
IllegalArgumentException("No Such From User");
IllegalArgumentException("No Such Conversation");
IllegalArgumentException("User Not Conversation Member");
  */
  public void replyMessage(String fromUsername, int conversationID, String subject, String content) throws IllegalArgumentException;
  // please call getConversationCount before calling this to verify start-end valid values - 0 start

/*
IllegalArgumentException("No Such User");
IllegalArgumentException("Out of Range");

*/
  public List<ConversationDTO> getOrderedConversationsRange(String username, int start, int returnCount) throws IllegalArgumentException;

//IllegalArgumentException("No such message");
  public MessageDTO getMessage(int id) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
IllegalArgumentException("No Such Conversation");
IllegalArgumentException("User Not Conversation Member");

  */
  public void hideConversationByUser(String username, int conversationId) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
IllegalArgumentException("No Such Message");
IllegalArgumentException("User Not Conversation Member");
  */
  public void hasReadMessage(String username, int messageId) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
  */
  public void updateSettings(String username, Boolean collapseReadMessages, Boolean allOrWhiteList, Boolean notifyUnreadMessages, Boolean markReadIfOpened) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
IllegalArgumentException("No Such Managed User");
  */
  public void addFilter(String username, String managedUsername, Boolean blockAllow) throws IllegalArgumentException;

  /*
IllegalArgumentException("No Such User");
IllegalArgumentException("No Such Managed User");
*/
  public void removeFilter(String username, String managedUsername, Boolean blockAllow) throws IllegalArgumentException;

// IllegalArgumentException("No Such User");
  public SettingsDTO getSettings(String username) throws IllegalArgumentException;

// IllegalArgumentException("No Such User");
  public List<FilterDTO> getFilters(String username) throws IllegalArgumentException;

// IllegalArgumentException("No Such User");
  public List<NotificationDTO> getNotifications(String username) throws IllegalArgumentException;

// IllegalArgumentException("No Such User");
  public int getConversationCount(String username) throws IllegalArgumentException;

}

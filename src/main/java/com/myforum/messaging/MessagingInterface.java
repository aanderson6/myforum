package com.myforum.messaging;

import com.myforum.messaging.dtos.*;
import java.util.List;

public interface MessagingInterface {

  // make sure you add fromUsername to toUsernameList on input??
  public void startConversation(String fromUsername, List<String> toUsernameList, String subject, String content) throws IllegalArgumentException;
  public void replyMessage(String fromUsername, int conversationID, String subject, String content) throws IllegalArgumentException;
  // please call getConversationCount before calling this to verify start-end valid values - 0 start
  public List<ConversationDTO> getOrderedConversationsRange(String username, int start, int returnCount) throws IllegalArgumentException;
  public MessageDTO getMessage(int id) throws IllegalArgumentException;
  public void hideConversationByUser(String username, int conversationId) throws IllegalArgumentException;
  public void hasReadMessage(String username, int messageId) throws IllegalArgumentException;
  public void updateSettings(String username, Boolean collapseReadMessages, Boolean allOrWhiteList, Boolean notifyUnreadMessages, Boolean markReadIfOpened) throws IllegalArgumentException;
  public void addFilter(String username, String managedUsername, Boolean blockAllow) throws IllegalArgumentException;
  public void removeFilter(String username, String managedUsername, Boolean blockAllow) throws IllegalArgumentException;
  public SettingsDTO getSettings(String username) throws IllegalArgumentException;
  public List<FilterDTO> getFilters(String username) throws IllegalArgumentException;
  public List<NotificationDTO> getNotifications(String username) throws IllegalArgumentException;
  public int getConversationCount(String username) throws IllegalArgumentException;

}

/*package com.myforum.messaging;
import com.myforum.login.DaoInterfaceLogin;
import com.myforum.login.models.UserModel;
import com.myforum.messaging.entities.*;
import java.sql.SQLException;
import javax.naming.NamingException;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

public class MessagingService {

  private DaoInterfaceMessaging messagingDAO;
  private DaoInterfaceLogin loginDAO;

  public MessagingService(DaoInterfaceMessaging messagingDAO, DaoInterfaceLogin loginDAO) {
    this.messagingDAO = messagingDAO;
    this.loginDAO = loginDAO;
  }

  public void startConversation(String fromUsername, List<String> toUsernameList, String subject, String content) throws SQLException, NamingException {

    if (fromUsername == null || toUsernameList == null || subject == null || content == null) {
      throw new NullPointerException();
    }

    UserModel fromUserModel = loginDAO.getUserByUsername(fromUsername);
    List<UserModel> toUserModelList = new ArrayList<UserModel>();

    for(int i = 0; i <= toUsernameList.size() - 1; i++) {
      UserModel tempUserModel = loginDAO.getUserByUsername(toUsernameList.get(i));
      toUserModelList.add(tempUserModel);
    }

    ConversationDTO newConversation = messagingDAO.createConversation(new ConversationDTO(fromUserModel.getUid(), Instant.now(), Instant.now()));
    MessageDTO newMessage = messagingDAO.createMessage(new MessageDTO(newConversation.getCid(), fromUserModel.getUid(), Instant.now(), subject, content, 1));

    for(UserModel user : toUserModelList) {
      MembershipDTO newMembership = messagingDAO.createMembership(new MembershipDTO(newConversation.getCid(), user.getUid(), 1, true));
      NotificationDTO newNotification = messagingDAO.createNotification(new NotificationDTO(user.getUid(), newMessage.getMid(), "Message"));
    }

  }

  public void replyMessage(String fromUsername, int conversationID, String subject, String content) throws SQLException, NamingException {

    if (fromUsername == null || subject == null || content == null) {
      throw new NullPointerException();
    }

    UserModel fromUserModel = loginDAO.getUserByUsername(fromUsername);
    ConversationDTO conversation = messagingDAO.getConversationByCid(conversationID);
    List<MembershipDTO> membershipList = messagingDAO.getMembershipsByCid(conversationID);
    /// need to edit db and DAO to allow 'conversation' to hold most recent chain number...
    MessageDTO newMessage = messagingDAO.createMessage(new MessageDTO(conversationID, fromUserModel.getUid(), Instant.now(), subject, content, conversation.getRecentChain()));

    for(MembershipDTO membership : membershipList) {
      messagingDAO.createNotification(new NotificationDTO(membership.getUid(), newMessage.getMid(), "Message"));
    }

  }

  // please call getConversationCount before calling this to verify start-end valid values - 0 start
  public List<ConversationDTO> getOrderedConversationsRange(String username, int start, int returnCount) throws SQLException, NamingException {

    if (username == null) {
      throw new NullPointerException();
    }

    UserModel user = loginDAO.getUserByUsername(username);

    List<MembershipDTO> membershipList = messagingDAO.getOrderedMembershipByUid(user.getUid(), start, returnCount);
    List<FilterDTO> filterList = messagingDAO.getFiltersByUid(user.getUid());

    for(FilterDTO filter : filterList) {
      int filterCount = messagingDAO.getMembershipByUidCount(filter.getManagedUid());
      List<MembershipDTO> managedMembershipList = messagingDAO.getOrderedMembershipByUid(filter.getManagedUid(), 0, filterCount - 1);
      membershipList.removeAll(managedMembershipList); // this may end up causing an error if objects are identical but different objects and references but methods require pointers to same object...
    }

    List<ConversationDTO> conversations = new ArrayList<ConversationDTO>();

    for(MembershipDTO membership : membershipList) {
      conversations.add(messagingDAO.getConversationByCid(membership.getCid()));
    }

    return conversations;
  }












  public List<MessageDTO> getMessagesByConversation(int cid){

    messagingDAO.getMessagesByCid();
    messagingDAO.getFiltersByUid();

  }

  public MessageDTO getMessage(int mid){

    messagingDAO.getMessageByMid();

  }

  public void hideConversationByUser(String username, int cid){

    if (username == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.updateMembership();

  }

  public void hasReadMessage(String username, int mid){

    if (username == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.getNotificationsByUid();
    messagingDAO.deleteNotificationByNid();
    messagingDAO.updateMembership();

  }

  public void updateSettings(String username, Boolean collapseReadMessages, Boolean allOrWhiteList, Boolean notifyUnreadMessages, Boolean markReadIfOpened){

    if (username == null || collapseReadMessages == null || allOrWhiteList == null || notifyUnreadMessages == null || markReadIfOpened == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.updateSettings();

  }

  public void addFilter(String username, String managedUsername, Boolean blockAllow){

    if (username == null || managedUsername == null || blockAllow == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.createFilter();

  }

  public void removeFilter(String username, String managedUsername, Boolean blockAllow){

    if (username == null || managedUsername == null || blockAllow == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.getFiltersByUid();
    messagingDAO.deleteFilterByFid();

  }

  public SettingsDTO getSettings(String username){

    if (username == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.getSettingsByUid();

  }

  public List<FilterDTO> getFilters(String username){

    if (username == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.getFiltersByUid();

  }

  public List<NotificationDTO> getNotifications(String username){

    if (username == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.getNotificationsByUid();

  }

  public int getConversationCount(String username){

    if (username == null) {
      throw new NullPointerException();
    }

    loginDAO.getUserByUsername();
    messagingDAO.getOrderedMembershipByUidCount();

  }

}*/

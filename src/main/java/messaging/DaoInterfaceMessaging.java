package com.myforum.messaging;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

import com.myforum.messaging.entities.ConversationDTO;
import com.myforum.messaging.entities.FilterDTO;
import com.myforum.messaging.entities.MembershipDTO;
import com.myforum.messaging.entities.MessageDTO;
import com.myforum.messaging.entities.NotificationDTO;
import com.myforum.messaging.entities.SettingsDTO;

public interface DaoInterfaceMessaging {

  public ConversationDTO createConversation(ConversationDTO conversation) throws SQLException, NamingException;
  public MessageDTO createMessage(MessageDTO message) throws SQLException, NamingException, IllegalArgumentException;
  public MembershipDTO createMembership(MembershipDTO membership) throws SQLException, NamingException;
  public FilterDTO createFilter(FilterDTO filter) throws SQLException, NamingException;
  public SettingsDTO createSettings(SettingsDTO settings) throws SQLException, NamingException;
  public NotificationDTO createNotification(NotificationDTO notification) throws SQLException, NamingException;
  public void deleteFilterByFID(int fid) throws SQLException, NamingException;
  public void deleteFilterByUID(int uid) throws SQLException, NamingException;
  public void deleteFilterByUidMidBa(int uid, int managed_uid, Boolean ba) throws SQLException, NamingException;
  public void deleteNotificationByNid(int nid) throws SQLException, NamingException;
  public void deleteNotificationsByUid(int uid) throws SQLException, NamingException;
  public void deleteSettingsByUID(int uid) throws SQLException, NamingException;
  public void deleteMembershipByUID(int uid) throws SQLException, NamingException;
  public void updateMembership(MembershipDTO membership) throws SQLException, NamingException;
  public void updateSettings(SettingsDTO settings) throws SQLException, NamingException;
  public void updateConversation(ConversationDTO conversation) throws SQLException, NamingException;
  public void updateMessage(MessageDTO message) throws SQLException, NamingException, IllegalArgumentException;
  public MembershipDTO getMembershipByGid(int gid) throws SQLException, NamingException;
  public int getMembershipByUidCount(int uid) throws SQLException, NamingException;
  public List<MembershipDTO> getOrderedMembershipByUid(int uid, int start, int returnCount) throws SQLException, NamingException, IllegalArgumentException;
  public ConversationDTO getConversationByCID(int cid) throws SQLException, NamingException;
  public MessageDTO getMessageByMid(int mid) throws SQLException, NamingException;
  public List<MessageDTO> getMessagesByCID(int cid) throws SQLException, NamingException;
  public FilterDTO getFilterByFid(int fid) throws SQLException, NamingException;
  public SettingsDTO getSettingsBySid(int sid) throws SQLException, NamingException;
  public SettingsDTO getSettingsByUID(int uid) throws SQLException, NamingException;
  public NotificationDTO getNotificationByNid(int nid) throws SQLException, NamingException;
  public List<NotificationDTO> getNotificationsByUID(int uid) throws SQLException, NamingException;

}

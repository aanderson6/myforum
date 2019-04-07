package com.myforum.messaging;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import com.myforum.messaging.entities.ConversationDTO;
import com.myforum.messaging.entities.FilterDTO;
import com.myforum.messaging.entities.MembershipDTO;
import com.myforum.messaging.entities.MessageDTO;
import com.myforum.messaging.entities.NotificationDTO;
import com.myforum.messaging.entities.SettingsDTO;


public class DaoMySqlMessaging implements DaoInterfaceMessaging {

  ///// VARIABLES /////

  Context ctx;
  DataSource ds;
  Connection con;
  PreparedStatement stmt;
  ResultSet rs;

  ///// CONNECTION METHODS /////

  private void openConnection() throws SQLException, NamingException {
    ctx = new InitialContext();
    ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyForum");
    con = ds.getConnection();
  }

  private void closeConnection() {
    try{
      rs.close();
      stmt.close();
      con.close();
      ctx.close();
    }
    catch (Exception e) {  /* IGNORE */   }
  }

  ///// CRUD METHODS /////

  ///// CREATES /////

  public ConversationDTO createConversation(ConversationDTO conversation) throws SQLException, NamingException {
    int auto_id;

    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "INSERT INTO Conversations (from_uid, started, most_recent) VALUES (?, ?, ?);";
      stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, conversation.getUid());
			stmt.setTimestamp(2, Timestamp.from(conversation.getStarted()));
			stmt.setTimestamp(3, Timestamp.from(conversation.getRecent()));
      stmt.execute();
      rs = stmt.getGeneratedKeys();
      rs.next();
      auto_id = rs.getInt(1);
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    ///// RETURN Created /////

    conversation = getConversationByCid(auto_id);
    return conversation;

  }

  public MessageDTO createMessage(MessageDTO message) throws SQLException, NamingException, IllegalArgumentException {
    int auto_id;
    ///// VALIDATE INPUT /////

    if (message.getSubject().length() > 140) {
      throw new IllegalArgumentException("Invalid subject");
    } else if (message.getContent().length() > 10000) {
      throw new IllegalArgumentException("Invalid content");
    }

    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "INSERT INTO Messages (conversation_id, from_uid, sent, subject, content, chain_num) VALUES (?, ?, ?, ?, ?, ?)";
      stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, message.getCid());
      stmt.setInt(2, message.getFromUid());
      stmt.setTimestamp(3, Timestamp.from(message.getSent()));
      stmt.setString(4, message.getSubject());
      stmt.setString(5, message.getContent());
      stmt.setInt(6, message.getChainNum());
      stmt.execute();
      rs = stmt.getGeneratedKeys();
      rs.next();
      auto_id = rs.getInt(1);
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    ///// RETURN Created /////

    message = getMessageByMid(auto_id);
    return message;

  }

  public MembershipDTO createMembership(MembershipDTO membership) throws SQLException, NamingException {
    int auto_id;
    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "INSERT INTO Membership (conversation_id, uid, unread_chain_num, visible) VALUES (?, ?, ?, TRUE)";
      stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, membership.getCid());
      stmt.setInt(2, membership.getUid());
			stmt.setInt(3, membership.getUnreadChainNum());
      stmt.execute();
      rs = stmt.getGeneratedKeys();
      rs.next();
      auto_id = rs.getInt(1);
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    ///// RETURN Created /////

    membership = getMembershipByMid(auto_id);
    return membership;
  }

  public FilterDTO createFilter(FilterDTO filter) throws SQLException, NamingException {
    int auto_id;
    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "INSERT INTO Filters (uid, managed_uid, block_allow) VALUES (?, ?, ?)";
      stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, filter.getUid());
      stmt.setInt(2, filter.getManagedUid());
      stmt.setBoolean(3, filter.getBlockAllow());
      stmt.execute();
      rs = stmt.getGeneratedKeys();
      rs.next();
      auto_id = rs.getInt(1);
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    ///// RETURN Created /////

    filter = getFilterByFid(auto_id);
    return filter;
  }

  public SettingsDTO createSettings(SettingsDTO settings) throws SQLException, NamingException {
    int auto_id;

    try {
      openConnection();
      String query = "INSERT INTO Settings (uid, collapse_read_messages, all_or_whitelist, notify_messages, open_mark_read) VALUES (?, ?, ?, ?, ?)";
      stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, settings.getUid());
      stmt.setBoolean(2, settings.getCollapseReadMessages());
      stmt.setBoolean(3, settings.getAllOrWhitelist());
      stmt.setBoolean(4, settings.getUnreadNotify());
      stmt.setBoolean(5, settings.getMarkReadIfOpened());
      stmt.execute();
      rs = stmt.getGeneratedKeys();
      rs.next();
      auto_id = rs.getInt(1);
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    ///// RETURN Created /////

    settings = getSettingsBySid(auto_id);
    return settings;
  }

  public NotificationDTO createNotification(NotificationDTO notification) throws SQLException, NamingException {
    int auto_id;

    ///// NEED TO ADD ID NUM TYPE VALIDATION ?????? /////

    try {
      openConnection();
      String query = "INSERT INTO Notifications (uid, id_num, id_num_type) VALUES (?, ?, ?)";
      stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, notification.getUid());
      stmt.setInt(2, notification.getIdNum());
      stmt.setString(3, notification.getIdNumType());
      stmt.execute();
      rs = stmt.getGeneratedKeys();
      rs.next();
      auto_id = rs.getInt(1);
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    ///// RETURN Created /////

    notification = getNotificationByNid(auto_id);
    return notification;
  }

  ///// DELETES /////

  public void deleteFilterByFid(int fid) throws SQLException, NamingException {
    ///// DELETE RECORD FROM DB /////
    try {
      openConnection();
      String query = "DELETE FROM Filters WHERE filter_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, fid);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public void deleteFilterByUid(int uid) throws SQLException, NamingException {
    ///// DELETE RECORD FROM DB /////
    try {
      openConnection();
      String query = "DELETE FROM Filters WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }
/*
  public void deleteFilterByUidMidBa(int uid, int managed_uid, Boolean ba) throws SQLException, NamingException {
    ///// DELETE RECORD FROM DB /////
    try {
      openConnection();
      String query = "DELETE FROM Filters WHERE uid = ? AND managed_uid = ? AND block_allow = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      stmt.setInt(2, managed_uid);
      stmt.setBoolean(3, ba);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }
*/
  public void deleteNotificationByNid(int nid) throws SQLException, NamingException {
    ///// DELETE RECORD FROM DB /////
    try {
      openConnection();
      String query = "DELETE FROM Notifications WHERE notification_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, nid);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public void deleteNotificationsByUid(int uid) throws SQLException, NamingException {
    ///// DELETE RECORD FROM DB /////
    try {
      openConnection();
      String query = "DELETE FROM Notifications WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public void deleteSettingsByUid(int uid) throws SQLException, NamingException {
    ///// DELETE RECORD FROM DB /////
    try {
      openConnection();
      String query = "DELETE FROM Settings WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public void deleteMembershipByUid(int uid) throws SQLException, NamingException {
    ///// DELETE RECORD FROM DB /////
    try {
      openConnection();
      String query = "DELETE FROM Membership WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  ///// UPDATES /////

  public void updateMembership(MembershipDTO membership) throws SQLException, NamingException {
    ///// UPDATE RECORD IN DB /////
    try {
      openConnection();
      String query = "UPDATE Membership SET conversation_id = ?, uid = ?, unread_chain_num = ?, visible = ? WHERE membership_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, membership.getCid());
      stmt.setInt(2, membership.getUid());
      stmt.setInt(3, membership.getUnreadChainNum());
      stmt.setBoolean(4, membership.getVisible());
      stmt.setInt(5, membership.getMid());
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public void updateSettings(SettingsDTO settings) throws SQLException, NamingException {
    ///// UPDATE RECORD IN DB /////
    try {
      openConnection();
      String query = "UPDATE Settings SET uid = ?, collapse_read_messages = ?, all_or_whitelist = ?, notify_messages = ?, open_mark_read = ? WHERE settings_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, settings.getUid());
      stmt.setBoolean(2, settings.getCollapseReadMessages());
      stmt.setBoolean(3, settings.getAllOrWhitelist());
      stmt.setBoolean(4, settings.getUnreadNotify());
      stmt.setBoolean(5, settings.getMarkReadIfOpened());
      stmt.setInt(6, settings.getSid());
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public void updateConversation(ConversationDTO conversation) throws SQLException, NamingException {
    ///// UPDATE RECORD IN DB /////
    try {
      openConnection();
      String query = "UPDATE Conversations SET from_uid = ?, started = ?, most_recent = ? WHERE conversation_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, conversation.getUid());
      stmt.setTimestamp(2, Timestamp.from(conversation.getStarted()));
      stmt.setTimestamp(3, Timestamp.from(conversation.getRecent()));
      stmt.setInt(4, conversation.getCid());
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public void updateMessage(MessageDTO message) throws SQLException, NamingException, IllegalArgumentException {
    ///// VALIDATE INPUT /////

    if (message.getSubject().length() > 140) {
      throw new IllegalArgumentException("Invalid subject");
    } else if (message.getContent().length() > 10000) {
      throw new IllegalArgumentException("Invalid content");
    }

    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "UPDATE Messages SET conversation_id = ?, from_uid = ?, sent = ?, subject = ?, content = ?, chain_num = ? WHERE message_id = ?";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, message.getCid());
      stmt.setInt(2, message.getFromUid());
      stmt.setTimestamp(3, Timestamp.from(message.getSent()));
      stmt.setString(4, message.getSubject());
      stmt.setString(5, message.getContent());
      stmt.setInt(6, message.getChainNum());
      stmt.setInt(7, message.getMid());
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  ///// READS /////

  public MembershipDTO getMembershipByMid(int mid) throws SQLException, NamingException {
    MembershipDTO membership = new MembershipDTO();

    try {
      openConnection();
      String query = "SELECT * FROM Membership WHERE membership_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, mid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
      		membership.setMid(mid);
					membership.setCid(rs.getInt("conversation_id"));
          membership.setUid(rs.getInt("uid"));
          membership.setUnreadChainNum(rs.getInt("unread_chain_num"));
          membership.setVisible(rs.getBoolean("visible"));
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return membership;
  }

  public int getMembershipByUidCount(int uid) throws SQLException, NamingException {
    int count = 0;
    try {
      openConnection();
      String query = "SELECT COUNT(*) FROM Membership WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
          count = rs.getInt("COUNT(*)");
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return count;
  }

  ///// START 0 to get 1 - X /////
  public List<MembershipDTO> getOrderedMembershipByUid(int uid, int start, int returnCount) throws SQLException, NamingException, IllegalArgumentException {
    List<MembershipDTO> memberList = new ArrayList<MembershipDTO>();

    ///// VALIDATE RANGE /////

    int count = getMembershipByUidCount(uid);
    if (start + returnCount > count) {
      throw new IllegalArgumentException("Out of Range");
    }

    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "SELECT * FROM Membership WHERE uid = ? ORDER BY conversation_id LIMIT ?, ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      stmt.setInt(2, start);
      stmt.setInt(3, returnCount);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
          MembershipDTO member = new MembershipDTO();
          member.setMid(rs.getInt("membership_id"));
          member.setCid(rs.getInt("conversation_id"));
          member.setUid(rs.getInt("uid"));
          member.setUnreadChainNum(rs.getInt("unread_chain_num"));
          member.setVisible(rs.getBoolean("visible"));
          memberList.add(member);
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return memberList;

  }

  public ConversationDTO getConversationByCid(int cid) throws SQLException, NamingException {
    ConversationDTO conversation = new ConversationDTO();

    try {
      openConnection();
      String query = "SELECT * FROM Conversations WHERE conversation_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, cid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
					conversation.setCid(cid);
					conversation.setUid(rs.getInt("from_uid"));
          conversation.setStarted(rs.getTimestamp("started").toInstant());
          conversation.setRecent(rs.getTimestamp("most_recent").toInstant());
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return conversation;
  }

  public MessageDTO getMessageByMid(int mid) throws SQLException, NamingException {
    MessageDTO message = new MessageDTO();

    try {
      openConnection();
      String query = "SELECT * FROM Messages WHERE message_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, mid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
      		message.setMid(mid);
					message.setCid(rs.getInt("conversation_id"));
          message.setFromUid(rs.getInt("from_uid"));
          message.setSent(rs.getTimestamp("sent").toInstant());
          message.setSubject(rs.getString("subject"));
          message.setContent(rs.getString("content"));
          message.setChainNum(rs.getInt("chain_num"));
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return message;
  }

  public List<MessageDTO> getMessagesByCid(int cid) throws SQLException, NamingException {
    List<MessageDTO> messageList = new ArrayList<MessageDTO>();

    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "SELECT * FROM Messages WHERE conversation_id = ? ORDER BY chain_num;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, cid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
          MessageDTO message = new MessageDTO();
          message.setMid(rs.getInt("message_id"));
          message.setCid(rs.getInt("conversation_id"));
          message.setFromUid(rs.getInt("from_uid"));
          message.setSent(rs.getTimestamp("sent").toInstant());
          message.setSubject(rs.getString("subject"));
          message.setContent(rs.getString("content"));
          message.setChainNum(rs.getInt("chain_num"));
          messageList.add(message);
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return messageList;

  }

  public FilterDTO getFilterByFid(int fid) throws SQLException, NamingException {
    FilterDTO filter = new FilterDTO();

    try {
      openConnection();
      String query = "SELECT * FROM Filters WHERE filter_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, fid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
    			filter.setFid(fid);
					filter.setUid(rs.getInt("uid"));
          filter.setManagedUid(rs.getInt("managed_uid"));
          filter.setBlockAllow(rs.getBoolean("block_allow"));
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return filter;
  }

  public SettingsDTO getSettingsBySid(int sid) throws SQLException, NamingException {
    SettingsDTO settings = new SettingsDTO();

    try {
      openConnection();
      String query = "SELECT * FROM Settings WHERE settings_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, sid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
      		settings.setSid(sid);
					settings.setUid(rs.getInt("uid"));
          settings.setCollapseReadMessages(rs.getBoolean("collapse_read_messages"));
          settings.setAllOrWhitelist(rs.getBoolean("all_or_whitelist"));
          settings.setUnreadNotify(rs.getBoolean("notify_messages"));
          settings.setMarkReadIfOpened(rs.getBoolean("open_mark_read"));
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return settings;
  }

  public SettingsDTO getSettingsByUid(int uid) throws SQLException, NamingException {
    SettingsDTO settings = new SettingsDTO();

    try {
      openConnection();
      String query = "SELECT * FROM Settings WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
      		settings.setUid(uid);
					settings.setSid(rs.getInt("settings_id"));
          settings.setCollapseReadMessages(rs.getBoolean("collapse_read_messages"));
          settings.setAllOrWhitelist(rs.getBoolean("all_or_whitelist"));
          settings.setUnreadNotify(rs.getBoolean("notify_messages"));
          settings.setMarkReadIfOpened(rs.getBoolean("open_mark_read"));
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return settings;
  }

  public NotificationDTO getNotificationByNid(int nid) throws SQLException, NamingException {
    NotificationDTO notification = new NotificationDTO();

    try {
      openConnection();
      String query = "SELECT * FROM Notifications WHERE notification_id = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, nid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
      		notification.setNid(nid);
					notification.setUid(rs.getInt("uid"));
          notification.setIdNum(rs.getInt("id_num"));
          notification.setIdNumType(rs.getString("id_num_type"));
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return notification;
  }

  public List<NotificationDTO> getNotificationsByUid(int uid) throws SQLException, NamingException {
    List<NotificationDTO> notificationList = new ArrayList<NotificationDTO>();

    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "SELECT * FROM Notifications WHERE uid = ? ORDER BY notification_id;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
          NotificationDTO notification = new NotificationDTO();
          notification.setNid(rs.getInt("notification_id"));
          notification.setUid(rs.getInt("uid"));
          notification.setIdNum(rs.getInt("id_num"));
          notification.setIdNumType(rs.getString("id_num_type"));
          notificationList.add(notification);
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    return notificationList;
  }

}

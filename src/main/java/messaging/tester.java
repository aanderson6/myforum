package com.myforum.messaging;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import javax.naming.NamingException;

import java.time.Instant;
import java.util.List;

import com.myforum.login.DaoMySqlLogin;
import com.myforum.login.DaoInterfaceLogin;

import com.myforum.messaging.entities.MessageDTO;
import com.myforum.messaging.entities.ConversationDTO;
import com.myforum.messaging.entities.NotificationDTO;
import com.myforum.messaging.entities.FilterDTO;
import com.myforum.messaging.entities.MembershipDTO;
import com.myforum.messaging.entities.SettingsDTO;

import java.io.PrintWriter;
import java.io.StringWriter;

public class tester extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    /*response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.write("<html><body>HELLO! <br>");
    out.write("testing13<br><br><br>");
     
    
		try {

			DaoInterfaceMessaging testMessagingDAO = new DaoMySqlMessaging();
			
			///// TEST CREATES AND GETS /////
			
			ConversationDTO conversation1 = new ConversationDTO(8, Instant.now(), Instant.now());
			ConversationDTO conversation1updated = testMessagingDAO.createConversation(conversation1);

			MembershipDTO membership1 = new MembershipDTO(conversation1updated.getCid(), 8, 0, true);
			MembershipDTO membership1updated = testMessagingDAO.createMembership(membership1);
			
			MessageDTO message1 = new MessageDTO(conversation1updated.getCid(), 8, Instant.now(), "Subject Test", "Content Test", 1);
			MessageDTO message1updated = testMessagingDAO.createMessage(message1);

			FilterDTO filter1 = new FilterDTO(8, 10, false);
			FilterDTO filter1updated = testMessagingDAO.createFilter(filter1);

			SettingsDTO settings1 = new SettingsDTO(8, true, true, true, true);
			SettingsDTO settings1updated = testMessagingDAO.createSettings(settings1);

			NotificationDTO notification1 = new NotificationDTO(8, 6, "Message");
			NotificationDTO notification1updated = testMessagingDAO.createNotification(notification1);

			
			ConversationDTO conversation2 = testMessagingDAO.getConversationByCid(conversation1updated.getCid());
			MembershipDTO membership2 = testMessagingDAO.getMembershipByMid(membership1updated.getMid());
			MessageDTO message2 = testMessagingDAO.getMessageByMid(message1updated.getMid());
			FilterDTO filter2 = testMessagingDAO.getFilterByFid(filter1updated.getFid());
			SettingsDTO settings2 = testMessagingDAO.getSettingsBySid(settings1updated.getSid());
			NotificationDTO notification2 = testMessagingDAO.getNotificationByNid(notification1updated.getNid());

			if (conversation2.getUid() == 8 && conversation2.getStarted().equals(conversation1.getStarted()) && conversation2.getRecent().equals(conversation1.getRecent())) {
				out.write("Create and Get Conversation worked<br>");
			} else {
				out.write("Create and Get Conversation broke<br>");
			}

			if (membership2.getCid() == membership1.getCid() && membership2.getUid() == 8 && membership2.getUnreadChainNum() == membership1.getUnreadChainNum() && membership2.getVisible() == membership1.getVisible()) { 
				out.write("Create and Get Membership worked<br>");
			} else {
				out.write("Create and Get Membership broke<br>");
			}

			if (message2.getCid() == message1.getCid() && message2.getFromUid() == 8 && message2.getSent().equals(message1.getSent()) && message2.getSubject().equals(message1.getSubject()) && message2.getContent().equals(message1.getContent()) && message2.getChainNum() == message1.getChainNum()) {
				out.write("Create and Get Message worked<br>");
			} else {
				out.write("Create and Get Message broke<br>");
			}

			if (filter2.getUid() == 8 && filter2.getManagedUid() == filter1.getManagedUid() && filter2.getBlockAllow() == filter1.getBlockAllow()) {
				out.write("Create and Get Filter worked<br>");
			} else {
				out.write("Create and Get Filter broke<br>");
			}


			if (settings2.getUid() == 8 && settings2.getCollapseReadMessages() == settings1.getCollapseReadMessages() && settings2.getAllOrWhitelist() == settings1.getAllOrWhitelist() && settings2.getUnreadNotify() == settings1.getUnreadNotify() && settings2.getMarkReadIfOpened() == settings1.getMarkReadIfOpened()) {
				out.write("Create and Get Settings worked<br>");
			} else {
				out.write("Create and Get Settings broke<br>");
			}
			

			if (notification2.getUid() == 8 && notification2.getIdNum() == notification1.getIdNum() && notification2.getIdNumType().equals(notification1.getIdNumType())) {
				out.write("Create and Get Notification worked<br>");
			} else {
				out.write("Create and Get Notification broke<br>");
			}
			
			///// UPDATES /////
		
			conversation2.setUid(11);
			testMessagingDAO.updateConversation(conversation2);

			membership2.setUid(11);
			testMessagingDAO.updateMembership(membership2);
			
			message2.setFromUid(11);
			testMessagingDAO.updateMessage(message2);

			settings2.setUid(11);
			testMessagingDAO.updateSettings(settings2);

			
			ConversationDTO conversation3 = testMessagingDAO.getConversationByCid(conversation2.getCid());
			MembershipDTO membership3 = testMessagingDAO.getMembershipByMid(membership2.getMid());
			MessageDTO message3 = testMessagingDAO.getMessageByMid(message2.getMid());
			SettingsDTO settings3 = testMessagingDAO.getSettingsBySid(settings2.getSid());
		

			
			if (conversation3.getUid() == 11 && conversation3.getStarted().equals(conversation2.getStarted()) && conversation3.getRecent().equals(conversation2.getRecent())) {
				out.write("Update Conversation worked<br>");
			} else {
				out.write("Update Conversation broke<br>");
			}


			if (membership3.getCid() == membership2.getCid() && membership3.getUid() == 11 && membership3.getUnreadChainNum() == membership2.getUnreadChainNum() && membership3.getVisible() == membership2.getVisible()) { 
				out.write("Update Membership worked<br>");
			} else {
				out.write("Update Membership broke<br>");
			}


			if (message3.getCid() == message2.getCid() && message3.getFromUid() == 11 && message3.getSent().equals(message2.getSent()) && message3.getSubject().equals(message2.getSubject()) && message3.getContent().equals(message2.getContent()) && message3.getChainNum() == message2.getChainNum()) {
				out.write("Update Message worked<br>");
			} else {
				out.write("Update Message broke<br>");
			}


			if (settings3.getUid() == 11 && settings3.getCollapseReadMessages() == settings2.getCollapseReadMessages() && settings3.getAllOrWhitelist() == settings2.getAllOrWhitelist() && settings3.getUnreadNotify() == settings2.getUnreadNotify() && settings3.getMarkReadIfOpened() == settings2.getMarkReadIfOpened()) {
				out.write("Update Settings worked<br>");
			} else {
				out.write("Update Settings broke<br>");
			}
			

			///// SELECTS /////


			int count = testMessagingDAO.getMembershipByUidCount(11);
			List<MembershipDTO> membershipList = testMessagingDAO.getOrderedMembershipByUid(11, 0, 1);
			List<MessageDTO> messageList = testMessagingDAO.getMessagesByCid(message2.getCid());
			SettingsDTO settings5 = testMessagingDAO.getSettingsByUid(11);
			List<NotificationDTO> notificationList = testMessagingDAO.getNotificationsByUid(8);
			


			if (count == 1) {
				out.write("Get Membership Count worked<br>");
			} else {
				out.write("Get Membership Count broke<br>");
			}


			if (membership2.getCid() == membershipList.get(0).getCid() && membershipList.get(0).getUid() == 11 && membership2.getUnreadChainNum() == membershipList.get(0).getUnreadChainNum() && membership2.getVisible() == membershipList.get(0).getVisible()) { 
				out.write("Get List Membership worked<br>");
			} else {
				out.write("Get List Membership broke<br>");
			}


			if (message2.getCid() == messageList.get(0).getCid() && messageList.get(0).getFromUid() == 11 && message2.getSent().equals(messageList.get(0).getSent()) && message2.getSubject().equals(messageList.get(0).getSubject()) && message2.getContent().equals(messageList.get(0).getContent()) && message2.getChainNum() == messageList.get(0).getChainNum()) {
				out.write("Message List worked<br>");
			} else {
				out.write("Message List broke<br>");
			}


			if (settings5.getUid() == 11 && settings2.getCollapseReadMessages() == settings5.getCollapseReadMessages() && settings2.getAllOrWhitelist() == settings5.getAllOrWhitelist() && settings2.getUnreadNotify() == settings5.getUnreadNotify() && settings2.getMarkReadIfOpened() == settings5.getMarkReadIfOpened()) {
				out.write("Settings by Uid worked<br>");
			} else {
				out.write("Settings by Uid broke<br>");
			}
			

			if (notificationList.get(0).getUid() == 8 && notification2.getIdNum() == notificationList.get(0).getIdNum() && notification2.getIdNumType().equals(notificationList.get(0).getIdNumType())) {
				out.write("Notification List worked<br>");
			} else {
				out.write("Notification List broke<br>");
			}

			///// DELETES /////

			// First, verify they exist "XYZ verified in DB." then delete and verify they do not exist "XYZ verified deleted."
			
			
			// Create

			MembershipDTO dMembership1 = testMessagingDAO.createMembership(new MembershipDTO(conversation1updated.getCid(), 8, 0, true));
			MembershipDTO tMembership1 = testMessagingDAO.getMembershipByMid(dMembership1.getMid());

			// Verify, then delete and verify
			
			if (dMembership1.getMid() == tMembership1.getMid()) {
				out.write("Created Membership to Test Delete");
			} else {
				out.write("Failed to create Membership to test Delete");
			}
			
			testMessagingDAO.deleteMembershipByUid(8);

			if (testMessagingDAO.getMembershipByMid(dMembership1.getMid()).isEmpty()) {
				out.write(" - Deletion of Membership Successful<br>");
			} else {
				out.write(" - Deletion of Membership Failed<br>"); 
			}


			// Create
			
			FilterDTO dFilter1 = testMessagingDAO.createFilter(new FilterDTO(8, 10, false));
			FilterDTO tFilter1 = testMessagingDAO.getFilterByFid(dFilter1.getFid());
			
			// Verify, then delete and verify
			
			if (dFilter1.getFid() == tFilter1.getFid()) {
				out.write("Created Filter1 to Test Delete");
			} else {
				out.write("Failed to create Filter1 to test Delete");
			}
			
			testMessagingDAO.deleteFilterByFid(dFilter1.getFid());

			if (testMessagingDAO.getFilterByFid(dFilter1.getFid()).isEmpty()) {
				out.write(" - Deletion of Filter1 Successful<br>");
			} else {
				out.write(" - Deletion of Filter1 Failed<br>"); 
			}

			// Create
			
			FilterDTO dFilter2 = testMessagingDAO.createFilter(new FilterDTO(8, 10, false));
			FilterDTO tFilter2 = testMessagingDAO.getFilterByFid(dFilter2.getFid());
		
			// Verify, then delete and verify
			
			if (dFilter2.getFid() == tFilter2.getFid()) {
				out.write("Created Filter2 to Test Delete");
			} else {
				out.write("Failed to create Filter2 to test Delete");
			}
			
			testMessagingDAO.deleteFilterByUid(8);

			if (testMessagingDAO.getFilterByFid(dFilter2.getFid()).isEmpty()) {
				out.write(" - Deletion of Filter2 Successful<br>");
			} else {
				out.write(" - Deletion of Filter2 Failed<br>"); 
			}
			
			// Create
			
			NotificationDTO dNotification1 = testMessagingDAO.createNotification(new NotificationDTO(8, 6, "Message"));
			NotificationDTO tNotification1 = testMessagingDAO.getNotificationByNid(dNotification1.getNid());		
			
			// Verify, then delete and verify
			
			if (dNotification1.getNid() == tNotification1.getNid()) {

				out.write("Created Notification1 to Test Delete");
			} else {
				out.write("Failed to create Notification1 to test Delete");
			}
			
			testMessagingDAO.deleteNotificationByNid(dNotification1.getNid());

			if (testMessagingDAO.getNotificationByNid(dNotification1.getNid()).isEmpty()) {
				out.write(" - Deletion of Notification1 Successful<br>");
			} else {
				out.write(" - Deletion of Notification1 Failed<br>"); 
			}
			
			// Create
			
			NotificationDTO dNotification2 = testMessagingDAO.createNotification(new NotificationDTO(8, 6, "Message"));
			NotificationDTO tNotification2 = testMessagingDAO.getNotificationByNid(dNotification2.getNid());		
			
			// Verify, then delete and verify
			
			if (dNotification2.getNid() == tNotification2.getNid()) {
				out.write("Created Notification2 to Test Delete");
			} else {
				out.write("Failed to create Notification2 to test Delete");
			}
			
			testMessagingDAO.deleteNotificationsByUid(8);

			if (testMessagingDAO.getNotificationByNid(dNotification2.getNid()).isEmpty()) {
				out.write(" - Deletion of Notification2 Successful<br>");
			} else {

				out.write(" - Deletion of Notification2 Failed<br>"); 
			}
			
			// Create
			
			SettingsDTO dSettings1 = testMessagingDAO.createSettings(new SettingsDTO(15, true, true, true, true));
			SettingsDTO tSettings1 = testMessagingDAO.getSettingsBySid(dSettings1.getSid());

			// Verify, then delete and verify
			
			if (dSettings1.getSid() == tSettings1.getSid()) {
				out.write("Created Settings to Test Delete");
			} else {
				out.write("Failed to create Settings to test Delete");
			}
			
			testMessagingDAO.deleteSettingsByUid(15);

			if (testMessagingDAO.getSettingsBySid(dSettings1.getSid()).isEmpty()) {
				out.write(" - Deletion of Settings Successful<br>");
			} else {
				out.write(" - Deletion of Settings Failed<br>"); 
			}




    } catch (IllegalArgumentException|SQLException|NamingException ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);	
			
			out.write(sw.toString());
    }
    

    out.write("FINISHED!");*/
  }
}

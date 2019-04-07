/*package com.myforum.messaging

import org.junit.Assert.assertEquals;
import org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import com.myforum.messaging.entities.*;
import com.myforum.login.models.*;

import java.util.List;
import java.util.ArrayList;

public class TestMessagingService {

	DaoInterfaceMessaging testMessagingInterface;
	DaoInterfaceLogin testLoginInterface;

	@BeforeClass
	public void setup() {
		// Create Dummy Entities

		UserModel testUser1 = new UserModel(...);
		UserModel testUser2 = new UserModel(...);
		SettingsDTO testSettings1 = new SettingsDTO(...);
		NotificationDTO testNotification1 = new NotificationDTO(...);
		NotificationDTO testNotification2 = new NotificationDTO(...);
		MessageDTO testMessage1 = new MessageDTO(...);
		MessageDTO testMessage2 = new MessageDTO(...);
		MembershipDTO testMembership1 = new MembershipDTO(...);
		FilterDTO testFilter1 = new FilterDTO(...);
		ConversationDTO testConversation1 = new ConversationDTO(...);

		// Create mock

		testMessagingInterface = mock(DaoInterfaceMessaging.class);
		testLoginInterface = mock(DaoInterfaceLogin.class);

		// Generating Mock Data

		when(testMessagingInterface.getFiltersByUid("Test4")).thenReturn(new ArrayList<FilterDTO>(...));
		/// Create the above method in Messaging Interface and DAO!
		when(testMessagingInterface.getNotificationByMid(123).thenReturn(new NotificationDTO(...));
		/// need to create this method...
		when(testMessagingInterface.getMembershipsByCid(123)).thenReturn(new ArrayList<MembershipDTO>(...));
		// need to create this method
		/// ALSO need to edit db and DAO to allow 'conversation' to hold most recent chain number...


		/////////// CREATE VALID TEST DATA FOR MOCK //////////

		// For startConversation
		UserModel testUser1 = new UserModel(...);
		ConversationDTO testConversation1 = new ConversationDTO(...);
		MembershipDTO testMembership1 = new MembershipDTO(...);
		MessageDTO testMessage1 = new MessageDTO(...);
		NotificationDTO testNotification1 = new NotificationDTO(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser1);
		when(testMessagingInterface.createConversation(new ConversationDTO(...))).thenReturn(testConversation1);
		when(testMessagingInterface.createMembership(new MembershipDTO(...))).thenReturn(testMembership1);
		when(testMessatingInterface.createMessage(new MessageDTO(...))).thenReturn(testMessage1);
		when(testMessagingInterface.createNotification(new NotificationDTO(...))).thenReturn(testNotification1);

		// For replyMessage
		UserModel testUser2 = new UserModel(...);
		MessageDTO testMessage2 = new MessageDTO(...);
		NotificationDTO testNotification2 = new NotificationDTO(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser2);
		when(testMessatingInterface.createMessage(new MessageDTO(...))).thenReturn(testMessage2);
		when(testMessagingInterface.createNotification(new NotificationDTO(...))).thenReturn(testNotification2);

		// For getOrderedConversationsRange
		UserModel testUser3 = new UserModel(...);
		List<MembershipDTO> testMembershipList2 = new ArrayList<MembershipDTO>(...);
		List<FilterDTO> testFiltersList1 = new ArrayList<FilterDTO>(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser3);
		when(testMessagingInterface.getOrderedMembershipByUid(123, x, y)).thenReturn(testMembershipList2);
		when(testMessagingInterface.getFiltersByUid("Test4")).thenReturn(testFiltersList1);

		// For getMessagesByConversation
		List<MessageDTO> testMessageList3 = new ArrayList<MessageDTO>(...);
		List<FilterDTO> testFiltersList2 = new ArrayList<FilterDTO>(...);
		when(testMessagingInterface.getMessagesByCid(123)).thenReturn(testMessageList3);
		when(testMessagingInterface.getFiltersByUid("Test4")).thenReturn(testFiltersList2);

		// For getMessage
		MessageDTO testMessage4 = new MessageDTO(...);
		when(testMessagingInterface.getMessagesByMid(123)).thenReturn(testMessage4);

		// For hideConversationByUser
		UserModel testUser4 = new UserModel(...);
		MembershipDTO testMembership3 = new MembershipDTO(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser4);
		doNothing().when(testMessagingInterface).updateMembership(testMembership3);
		// verify(testMessagingInterface, atLeastOnce()).updateMembership(testMembership3);

		// For hasReadMessage
		UserModel testUser5 = new UserModel(...);
		List<NotificationDTO> testNotificationList3 = new ArrayList<NotificationDTO>(...);
		MembershipDTO testMembership4 = new MembershipDTO(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser5);
		when(testMessagingInterface.getNotificationsByUid(123).thenReturn(testNotificationList3);
		doNothing().when(testMessagingInterface).deleteNotificationByNid(123);
		// verify(testMessagingInterface, atLeastOnce()).deleteNotificationByNid(123);
		doNothing().when(testMessagingInterface).updateMembership(testMembership4);
		// verify(testMessagingInterface, atLeastOnce()).updateMembership(testMembership4);

		// For updateSettings
		UserModel testUser6 = new UserModel(...);
		SettingsDTO testSettings1 = new SettingsDTO(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser6);
		doNothing().when(testMessagingInterface).updateSettings(testSettings1);
		// verify(testMessagingInterface, atLeastOnce()).updateSettings(testSettings1);

		// For addFilter
		UserModel testUser7 = new UserModel(...);
		FilterDTO testFilter3 = new FilterDTO(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser7);
		when(testMessagingInterface.createFilter(new FilterDTO(...))).thenReturn(testFilter2);

		// For removeFilter
		UserModel testUser8 = new UserModel(...);
		List<FilterDTO> testFiltersList4 = new ArrayList<FilterDTO>(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser8);
		when(testMessagingInterface.getFiltersByUid("Test4")).thenReturn(testFiltersList4);
		doNothing().when(testMessagingInterface).deleteFilterByFid(123);
		// verify(testMessagingInterface, atLeastOnce()).deleteFilterByFid(123);

		// For getSettings
		UserModel testUser9 = new UserModel(...);
		SettingsDTO testSettings2 = new SettingsDTO(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser9);
		when(testMessagingInterface.getSettingsByUid(123)).thenReturn(testSettings2);

		// For getFilters
		UserModel testUser10 = new UserModel(...);
		List<FilterDTO> testFiltersList5 = new ArrayList<FilterDTO>(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser10);
		when(testMessagingInterface.getFiltersByUid("Test4")).thenReturn(testFiltersList5);

		// For getNotifications
		UserModel testUser11 = new UserModel(...);
		List<NotificationDTO> testNotificationList4 = new ArrayList<NotificationDTO>(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser11);
		when(testMessagingInterface.getNotificationsByUid(123).thenReturn(testNotificationList4);

		// For getConversationCount
		UserModel testUser12 = new UserModel(...);
		when(testLoginInterface.getUserByUsername("asdf")).thenReturn(testUser12);
		when(testMessagingInterface.getMembershipByUidCount(123)).thenReturn(3);




		// Hand mock DAOs over to Service to use for testing...

		////////
	}


	//  VALID INPUT CHECKS

		// StartConversation

	@Test
	public void testStartConversationValidInputOneToUser() {

	}

	@Test
	public void testStartConversationValidInputMultiToUser() {

	}

		// ReplyMessage

	@Test
	public void testReplyMessageValidInput() {

	}

		// GetOrderedConversationsRange

	@Test
	public void testGetORderedConversationRangeValidInput() {

	}

		// GetMessagesByConversation

	@Test
	public void testGetMessagesByConversationValidInput() {

	}

		// GetMessage

	@Test
	public void testGetMessageValidInput() {

	}

		// HideConversationByUser

	@Test
	public void testHideConversationByUserValidInput() {

	}

		// HasReadMessage

	@Test
	public void testHasReadMessageValidInput() {

	}

		// UpdateSettings

	@Test
	public void testUpdateSettingsValidInput() {

	}

		// AddFilter

	@Test
	public void testAddFilterValidInput() {

	}

		// RemoveFilter

	@Test
	public void testRemoveFilterValidInput() {

	}

		// GetSettings

	@Test
	public void testGetSettingsValidInput() {

	}

		// GetFilters

	@Test
	public void testGetFiltersValidInput() {

	}

		// GetNotifications

	@Test
	public void testGetNotificationsValidInput() {

	}

		// GetConversationCount

	@Test
	public void testGetConversationCountValidInput() {

	}

	//  CONTENT INVALID INPUT CHECKS (invalid input that requires a mock db check)

		// StartConversation

	@Test(expected = IllegalArgumentException.class)
	public void testStartConversationFromUserDoesntExist() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testStartConversationToUserDoesntExist() {

	}

		// ReplyMessage

	@Test(expected = IllegalArgumentException.class)
	public void testReplyMessageFromUserDoesntExist() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testReplyMessageCidDoesntExist() {

	}

		// GetOrderedConversationsRange

	@Test(expected = IllegalArgumentException.class)
	public void testGetOrderedConversationsRangeUserDoesntExist() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetOrderedConversationsRangeEndPastLength() {

	}

		// GetMessagesByConversation

	@Test(expected = IllegalArgumentException.class)
	public void testGetMessageByConversationNoCid() {

	}

		// GetMessage

	@Test(expected = IllegalArgumentException.class)
	public void testGetMessageNoMid() {

	}

		// HideConversationByUser

	@Test(expected = IllegalArgumentException.class)
	public void testHideConversationByUserNoUser() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testHideConversationByUserNoCid() {

	}

		// HasReadMessage

	@Test(expected = IllegalArgumentException.class)
	public void testHasReadMessageNoUser() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasReadMessageNoMid() {

	}

		// UpdateSettings

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateSettingsNoUser() {

	}

		// AddFilter

	@Test(expected = IllegalArgumentException.class)
	public void testAddFilterNoUser() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddFilterNoManagedUser() {

	}

		// RemoveFilter

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveFilterNoUser() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveFilterNoManagedUser() {

	}

		// GetSettings

	@Test(expected = IllegalArgumentException.class)
	public void testGetSettingsNoUser() {

	}

		// GetFilters

	@Test(expected = IllegalArgumentException.class)
	public void testGetFiltersNoUser() {

	}

		// GetNotifications

	@Test(expected = IllegalArgumentException.class)
	public void testGetNotificationsNoUser() {

	}

		// GetConversationCount

	@Test(expected = IllegalArgumentException.class)
	public void testGetConversationCountNoUser() {

	}

	// 	SHAPE INVALID INPUT CHECKS (invalid input that doesn't require mock db check) - return false for invalid input

	@Test(expected = IllegalArgumentException.class)
	public void testStartConversationEmptyUserList() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetOrderedConversationsRangeStartNegative() {

	}


	// NULL CHECKS

		// startConversation

	@Test(expected = NullPointerException.class)
	public void testStartConversationNullFromUser() {

	}


	@Test(expected = NullPointerException.class)
	public void testStartConversationNullToUserList() {

	}


	@Test(expected = NullPointerException.class)
	public void testStartConversationNullSubject() {

	}


	@Test(expected = NullPointerException.class)
	public void testStartConversationNullContent() {

	}

		// replyMessage

	@Test(expected = NullPointerException.class)
	public void testReplyMessageNullFromUser() {

	}


	@Test(expected = NullPointerException.class)
	public void testReplyMessageNullCid() {

	}


	@Test(expected = NullPointerException.class)
	public void testReplyMessageNullMessageContent() {

	}

		// getOrderedConversationsRange

	@Test(expected = NullPointerException.class)
	public void testGetOrderedConversationsRangeNullUser() {

	}

	@Test(expected = NullPointerException.class)
	public void testGetOrderedConversationsRangeNullStart() {

	}


	@Test(expected = NullPointerException.class)
	public void testGetOrderedConversationsRangeNullEnd() {

	}

		// getMessagesByConversation

	@Test(expected = NullPointerException.class)
	public void testGetMessagesByConversationNullCid() {

	}

		// getMessage

	@Test(expected = NullPointerException.class)
	public void testGetMessageNullMid() {

	}

		// hideConversationByUser

	@Test(expected = NullPointerException.class)
	public void testHideConversationByUserNullUser() {

	}


	@Test(expected = NullPointerException.class)
	public void testHideConversationByUserNullCid() {

	}

		// hasReadMessage

	@Test(expected = NullPointerException.class)
	public void testHasReadMessageNullUser() {

	}

	@Test(expected = NullPointerException.class)
	public void testHasReadMessageNullMid() {

	}

		// updateSettings

	@Test(expected = NullPointerException.class)
	public void testUpdateSettingsNullUser() {

	}

	@Test(expected = NullPointerException.class)
	public void testUpdateSettingsNullCollapseReadMessages() {

	}

	@Test(expected = NullPointerException.class)
	public void testUpdateSettingsNullAllOrWhiteList() {

	}

	@Test(expected = NullPointerException.class)
	public void testUpdateSettingsNullNotifyUnreadMessages() {

	}

	@Test(expected = NullPointerException.class)
	public void testUpdateSettingsNullMarkReadOpened() {

	}

		// addFilter

	@Test(expected = NullPointerException.class)
	public void testAddFilterNullUser() {

	}

	@Test(expected = NullPointerException.class)
	public void testAddFilterNullManagedUser() {

	}

	@Test(expected = NullPointerException.class)
	public void testAddFilterNullBlockAllow() {

	}

		// removeFilter

	@Test(expected = NullPointerException.class)
	public void testRemoveFilterNullUser() {

	}

	@Test(expected = NullPointerException.class)
	public void testRemoveFilterNullManagedUser() {

	}

	@Test(expected = NullPointerException.class)
	public void testRemoveFilterNullBlockAllow() {

	}

		// getSettings

	@Test(expected = NullPointerException.class)
	public void testGetSettingsNullUser() {

	}

		// getFilters()

	@Test(expected = NullPointerException.class)
	public void testGetFiltersNullUser() {

	}

		// getNotifications

	@Test(expected = NullPointerException.class)
	public void testGetNotificationsNullUser() {

	}

		// getConversationCount

	@Test(expected = NullPointerException.class)
	public void testGetConversationCountNullUser() {

	}








	@AfterClass
	public void teardown() {

	}

}
*/

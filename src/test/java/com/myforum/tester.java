/*package com.myforum;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*import com.myforum.login.entities.*;
import com.myforum.login.repositories.*;

import com.myforum.messaging.entities.*;
import com.myforum.messaging.repositories.*;

import com.myforum.subforum.entities.*;
import com.myforum.subforum.repositories.*;

import com.myforum.posts.entities.*;
import com.myforum.posts.repositories.*;

import com.myforum.comments.entities.*;
import com.myforum.comments.repositories.*; */
/*
import com.myforum.login.dtos.*;
import com.myforum.messaging.dtos.*;
import com.myforum.subforum.dtos.*;
import com.myforum.posts.dtos.*;
import com.myforum.comments.dtos.*;
import com.myforum.login.*;
import com.myforum.messaging.*;
import com.myforum.subforum.*;
import com.myforum.posts.*;
import com.myforum.comments.*;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

public class tester extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.write("<html><body>START TEST<br>");

    LoginInterface loginService = new LoginService();
    MessagingInterface messagingService = new MessagingService();
    SubforumInterface subforumService = new SubforumService();
    PostInterface postService = new PostService();
    CommentInterface commentService = new CommentService();
*/
    // next, test comment service...
    /*
    int commentId = commentService.createComment("username1", "subforum1", "testpost", "this is my post!");
    int commentId2 = commentService.createComment("username2", "subforum1", "testpost2", "this is my post2!");
    commentService.updateCommentContent(commentId, "new content", "username1");

    int commentKarma = commentService.getById(commentId).getKarma();
    out.write(commentKarma + "<br>");

    commentService.removeUserFromComment(commentId, "username1");
    commentKarma = commentService.getById(commentId).getKarma();
    out.write(commentKarma + "<br>");

    commentService.upvoteComment(commentId, "username2");
    commentKarma = commentService.getById(commentId).getKarma();
    out.write(commentKarma + "<br>");
    commentService.upvoteComment(commentId, "username3");
    commentKarma = commentService.getById(commentId).getKarma();
    out.write(commentKarma + "<br>");
    commentService.downvoteComment(commentId, "username3");
    commentKarma = commentService.getById(commentId).getKarma();
    out.write(commentKarma + "<br>");

    commentService.removeVoteOnComment(commentId, "username2");
    commentKarma = commentService.getById(commentId).getKarma();
    out.write(commentKarma + "<br>");
*/





  //  public CommentDTO getById(int id) throws IllegalArgumentException;
  //  public List<CommentDTO> getByUserOrderedPaginated(String username, int start, int returnCount) throws IllegalArgumentException;
  //  public int getCountByUser(String username) throws IllegalArgumentException;
  //  public List<CommentDTO> getByPost(int postId, String requestingUsername, int start, int returnCount, int depth) throws IllegalArgumentException;
  //  public int getCountByPostFiltered(int postId, String requestingUsername) throws IllegalArgumentException;
  //  public List<CommentDTO> getChildrenOf(int commentId, String requestingUsername, int start, int returnCount, int depth) throws IllegalArgumentException;
  //  public int getCountChildrenOf(int commentId, String requestingUsername) throws IllegalArgumentException;

    // need to rewrite these to work with comment get methods instead of post methods
/*
    int commentCount = commentService.getCountBySubforumFiltered("subforum1", "username2");
    List<CommentDTO> commentList = commentService.getBySubforumOrderedPaginated("subforum1", 0, postCount, "username2");
    int commentCount2 = commentService.getCountByUser("username1");
    List<CommentDTO> commentList2 = commentService.getByUserOrderedPaginated("username1", 0, postCount2);

    int commentCount3 = commentService.getCountForUserSubscribed("username2");
    List<CommentDTO> commentList3 = commentService.getForUserSubscribedOrderedPaginated("username2", 0, postCount3);

    CommentDTO comment = commentService.getById(commentId);

    out.write(Boolean.toString(post.getTitle().equals("updatetitle")) + "<br>");
    out.write(Boolean.toString(post.getContent().equals("new content")) + "<br>");
    out.write(Boolean.toString(post.getUsername().equals("[deleted]")) + "<br>");
    out.write(Boolean.toString(post.getKarma() == -1) + "<br>");
    out.write(Boolean.toString(postCount == 2) + "<br>");
    out.write(Boolean.toString(postCount2 == 0) + "<br>");
    out.write(Boolean.toString(postCount3 == 2) + "<br>");
    out.write(Boolean.toString(postList.size() == 2) + "<br>");
    out.write(Boolean.toString(postList2.size() == 0) + "<br>");
    out.write(Boolean.toString(postList3.size() == 2) + "<br>");



*/
















































/*
    int postId = postService.createPost("username1", "subforum1", "testpost", "this is my post!");
    int postId2 = postService.createPost("username2", "subforum1", "testpost2", "this is my post2!");
    postService.updatePostTitle(postId, "updatetitle", "username1");
    postService.updatePostContent(postId, "new content", "username1");

    int postKarma = postService.getById(postId).getKarma();
    out.write(postKarma + "<br>");

    postService.removeUserFromPost(postId, "username1");
    postKarma = postService.getById(postId).getKarma();
    out.write(postKarma + "<br>");

    postService.upvotePost(postId, "username2");
    postKarma = postService.getById(postId).getKarma();
    out.write(postKarma + "<br>");
    postService.upvotePost(postId, "username3");
    postKarma = postService.getById(postId).getKarma();
    out.write(postKarma + "<br>");
    postService.downvotePost(postId, "username3");
    postKarma = postService.getById(postId).getKarma();
    out.write(postKarma + "<br>");

    postService.removeVoteOnPost(postId, "username2");
    postKarma = postService.getById(postId).getKarma();
    out.write(postKarma + "<br>");

    int postCount = postService.getCountBySubforumFiltered("subforum1", "username2");
    List<PostDTO> postList = postService.getBySubforumOrderedPaginated("subforum1", 0, postCount, "username2");
    int postCount2 = postService.getCountByUser("username1");
    List<PostDTO> postList2 = postService.getByUserOrderedPaginated("username1", 0, postCount2);

    int postCount3 = postService.getCountForUserSubscribed("username2");
    List<PostDTO> postList3 = postService.getForUserSubscribedOrderedPaginated("username2", 0, postCount3);

    PostDTO post = postService.getById(postId);

    out.write(Boolean.toString(post.getTitle().equals("updatetitle")) + "<br>");
    out.write(Boolean.toString(post.getContent().equals("new content")) + "<br>");
    out.write(Boolean.toString(post.getUsername().equals("[deleted]")) + "<br>");
    out.write(Boolean.toString(post.getKarma() == -1) + "<br>");
    out.write(Boolean.toString(postCount == 2) + "<br>");
    out.write(Boolean.toString(postCount2 == 0) + "<br>");
    out.write(Boolean.toString(postCount3 == 2) + "<br>");
    out.write(Boolean.toString(postList.size() == 2) + "<br>");
    out.write(Boolean.toString(postList2.size() == 0) + "<br>");
    out.write(Boolean.toString(postList3.size() == 2) + "<br>");


*/












































































/*
    subforumService.createSubforum("username1", "subforum1", "this is a subforum", "cool space", "don't break my rules!");
    subforumService.createSubforum("username1", "subforum2", "this is also sub", "cool", "rules");

    subforumService.updateSubforumDescription("subforum1", "maybe it's not");
    subforumService.updateSubforumFlair("subforum1", "no no");
    subforumService.updateSubforumRules("subforum1", "new rules");

    subforumService.subscribeToSubforum("username1", "subforum1");
    subforumService.unsubscribeFromSubforum("username1", "subforum1");
    subforumService.subscribeToSubforum("username2", "subforum1");

    subforumService.addModToSubforum("username1", "subforum1");
    subforumService.addModToSubforum("username2", "subforum1");
    subforumService.removeModFromSubforum("username2", "subforum1");

    Boolean shouldBeTrue1 = subforumService.isMod("subforum1", "username1");
    Boolean shouldBeFalse1 = subforumService.isMod("subforum1", "username2");

    Boolean shouldBeTrue2 = subforumService.isSubscribed("subforum1", "username2");
    Boolean shouldBeFalse2 = subforumService.isSubscribed("subforum1", "username1");

    int countSubsShouldBe2 = subforumService.getSubforumsCount();

    Boolean shouldBeTrue3 = subforumService.subforumExists("subforum1");
    Boolean shouldBeFalse3 = subforumService.subforumExists("subforum3");

    SubforumDTO sub1 = subforumService.getSubforumByName("subforum1");

    List<ModDTO> modList = subforumService.getModsBySubforumOrderedByRank("subforum1");
    List<SubforumDTO> subList = subforumService.getSubforumSubscriptionsByUser("username1");
    List<SubforumDTO> subList2 = subforumService.getSubforumsOrderedPaginated(0, countSubsShouldBe2);

    out.write(Boolean.toString(sub1.getDescription().equals("maybe it's not")) + "<br>");
    out.write(Boolean.toString(sub1.getFlair().equals("no no")) + "<br>");
    out.write(Boolean.toString(subforumService.getSubSettingsByName(sub1.getName()).getRules().equals("new rules")) + "<br>");

    out.write(Boolean.toString(shouldBeTrue1 == true) + "<br>");
    out.write(Boolean.toString(shouldBeTrue2 == true) + "<br>");
    out.write(Boolean.toString(shouldBeTrue3 == true) + "<br>");
    out.write(Boolean.toString(shouldBeFalse1 == false) + "<br>");
    out.write(Boolean.toString(shouldBeFalse2 == false) + "<br>");
    out.write(Boolean.toString(shouldBeFalse3 == false) + "<br>");
    out.write(Boolean.toString(countSubsShouldBe2 == 2) + "<br>");

    out.write(Boolean.toString(modList.size() == 1) + "<br>");
    out.write(Boolean.toString(subList.size() == 1) + "<br>");
    out.write(Boolean.toString(subList2.size() == 2) + "<br>");


*/


























































































/*

    // TEST MESSAGING SERVICE :

    messagingService.updateSettings("username1", false, false, true, true);

    List<String> usernameList = new ArrayList<>();
    usernameList.add("username1");
    usernameList.add("username2");
    usernameList.add("username3");
    messagingService.startConversation("username1", usernameList, "firstconvo", "this is my first message");

    int firstCountShouldBeOne = messagingService.getConversationCount("username1");
    System.out.println(firstCountShouldBeOne);
    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    List<ConversationDTO> conversations = messagingService.getOrderedConversationsRange("username1", 0, 1);
    List<MessageDTO> messages = conversations.get(0).getMessages();
    messagingService.replyMessage("username2", conversations.get(0).getId(), "response", "this is my second message from a different user");

    messagingService.hideConversationByUser("username3", conversations.get(0).getId());
    int secondCountShouldBeZero = messagingService.getConversationCount("username3");

    messagingService.hasReadMessage("username2", messages.get(0).getId());

    SettingsDTO settings1 = messagingService.getSettings("username1");

    List<FilterDTO> filters = messagingService.getFilters("username1");

    List<NotificationDTO> notifications = messagingService.getNotifications("username1");

    List<ConversationDTO> conversations2 = messagingService.getOrderedConversationsRange("username1", 0, 1);
    List<MessageDTO> messages2 = conversations2.get(0).getMessages();
    for(MessageDTO messagedto : messages2) {
      out.write(messagedto.getContent() + "<br>");
    }

    out.write((firstCountShouldBeOne == 1) + "<br>");

    out.write((secondCountShouldBeZero == 0) + "<br>");

    out.write(Boolean.toString(settings1.getOpenMarkRead()) + Boolean.toString(settings1.getNotifyMessages()) + Boolean.toString(settings1.getAllOrWhitelist())+ "<br>");

    for(FilterDTO filter : filters) {
      out.write(filter.getManagedUser() + "<br>");
    }

    for(NotificationDTO notification : notifications) {
      out.write(notification.getIdNumberType() + " notification<br>");
    }





*/











































    /* TESTING LOGINSERVICE
    String[][] sqs = {{"question1", "answer1"},{"question2","answer2"},{"question3","answer3"},};
    String[][] badsqs = {{"badquestion","badanswer"},{"badquestion","badanswer"},{"badq","bada"}};

    loginService.createUser("username1", "badpassword", "displayname1", "abc@abc.com", sqs);

    loginService.createUser("username2", "badpassword", "displayname2", "abc@abc.com", sqs);

    loginService.createUser("username3", "badpassword", "displayname2", "abc@abc.com", sqs);

    Boolean validatedCorrect = loginService.validateCredentials("username1", "badpassword");
    Boolean validatedIncorrect = loginService.validateCredentials("username2", "sifne9");

    Boolean changepwCorrect = loginService.changePassword("username1", "badpassword", "newbadpassword");
    Boolean changepwIncorrect = loginService.changePassword("username2", "wrongpassword", "asdf");

    Boolean validatedCorrect2 = loginService.validateCredentials("username1", "newbadpassword");
    Boolean validatedIncorrect2 = loginService.validateCredentials("username1", "sifne9");

    Boolean resetpwCorrect = loginService.resetPassword("username2", "newbadpassword", sqs);
    Boolean resetpwIncorrect = loginService.resetPassword("username2", "asdf", badsqs);

    Boolean validatedCorrect3 = loginService.validateCredentials("username2", "newbadpassword");
    Boolean validatedIncorrect3 = loginService.validateCredentials("username1", "sifne9");

    loginService.updateUserDisplayName("username1", "updatename");
    loginService.updateUserEmail("username1", "updateemail@email.com");
    loginService.increaseUserKarma("username1");
    loginService.increaseUserKarma("username1");
    loginService.decreaseUserKarma("username1");

    UserDTO userDTOtest = loginService.getUserInfo("username3");
    String[] testsqs = loginService.getUserQuestions("username1");

    Boolean validateCorrectUpdateQs = loginService.updateSecQs("username1", "newbadpassword", badsqs);
    Boolean validateIncorrectUpdateQs = loginService.updateSecQs("username1", "asdf", sqs);

    out.write((validatedCorrect == true) + "<br>");
    out.write((validatedIncorrect == false) + "<br>");
    out.write((changepwCorrect == true) + "<br>");
    out.write((changepwIncorrect == false) + "<br>");
    out.write((validatedCorrect2 == true) + "<br>");
    out.write((validatedIncorrect2 == false) + "<br>");
    out.write((resetpwCorrect == true) + "<br>");
    out.write((resetpwIncorrect == false) + "<br>");
    out.write((validatedCorrect3 == true) + "<br>");
    out.write((validatedIncorrect3 == false) + "<br>");
    out.write((validateCorrectUpdateQs == true) + "<br>");
    out.write((validateIncorrectUpdateQs == false) + "<br>");

    out.write(userDTOtest.getUsername() + "<br>");
    out.write(testsqs[0]);

    */

    /* TEST INSERT/UPDATE info
    UserRepo userRepo = new UserRepo();
    SecurityQuestionRepo sqRepo = new SecurityQuestionRepo();
    LoginAttemptRepo laRepo = new LoginAttemptRepo();
    SettingsRepo settingsRepo = new SettingsRepo();
    FilterRepo filterRepo = new FilterRepo();
    ConversationRepo conversationRepo = new ConversationRepo();
    MembershipRepo membershipRepo = new MembershipRepo();
    MessageRepo messageRepo = new MessageRepo();
    NotificationRepo notificationRepo = new NotificationRepo();
    SubforumRepo subforumRepo = new SubforumRepo();
    SubforumSettingsRepo subSettingsRepo = new SubforumSettingsRepo();
    SubscriptionRepo subscriptionRepo = new SubscriptionRepo();
    ModListRepo modRepo = new ModListRepo();
    PostRepo postRepo = new PostRepo();
    PostKarmaRepo postKarmaRepo = new PostKarmaRepo();
    CommentRepo commentRepo = new CommentRepo();
    CommentKarmaRepo commentKarmaRepo = new CommentKarmaRepo();





    UserEntity user1 = new UserEntity("username1", "password1", "displayname1", "abc@abc.com", Instant.now(), 0, Instant.now());
    UserEntity user2 = new UserEntity("username2", "password2", "displayname2", "abc@abc.com", Instant.now(), 0, Instant.now());
    SecurityQuestionEntity sq1 = new SecurityQuestionEntity(user1, "question1", "answer1");
    SecurityQuestionEntity sq2 = new SecurityQuestionEntity(user2, "question2", "answer2");
    LoginAttemptEntity la1 = new LoginAttemptEntity(user1, Instant.now(), true);
    LoginAttemptEntity la2 = new LoginAttemptEntity(user2, Instant.now(), true);
    SettingsEntity user1Settings = new SettingsEntity(user1, true, true, true, true);
    SettingsEntity user2Settings = new SettingsEntity(user2, true, true, true, true);
    FilterEntity filter1 = new FilterEntity(user1, user2, false);
    FilterEntity filter2 = new FilterEntity(user2, user1, false);

    ConversationEntity conversation1 = new ConversationEntity(user1, Instant.now(), Instant.now(), 0);
    ConversationEntity conversation2 = new ConversationEntity(user2, Instant.now(), Instant.now(), 0);
    MembershipEntity membership1 = new MembershipEntity(conversation1, user1, 0, true);
    MembershipEntity membership2 = new MembershipEntity(conversation2, user2, 0, true);
    MessageEntity message1 = new MessageEntity(conversation1, user1, Instant.now(), "Message Subject 1 ", "Message Content 1 ", 1);
    MessageEntity message2 = new MessageEntity(conversation2, user2, Instant.now(), "Message Subject 2 ", "Message Content 2 ", 1);
    NotificationEntity notification1 = new NotificationEntity(user1, message1.getId(), "message");
    NotificationEntity notification2 = new NotificationEntity(user2, message2.getId(), "message");

    SubforumEntity subforum1 = new SubforumEntity("subforum1", "subforum1 description", "subforum1 flair", 0, Instant.now(), user1);
    SubforumEntity subforum2 = new SubforumEntity("subforum2", "subforum2 description", "subforum2 flair", 0, Instant.now(), user2);
    SubforumSettingsEntity subforum1Settings = new SubforumSettingsEntity(subforum1, "subforum 1 rules");
    SubforumSettingsEntity subforum2Settings = new SubforumSettingsEntity(subforum2, "subforum 2 rules");
    SubscriptionEntity subscription1 = new SubscriptionEntity(subforum1, user1);
    SubscriptionEntity subscription2 = new SubscriptionEntity(subforum2, user2);
    ModListEntity mod1 = new ModListEntity(user1, subforum1, 1);
    ModListEntity mod2 = new ModListEntity(user1, subforum2, 1);

    PostEntity post1 = new PostEntity(Instant.now(), user1, subforum1, 0, "post1 title", "post1 content");
    PostEntity post2 = new PostEntity(Instant.now(), user2, subforum2, 0, "post2 title", "post2 content");
    PostKarmaEntity postKarma1 = new PostKarmaEntity(Instant.now(), user1, post1, true);
    PostKarmaEntity postKarma2 = new PostKarmaEntity(Instant.now(), user2, post2, true);

    CommentEntity comment1 = new CommentEntity(Instant.now(), user1, post1, subforum1, 0, "comment1 content", null, 1, false);
    CommentEntity comment2 = new CommentEntity(Instant.now(), user2, post2, subforum2, 0, "comment2 content", null, 1, false);
    CommentKarmaEntity commentKarma1 = new CommentKarmaEntity(Instant.now(), user1, comment1, true);
    CommentKarmaEntity commentKarma2 = new CommentKarmaEntity(Instant.now(), user2, comment2, true);


    user1 = userRepo.add(user1);
    user2 = userRepo.add(user2);
    sq1 = sqRepo.add(sq1);
    sq2 = sqRepo.add(sq2);
    la1 = laRepo.add(la1);
    la2 = laRepo.add(la2);
    user1Settings = settingsRepo.add(user1Settings);
    user2Settings = settingsRepo.add(user2Settings);
    filter1 = filterRepo.add(filter1);
    filter2 = filterRepo.add(filter2);
    conversation1 = conversationRepo.add(conversation1);
    conversation2 = conversationRepo.add(conversation2);
    membership1 = membershipRepo.add(membership1);
    membership2 = membershipRepo.add(membership2);
    message1 = messageRepo.add(message1);
    message2 = messageRepo.add(message2);
    notification1 = notificationRepo.add(notification1);
    notification2 = notificationRepo.add(notification2);
    subforum1 = subforumRepo.add(subforum1);
    subforum2 = subforumRepo.add(subforum2);
    subforum1Settings = subSettingsRepo.add(subforum1Settings);
    subforum2Settings = subSettingsRepo.add(subforum2Settings);
    subscription1 = subscriptionRepo.add(subscription1);
    subscription2 = subscriptionRepo.add(subscription2);
    mod1 = modRepo.add(mod1);
    mod2 = modRepo.add(mod2);
    post1 = postRepo.add(post1);
    post2 = postRepo.add(post2);
    postKarma1 = postKarmaRepo.add(postKarma1);
    postKarma2 = postKarmaRepo.add(postKarma2);
    comment1 = commentRepo.add(comment1);
    comment2 = commentRepo.add(comment2);
    commentKarma1 = commentKarmaRepo.add(commentKarma1);
    commentKarma2 = commentKarmaRepo.add(commentKarma2);

    user1.setDisplayName("Update DisplayName1");
    sq1.setQuestion("Update Question 1");
    la1.setSucceeded(false);
    user1Settings.setOpenMarkRead(false);
    filter1.setBlockAllow(true);
    conversation1.setChainLength(2);
    membership1.setUnreadChainNum(2);
    message1.setSubject("Updated Subject 1");
    notification1.setIdNumberType("updated test");
    subforum1.setDescription("updated Description 1");
    subforum1Settings.setRules("Updated Rules 1");
    subscription1.setSubforum(subforum2);
    mod1.setRank(2);
    post1.setTitle("Updated Title 1");
    postKarma1.setUpOrDown(false);
    comment1.setContent("Updated Content 1");
    commentKarma1.setUpOrDown(false);

    userRepo.update(user1);
    sqRepo.update(sq1);
    laRepo.update(la1);
    settingsRepo.update(user1Settings);
    filterRepo.update(filter1);
    conversationRepo.update(conversation1);
    membershipRepo.update(membership1);
    messageRepo.update(message1);
    notificationRepo.update(notification1);
    subforumRepo.update(subforum1);
    subSettingsRepo.update(subforum1Settings);
    subscriptionRepo.update(subscription1);
    modRepo.update(mod1);
    postRepo.update(post1);
    postKarmaRepo.update(postKarma1);
    commentRepo.update(comment1);
    commentKarmaRepo.update(commentKarma1);

  }
}
*/

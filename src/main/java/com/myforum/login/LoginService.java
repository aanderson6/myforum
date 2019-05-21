package com.myforum.login;

// imports...

import com.myforum.login.entities.*;
import com.myforum.login.repositories.*;
import com.myforum.login.dtos.*;

import com.myforum.messaging.entities.*;
import com.myforum.messaging.repositories.*;

import com.myforum.subforum.entities.*;
import com.myforum.subforum.repositories.*;

import com.myforum.posts.entities.*;
import com.myforum.posts.repositories.*;

import com.myforum.comments.entities.*;
import com.myforum.comments.repositories.*;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

import java.util.Base64;
import java.util.Arrays;
import java.util.List;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.myforum.util.Validator;

public class LoginService implements LoginInterface {

  UserRepo userRepo = new UserRepo();
  LoginAttemptRepo loginAttemptRepo = new LoginAttemptRepo();
  SecurityQuestionRepo secQuestionRepo = new SecurityQuestionRepo();
  SettingsRepo settingsRepo = new SettingsRepo();

  public int createUser(String username, String password, String displayName, String email, String[][] sqeArray) throws IllegalArgumentException {

    if (username == null || sqeArray == null) {
      throw new NullPointerException();
    }

    if (!Validator.validateUsername(username)) {
      throw new IllegalArgumentException("Invalid Username");
    } else if (!Validator.validateDisplayName(displayName)) {
      throw new IllegalArgumentException("Invalid Display Name");
    } else if (!Validator.validateEmail(email)) {
      throw new IllegalArgumentException("Invalid Email");
    } else if (!Validator.validateSHPW(password)) {
      throw new IllegalArgumentException("Invalid Password");
    }

    for(String[] sqeArr : sqeArray) {
      if(sqeArr == null) {
        throw new NullPointerException();
      }
      for(String sqe : sqeArr) {
        if(sqe == null) {
          throw new NullPointerException();
        }
        if (!Validator.validateSecQuestion(sqe)) {
          throw new IllegalArgumentException("Invalid Security Info");
        }
      }
    }

    UserEntity tempUser = userRepo.getByUsername(username);
    if(!(tempUser == null)) {
      throw new IllegalArgumentException("Username Taken");
    }

    UserEntity user = new UserEntity(username, shString(password), displayName, email, Instant.now(), 0, Instant.now().minusSeconds(1000));
    user = userRepo.add(user);

    SettingsEntity settings = new SettingsEntity(user, true, false, true, true);
    settingsRepo.add(settings);

    for(int i = 0; i < 3; i++) {
      SecurityQuestionEntity sqe = new SecurityQuestionEntity(user, sqeArray[i][0], shString(sqeArray[i][1]));
      secQuestionRepo.add(sqe);
    }

    return user.getId();
  }

  public Boolean validateCredentials(String username, String password) throws IllegalStateException, IllegalArgumentException {

    if (username == null || password == null) {
      throw new NullPointerException();
    }

    Boolean validated = false;
    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    if (user.getIsLocked().compareTo(Instant.now().minus(4, ChronoUnit.MINUTES)) > 0) {
      // user is locked
      throw new IllegalStateException("User Locked");
    } else {
      // user is not locked
      validated = validateSHString(user.getShPassword(), password);

      // add login attempt to records
      LoginAttemptEntity lae = new LoginAttemptEntity(user, Instant.now(), validated);
      loginAttemptRepo.add(lae);

      // if failure, check if account needs locked. lock if needed
      if (!validated) {
        List<LoginAttemptEntity> laeList = loginAttemptRepo.getByUserAndTime(user, Instant.now().minus(5, ChronoUnit.MINUTES));
        int laeInvalidListSize = 0;
        for(LoginAttemptEntity laeCycle: laeList) {
          if (!laeCycle.getSucceeded()) {
            laeInvalidListSize++;
          }
        }
        if (laeInvalidListSize >= 4) {
          lockUser(user);
        }
      }
    }

    return validated;
  }

  public Boolean changePassword(String username, String oldPassword, String newPassword) throws IllegalStateException, IllegalArgumentException {

    if (username == null || oldPassword == null || newPassword == null) {
      throw new NullPointerException();
    }

    if (!Validator.validateSHPW(newPassword)) {
      throw new IllegalArgumentException("Invalid Password");
    }

    Boolean validated = validateCredentials(username, oldPassword);

    if(validated) {
      UserEntity user = userRepo.getByUsername(username);
      user.setShPassword(shString(newPassword));
      updateUserSH(user);
    }

    return validated;
  }

  // answers in SQEs should be plain text
  public Boolean resetPassword(String username, String newPassword, String[][] sqeArray) throws IllegalArgumentException  {

    if (username == null || newPassword == null || sqeArray == null) {
      throw new NullPointerException();
    }

    if (!Validator.validateSHPW(newPassword)) {
      throw new IllegalArgumentException("Invalid Password");
    }

    for(String[] sqeArr : sqeArray) {
      if(sqeArr == null) {
        throw new NullPointerException();
      }
      for(String sqe : sqeArr) {
        if(sqe == null) {
          throw new NullPointerException();
        }
      }
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    Boolean validated = false;
    validated = validateSecQs(user.getId(), sqeArray);

    if(validated) {
      user.setShPassword(shString(newPassword));
      userRepo.update(user);
    }

    return validated;
  }

  /////// UPDATE USER PROPERTY METHODS
  public void updateUserDisplayName(String username, String displayName) throws IllegalArgumentException  {
    if (username == null || displayName == null) {
      throw new NullPointerException();
    }

    if (!Validator.validateDisplayName(displayName)) {
      throw new IllegalArgumentException("Invalid Display Name");
    }

    UserEntity newUser = userRepo.getByUsername(username);

    if(newUser == null) {
      throw new IllegalArgumentException("No Such User");
    }

    newUser.setDisplayName(displayName);
    userRepo.update(newUser);
  }

  public void updateUserEmail(String username, String email) throws IllegalArgumentException  {
    if (username == null || email == null) {
      throw new NullPointerException();
    }

    if (!Validator.validateEmail(email)) {
      throw new IllegalArgumentException("Invalid Email");
    }

    UserEntity newUser = userRepo.getByUsername(username);

    if(newUser == null) {
      throw new IllegalArgumentException("No Such User");
    }

    newUser.setEmail(email);
    userRepo.update(newUser);
  }

  public void increaseUserKarma(String username) throws IllegalArgumentException  {
    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity newUser = userRepo.getByUsername(username);

    if(newUser == null) {
      throw new IllegalArgumentException("No Such User");
    }

    newUser.setKarma(newUser.getKarma() + 1);
    userRepo.update(newUser);
  }

  public void decreaseUserKarma(String username) throws IllegalArgumentException  {
    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity newUser = userRepo.getByUsername(username);

    if(newUser == null) {
      throw new IllegalArgumentException("No Such User");
    }

    newUser.setKarma(newUser.getKarma() - 1);
    userRepo.update(newUser);
  }


  public UserDTO getUserInfo(String username) throws IllegalArgumentException  {

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity userTemp = userRepo.getByUsername(username);

    if(userTemp == null) {
      throw new IllegalArgumentException("No Such User");
    }

    UserDTO user = new UserDTO(username, userTemp.getDisplayName(), userTemp.getEmail(), userTemp.getJoinDate(), userTemp.getKarma());
    return user;
  }

  public String[] getUserQuestions(String username) throws IllegalArgumentException  {

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);

    if(user == null) {
      throw new IllegalArgumentException("No Such User");
    }

    List<SecurityQuestionEntity> sqeList = secQuestionRepo.getByUser(user);
    String[] questionsArray = new String[3];

    for(int i = 0; i < 3; i++) {
      SecurityQuestionEntity sqe = sqeList.get(i);
      questionsArray[i] = sqe.getQuestion();
    }

    return questionsArray;
  }

  // Returns false if username and password do not match, or if any question uid does not match the username
  public Boolean updateSecQs(String username, String password, String[][] sqeUpdateArray) throws IllegalStateException, IllegalArgumentException {

    if (username == null || password == null || sqeUpdateArray == null) {
      throw new NullPointerException();
    }

    for(String[] sqeArr : sqeUpdateArray) {
      if(sqeArr == null) {
        throw new NullPointerException();
      }
      for(String sqe : sqeArr) {
        if(sqe == null) {
          throw new NullPointerException();
        }
        if (!Validator.validateSecQuestion(sqe)) {
          throw new IllegalArgumentException("Invalid Security Info");
        }
      }
    }

    Boolean validated = false;
    validated = validateCredentials(username, password);

    if(validated) {
      UserEntity user = userRepo.getByUsername(username);
      List<SecurityQuestionEntity> sqeList = secQuestionRepo.getByUser(user);

      for(int i = 0; i < 3; i++) {
        SecurityQuestionEntity sqe = sqeList.get(i);
        sqe.setQuestion(sqeUpdateArray[i][0]);
        sqe.setShAnswer(shString(sqeUpdateArray[i][1]));
        secQuestionRepo.update(sqe);
      }
    }

    return validated;
  }

  public void deleteUser(String username) {

    ConversationRepo conversationRepo = new ConversationRepo();
    MessageRepo messageRepo = new MessageRepo();
    FilterRepo filterRepo = new FilterRepo();
    MembershipRepo membershipRepo = new MembershipRepo();
    NotificationRepo notificationRepo = new NotificationRepo();
    SubforumRepo subforumRepo = new SubforumRepo();
    SubscriptionRepo subscriptionRepo = new SubscriptionRepo();
    ModListRepo modRepo = new ModListRepo();
    PostRepo postRepo = new PostRepo();
    PostKarmaRepo postKarmaRepo = new PostKarmaRepo();
    CommentRepo commentRepo = new CommentRepo();
    CommentKarmaRepo commentKarmaRepo = new CommentKarmaRepo();

    if (username == null) {
      throw new NullPointerException();
    }

    UserEntity user = userRepo.getByUsername(username);
    loginAttemptRepo.deleteByUser(user);
    secQuestionRepo.deleteByUser(user);

    List<ConversationEntity> conversationList = conversationRepo.getByUser(user);
    for(ConversationEntity conversation : conversationList) {
      conversation.setFromUser(null);
      conversationRepo.update(conversation);
    }

    List<MessageEntity> messageList = messageRepo.getByUser(user);
    for(MessageEntity message : messageList) {
      message.setFromUser(null);
      messageRepo.update(message);
    }

    filterRepo.removeByUser(user);
    membershipRepo.removeByUser(user);
    notificationRepo.removeByUser(user);
    settingsRepo.removeByUser(user);

    List<SubforumEntity> subforumList = subforumRepo.getByUser(user);
    for(SubforumEntity subforum : subforumList) {
      subforum.setCreatedBy(null);
      subforumRepo.update(subforum);
    }

    List<SubscriptionEntity> subscriptions = subscriptionRepo.getByUser(user);
    for(SubscriptionEntity subscription : subscriptions){
      SubforumEntity subSub = subscription.getSubforum();
      subSub.setSubscriberCount(subSub.getSubscriberCount() - 1);
      subforumRepo.update(subSub);
      subscriptionRepo.remove(subscription);
    }

    List<ModListEntity> modList = modRepo.getByUser(user);
    for(ModListEntity mod : modList) {
      int modRank = mod.getRank();
      SubforumEntity subforum = mod.getSubforum();
      List<ModListEntity> modSubList = modRepo.getBySubforumAboveRank(subforum, modRank);
      for(ModListEntity modEnt : modSubList) {
        modEnt.setRank(modEnt.getRank() - 1);
        modRepo.update(modEnt);
      }
      modRepo.remove(mod);
    }

    List<PostEntity> postList = postRepo.getByUser(user);
    for(PostEntity post : postList) {
      post.setUser(null);
      postRepo.update(post);
    }

    List<PostKarmaEntity> postKarmaList = postKarmaRepo.getByUser(user);
    for(PostKarmaEntity postKarma : postKarmaList) {
      PostEntity post = postKarma.getPost();
      if(postKarma.getUpOrDown()) {

        UserEntity postUser = post.getUser();
        postUser.setKarma(postUser.getKarma() - 1);
        userRepo.update(postUser);

        postKarmaRepo.remove(postKarma);

        post.setKarma(post.getKarma() - 1);
        postRepo.update(post);

      } else {

        UserEntity postUser = post.getUser();
        postUser.setKarma(postUser.getKarma() + 1);
        userRepo.update(postUser);

        postKarmaRepo.remove(postKarma);

        post.setKarma(post.getKarma() + 1);
        postRepo.update(post);

      }
    }

    List<CommentEntity> commentList = commentRepo.getByUser(user);
    for(CommentEntity comment : commentList) {
      comment.setUser(null);
      commentRepo.update(comment);
    }

    List<CommentKarmaEntity> commentKarmaList = commentKarmaRepo.getByUser(user);
    for(CommentKarmaEntity commentKarma : commentKarmaList) {
      CommentEntity comment = commentKarma.getComment();
      if(commentKarma.getUpOrDown()) {

        UserEntity commentUser = comment.getUser();
        commentUser.setKarma(commentUser.getKarma() - 1);
        userRepo.update(commentUser);

        commentKarmaRepo.remove(commentKarma);

        comment.setKarma(comment.getKarma() - 1);
        commentRepo.update(comment);

      } else {

        UserEntity commentUser = comment.getUser();
        commentUser.setKarma(commentUser.getKarma() + 1);
        userRepo.update(commentUser);

        commentKarmaRepo.remove(commentKarma);

        comment.setKarma(comment.getKarma() + 1);
        commentRepo.update(comment);

      }
    }

    userRepo.remove(user);
  }



  // INTERNAL METHODS

  private void updateUserSH(UserEntity newUser) {

    if (newUser == null) {
      throw new NullPointerException();
    }

    userRepo.update(newUser);
  }

  // Answers input in plain text, must include qid
  private Boolean validateSecQs(int uid, String[][] sqeArray) {

    if (sqeArray == null) {
      throw new NullPointerException();
    }

    for(String[] sqeArr : sqeArray) {
      if(sqeArr == null) {
        throw new NullPointerException();
      }
      for(String sqe : sqeArr) {
        if(sqe == null) {
          throw new NullPointerException();
        }
      }
    }

    List<SecurityQuestionEntity> sqeList = secQuestionRepo.getByUser(userRepo.getById(uid));
    int correctAnswersCount = 0;

    for(int i = 0; i < 3; i++) {
      for(SecurityQuestionEntity sqe : sqeList) {
        if (sqeArray[i][0].equals(sqe.getQuestion())) {
          if (validateSHString(sqe.getShAnswer(), sqeArray[i][1])) {
            correctAnswersCount++;
          }
        }
      }
    }

    if (correctAnswersCount == 3) {
      return true;
    } else {
      return false;
    }

  }

  private void lockUser(UserEntity user) {

    if (user == null) {
      throw new NullPointerException();
    }

    user.setIsLocked(Instant.now());
    userRepo.update(user);
  }

  private String shString(String inputString) {

    if (inputString == null) {
      throw new NullPointerException();
    }

    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[32];
    byte[] hash = new byte[32];
    random.nextBytes(salt);

    KeySpec spec = new PBEKeySpec(inputString.toCharArray(), salt, 10000, 256);
    try {
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      hash = factory.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException|InvalidKeySpecException ex) {
      //// ignore - Algorithm Hardcoded
    }

    String returnString = Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);

    return returnString;
  }

  private Boolean validateSHString(String storedSHString, String validateThis) {

    if (storedSHString == null || validateThis == null) {
      throw new NullPointerException();
    }

    byte[] newHash = new byte[32];

    String[] sepPW = storedSHString.split(":");
    byte[] salt = Base64.getDecoder().decode(sepPW[0]);
    byte[] storedHash = Base64.getDecoder().decode(sepPW[1]);

    KeySpec spec = new PBEKeySpec(validateThis.toCharArray(), salt, 10000, 256);
    try {
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      newHash = factory.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException|InvalidKeySpecException ex) {
      //// ignore - Algorithm Hardcoded
    }

    return Arrays.equals(storedHash, newHash);
  }

}

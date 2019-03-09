package com.myforum.login;

// imports...

import com.myforum.login.models.UserModel;
import com.myforum.login.models.SecurityQuestionModel;
import com.myforum.login.models.LoginAttemptModel;

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
import java.sql.SQLException;
import javax.naming.NamingException;

// All you need is Login Service, UserModel, and SecurityQuestionModel in your Controller class to use all these methods. Everything else is hidden 'under the hood' of the Service
// Tested and ready to go now

/*
  PUBLIC METHODS:

  UserModel createUser(UserModel user, List<SecurityQuestionModel> sqmList) throws IllegalArgumentException, SQLException, NamingException
  UserModel getUser(String username) throws SQLException, NamingException
  void updateUserNoPW(UserModel newUser) throws SQLException, NamingException, IllegalArgumentException
  void deleteUser(String username) throws SQLException, NamingException

  Boolean validateCredentials(String username, String password) throws SQLException, NamingException, IllegalStateException
  Boolean changePassword(String username, String oldPassword, String newPassword) throws SQLException, NamingException, IllegalStateException
  Boolean resetPassword(String username, String newPassword, List<SecurityQuestionModel> sqmList) throws SQLException, NamingException, IllegalArgumentException, IllegalStateException

  Boolean updateSecQs(String username, String password, List<SecurityQuestionModel> sqmUpdateList) throws SQLException, NamingException, IllegalArgumentException
  List<SecurityQuestionModel> getSecQs(String username) throws SQLException, NamingException
*/

public class LoginService {
  private DaoInterfaceLogin loginDAO = new DaoMySqlLogin();

  // user only needs username, plain text password, display name, email; sqmList entities only need question and plain text answer
  public UserModel createUser(UserModel user, List<SecurityQuestionModel> sqmList) throws IllegalArgumentException, SQLException, NamingException {
    user.setSHPassword(shString(user.getSHPassword()));

    user = loginDAO.createUser(user);
    for(SecurityQuestionModel sqm: sqmList) {
      sqm.setUid(user.getUid());
      sqm.setSHAnswer(shString(sqm.getSHAnswer()));
      loginDAO.createSecurityQuestion(sqm);
    }

    user.setSHPassword("");
    return user;
  }

  public Boolean validateCredentials(String username, String password) throws SQLException, NamingException, IllegalStateException {
    Boolean validated = false;
    UserModel user = loginDAO.getUserByUsername(username);

    if (user.getIsLocked().compareTo(Instant.now().minus(4, ChronoUnit.MINUTES)) > 0) {
      // user is locked
      throw new IllegalStateException("User Locked");
    } else {
      // user is not locked
      validated = validateSHString(user.getSHPassword(), password);
      // add login attempt to records
      LoginAttemptModel lam = new LoginAttemptModel(user.getUid(), validated);
      loginDAO.createLoginRecord(lam);
      // if failure, check if account needs locked. lock if needed
      if (!validated) {
        List<LoginAttemptModel> lamList = loginDAO.getLoginRecordsByUidAndTime(user.getUid(), Instant.now().minus(5, ChronoUnit.MINUTES));
        int lamInvalidListSize = 0;
        for(LoginAttemptModel lamCycle: lamList) {
          if (!lamCycle.getSucceeded()) {
            lamInvalidListSize++;
          }
        }
        if (lamInvalidListSize >= 4) {
          lockUser(user);
        }
      }
    }

    return validated;
  }

  public Boolean changePassword(String username, String oldPassword, String newPassword) throws SQLException, NamingException, IllegalStateException {
    Boolean validated = validateCredentials(username, oldPassword);

    if(validated) {
      UserModel user = getUser(username);
      user.setSHPassword(shString(newPassword));
      updateUserSH(user);
    }

    return validated;
  }

  // answers in SQMs should be plain text
  public Boolean resetPassword(String username, String newPassword, List<SecurityQuestionModel> sqmList) throws SQLException, NamingException, IllegalArgumentException, IllegalStateException {

    if(sqmList.size() != 3) {
      return false;
    }

    UserModel user = getUser(username);
    for(SecurityQuestionModel sqm: sqmList) {
      if(sqm.getUid() != user.getUid()) {
        return false;
      }
    }
    Boolean validated = false;
    validated = validateSecQs(sqmList);

    if(validated) {
      user.setSHPassword(shString(newPassword));
      updateUserSH(user);
    }

    return validated;
  }

  public void updateUserNoPW(UserModel newUser) throws SQLException, NamingException, IllegalArgumentException {
    UserModel tempUser = getUserSH(newUser.getUsername());
    newUser.setSHPassword(tempUser.getSHPassword());
    loginDAO.updateUser(newUser);
  }

  public UserModel getUser(String username) throws SQLException, NamingException {
    UserModel user = loginDAO.getUserByUsername(username);
    user.setSHPassword("");
    return user;
  }

  // Returns false if username and password do not match, or if any question uid does not match the username
  public Boolean updateSecQs(String username, String password, List<SecurityQuestionModel> sqmUpdateList) throws SQLException, NamingException, IllegalArgumentException, IllegalStateException {

    Boolean validated = false;
    validated = validateCredentials(username, password);

    if(validated) {
      UserModel user = loginDAO.getUserByUsername(username);
      for(SecurityQuestionModel sqm: sqmUpdateList) {
        if(sqm.getUid() != user.getUid()) {
          return false;
        }
      }
      for(SecurityQuestionModel sqm: sqmUpdateList) {
        loginDAO.updateSecurityQuestion(sqm);
      }
    }

    return validated;
  }

  public List<SecurityQuestionModel> getSecQs(String username) throws SQLException, NamingException {
    UserModel user = loginDAO.getUserByUsername(username);
    List<SecurityQuestionModel> sqmList = loginDAO.getSecurityQuestionsByUid(user.getUid());
    for(SecurityQuestionModel sqm: sqmList) {
      sqm.setSHAnswer("");
    }
    return sqmList;
  }

  public void deleteUser(String username) throws SQLException, NamingException {
    UserModel user = loginDAO.getUserByUsername(username);
    loginDAO.deleteLoginRecordsByUid(user.getUid());
    loginDAO.deleteSecurityQuestionsByUid(user.getUid());
    loginDAO.deleteUser(user.getUid());
  }



  // INTERNAL METHODS

  private void updateUserSH(UserModel newUser) throws SQLException, NamingException, IllegalArgumentException {
    loginDAO.updateUser(newUser);
  }

  private UserModel getUserSH(String username) throws SQLException, NamingException {
    return loginDAO.getUserByUsername(username);
  }

  // Answers input in plain text, must include qid
  private Boolean validateSecQs(List<SecurityQuestionModel> sqmList) throws SQLException, NamingException {
    for(SecurityQuestionModel sqm: sqmList) {
      SecurityQuestionModel sqmDB = loginDAO.getSecurityQuestionByQid(sqm.getQid());
      if (!validateSHString(sqmDB.getSHAnswer(), sqm.getSHAnswer())) {
        return false;
      }
    }
    return true;
  }

  private void lockUser(UserModel user) throws IllegalArgumentException, SQLException, NamingException {
    user.setIsLocked(Instant.now());
    loginDAO.updateUser(user);
  }

  private String shString(String inputString) {
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

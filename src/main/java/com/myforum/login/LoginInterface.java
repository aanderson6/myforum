package com.myforum.login;

import com.myforum.login.dtos.*;
import java.time.Instant;

public interface LoginInterface {

  public int createUser(String username, String password, String displayName, String email, String[][] sqeArray) throws IllegalArgumentException;
  public Boolean validateCredentials(String username, String password) throws IllegalStateException, IllegalArgumentException;
  public Boolean changePassword(String username, String oldPassword, String newPassword) throws IllegalStateException, IllegalArgumentException;
  // answers in SQEs should be plain text
  public Boolean resetPassword(String username, String newPassword, String[][] sqeArray) throws IllegalArgumentException;
  /////// UPDATE USER PROPERTY METHODS
  public void updateUserDisplayName(String username, String displayName) throws IllegalArgumentException;
  public void updateUserEmail(String username, String email) throws IllegalArgumentException;
  public void increaseUserKarma(String username) throws IllegalArgumentException;
  public void decreaseUserKarma(String username) throws IllegalArgumentException;
  public UserDTO getUserInfo(String username) throws IllegalArgumentException;
  public String[] getUserQuestions(String username) throws IllegalArgumentException;
  // Returns false if username and password do not match, or if any question uid does not match the username
  public Boolean updateSecQs(String username, String password, String[][] sqeUpdateArray) throws IllegalStateException, IllegalArgumentException;
  public void deleteUser(String username);

}

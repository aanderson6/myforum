package com.myforum.login;

import com.myforum.login.dtos.*;

public interface LoginInterface {

/*
IllegalArgumentException("Invalid Username");
IllegalArgumentException("Invalid Display Name");
IllegalArgumentException("Invalid Email");
IllegalArgumentException("Invalid Password");
IllegalArgumentException("Invalid Security Info");
IllegalArgumentException("Username Taken");
*/
  public int createUser(String username, String password, String displayName, String email, String[][] sqeArray) throws IllegalArgumentException;

/*
IllegalArgumentException("No Such User");
IllegalStateException("User Locked");
*/
  public Boolean validateCredentials(String username, String password) throws IllegalStateException, IllegalArgumentException;

  /*
  IllegalArgumentException("No Such User");
IllegalArgumentException("Invalid Password");
IllegalStateException("User Locked");
  */
  public Boolean changePassword(String username, String oldPassword, String newPassword) throws IllegalStateException, IllegalArgumentException;

  /*
IllegalArgumentException("Invalid Password");
IllegalArgumentException("No Such User");
  */
  // answers in SQEs should be plain text
  public Boolean resetPassword(String username, String newPassword, String[][] sqeArray) throws IllegalArgumentException;

  /////// UPDATE USER PROPERTY METHODS
  /*
IllegalArgumentException("Invalid Display Name");
IllegalArgumentException("No Such User");
  */
  public void updateUserDisplayName(String username, String displayName) throws IllegalArgumentException;

  /*
IllegalArgumentException("Invalid Email");
IllegalArgumentException("No Such User");
  */
  public void updateUserEmail(String username, String email) throws IllegalArgumentException;

  // IllegalArgumentException("No Such User");
  public void increaseUserKarma(String username) throws IllegalArgumentException;

  // IllegalArgumentException("No Such User");
  public void decreaseUserKarma(String username) throws IllegalArgumentException;

  // IllegalArgumentException("No Such User");
  public UserDTO getUserInfo(String username) throws IllegalArgumentException;

  // IllegalArgumentException("No Such User");
  public String[] getUserQuestions(String username) throws IllegalArgumentException;

  /* IllegalArgumentException("Invalid Security Info");
  IllegalArgumentException("No Such User");
  IllegalStateException("User Locked");
  */
  // Returns false if username and password do not match, or if any question uid does not match the username
  public Boolean updateSecQs(String username, String password, String[][] sqeUpdateArray) throws IllegalStateException, IllegalArgumentException;
  public void deleteUser(String username);

}

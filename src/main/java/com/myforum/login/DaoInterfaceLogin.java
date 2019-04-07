package com.myforum.login;

import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.List;
import java.time.Instant;

import com.myforum.login.models.UserModel;
import com.myforum.login.models.SecurityQuestionModel;
import com.myforum.login.models.LoginAttemptModel;

public interface DaoInterfaceLogin {

  public UserModel createUser(UserModel inputUser) throws IllegalArgumentException, SQLException, NamingException;
  public UserModel getUserByUsername(String username) throws SQLException, NamingException;
  public UserModel getUserByUid(int uid) throws SQLException, NamingException;
  public void deleteUser(int uid) throws SQLException, NamingException;
  public void updateUser(UserModel inputUser) throws SQLException, NamingException, IllegalArgumentException;
  public SecurityQuestionModel createSecurityQuestion(SecurityQuestionModel createSQM) throws SQLException, NamingException, IllegalArgumentException;
  public List<SecurityQuestionModel> getSecurityQuestionsByUid(int uid) throws SQLException, NamingException;
  public SecurityQuestionModel getSecurityQuestionByQid(int qid) throws SQLException, NamingException;
  public void deleteSecurityQuestionsByUid(int uid) throws SQLException, NamingException;
  public void updateSecurityQuestion(SecurityQuestionModel updateSQM) throws SQLException, NamingException, IllegalArgumentException;
  public void createLoginRecord(LoginAttemptModel lam) throws SQLException, NamingException;
  public List<LoginAttemptModel> getLoginRecordsByUidAndTime(int uid, Instant recentTime) throws SQLException, NamingException;
  public void deleteLoginRecordsByUid(int uid) throws SQLException, NamingException;

}

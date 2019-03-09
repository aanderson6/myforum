package com.myforum.login;

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
import java.time.temporal.ChronoUnit;

import com.myforum.login.models.UserModel;
import com.myforum.login.models.SecurityQuestionModel;
import com.myforum.login.models.LoginAttemptModel;

public class DaoMySqlLogin implements DaoInterfaceLogin {

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

  ///// THROWS noted exceptions, differentiate IllegalArgumentException with string attached...
  public UserModel createUser(UserModel inputUser) throws IllegalArgumentException, SQLException, NamingException {

    String username = inputUser.getUsername();
    String shpw = inputUser.getSHPassword();
    String displayName = inputUser.getDisplayName();
    String email = inputUser.getEmail();

    ///// VALIDATE INPUT /////
    if (!ValidateUtility.validateUsername(username)) {
      throw new IllegalArgumentException("Invalid username");
    } else if (!ValidateUtility.validateSHPW(shpw)) {
      throw new IllegalArgumentException("Invalid shpw");
    } else if (!ValidateUtility.validateDisplayName(displayName)) {
      throw new IllegalArgumentException("Invalid displayname");
    } else if (!ValidateUtility.validateEmail(email)) {
      throw new IllegalArgumentException("Invalid email");
    } else if (!getUserByUsername(username).isEmpty()) {
      throw new IllegalArgumentException("Duplicate username");
    }

    ///// ACCESS DATABASE /////
    try {
      openConnection();
      String query = "INSERT INTO Users (username, shpw, displayname, email, joindate, karma, islocked) VALUES (?, ?, ?, ?, NOW(), 0, ?);";
      stmt = con.prepareStatement(query);
      stmt.setString(1, username);
      stmt.setString(2, shpw);
      stmt.setString(3, displayName);
      stmt.setString(4, email);
      stmt.setTimestamp(5, Timestamp.from(Instant.now().minus(5, ChronoUnit.MINUTES)));
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }

    inputUser = getUserByUsername(username);
    return inputUser;
  }

///// RETURNS empty UserModel if invalid input or no such user
  public UserModel getUserByUsername(String username) throws SQLException, NamingException {
    ///// VARIABLES /////
    UserModel returnUser = new UserModel();

    ///// VALIDATE INPUT /////
    Boolean validUsername = ValidateUtility.validateUsername(username);
    if (!validUsername) {
      return returnUser;
    }

    ///// GET USERMODEL FROM DB /////
    try {
      openConnection();
      String query = "SELECT * FROM Users WHERE username = ?;";
      stmt = con.prepareStatement(query);
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        rs.next();
        returnUser.setUid(rs.getInt("uid"));
        returnUser.setUsername(rs.getString("username"));
        returnUser.setSHPassword(rs.getString("shpw"));
        returnUser.setDisplayName(rs.getString("displayname"));
        returnUser.setEmail(rs.getString("email"));
        returnUser.setJoinDate(rs.getTimestamp("joindate").toInstant());
        returnUser.setKarma(rs.getInt("karma"));
        returnUser.setIsLocked(rs.getTimestamp("islocked").toInstant());
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
    return returnUser;
  }

///// RETURNS empty UserModel if invalid input or no such user
  public UserModel getUserByUid(int uid) throws SQLException, NamingException {
    ///// VARIABLES /////
    UserModel returnUser = new UserModel();

    ///// GET USERMODEL FROM DB /////
    try {
      openConnection();
      String query = "SELECT * FROM Users WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        rs.next();
        returnUser.setUid(rs.getInt("uid"));
        returnUser.setUsername(rs.getString("username"));
        returnUser.setSHPassword(rs.getString("shpw"));
        returnUser.setDisplayName(rs.getString("displayname"));
        returnUser.setEmail(rs.getString("email"));
        returnUser.setJoinDate(rs.getTimestamp("joindate").toInstant());
        returnUser.setKarma(rs.getInt("karma"));
        returnUser.setIsLocked(rs.getTimestamp("islocked").toInstant());
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
    return returnUser;
  }

  public void deleteUser(int uid) throws SQLException, NamingException {

    if (getUserByUid(uid).isEmpty()) {
      throw new IllegalArgumentException("No such username");
    }

    ///// DELETE RECORD FROM DB /////
    try {
      deleteSecurityQuestionsByUid(uid);
      openConnection();
      String query = "DELETE FROM Users WHERE uid = ?;";
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

  public void updateUser(UserModel inputUser) throws SQLException, NamingException, IllegalArgumentException {

    int uid = inputUser.getUid();
    String username = inputUser.getUsername();
    String shpw = inputUser.getSHPassword();
    String displayName = inputUser.getDisplayName();
    String email = inputUser.getEmail();
    int karma = inputUser.getKarma();
    Instant isLocked = inputUser.getIsLocked();

    if (getUserByUsername(username).isEmpty()) {
      throw new IllegalArgumentException("No such username");
    }

    ///// VALIDATE INPUT /////
    if (!ValidateUtility.validateSHPW(shpw)) {
      throw new IllegalArgumentException("Invalid shpw");
    } else if (!ValidateUtility.validateDisplayName(displayName)) {
      throw new IllegalArgumentException("Invalid displayname");
    } else if (!ValidateUtility.validateEmail(email)) {
      throw new IllegalArgumentException("Invalid email");
    }
    ///// UPDATE RECORD IN DB /////
    try {
      openConnection();
      String query = "UPDATE Users SET shpw = ?, displayname = ?, email = ?, karma = ?, islocked = ? WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setString(1, shpw);
      stmt.setString(2, displayName);
      stmt.setString(3, email);
      stmt.setInt(4, karma);
      stmt.setTimestamp(5, Timestamp.from(isLocked));
      stmt.setInt(6, uid);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public SecurityQuestionModel createSecurityQuestion(SecurityQuestionModel createSQM) throws SQLException, NamingException, IllegalArgumentException {
    int auto_id;
    int uid = createSQM.getUid();
    String question = createSQM.getQuestion();
    String shanswer = createSQM.getSHAnswer();

    ///// VALIDATE INPUT /////

    if (!ValidateUtility.validateSecQuestion(question)) {
      throw new IllegalArgumentException("Invalid question");
    } else if (!ValidateUtility.validateSHAnswer(shanswer)) {
      throw new IllegalArgumentException("Invalid shanswer");
    }

    ///// CREATE RECORD IN DB /////
    try {
      openConnection();
      String query = "INSERT INTO SecurityQuestions (uid, question, shanswer) VALUES (?, ?, ?);";
      stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, uid);
      stmt.setString(2, question);
      stmt.setString(3, shanswer);
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
    createSQM.setQid(auto_id);
    return createSQM;
  }

  public List<SecurityQuestionModel> getSecurityQuestionsByUid(int uid) throws SQLException, NamingException {
    List<SecurityQuestionModel> outputSQArray = new ArrayList<SecurityQuestionModel>();
    try {
      openConnection();
      String query = "SELECT * FROM SecurityQuestions WHERE uid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
          SecurityQuestionModel tempSQM = new SecurityQuestionModel();
          tempSQM.setQid(rs.getInt("qid"));
          tempSQM.setUid(rs.getInt("uid"));
          tempSQM.setQuestion(rs.getString("question"));
          tempSQM.setSHAnswer(rs.getString("shanswer"));
          outputSQArray.add(tempSQM);
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
    return outputSQArray;
  }

  public SecurityQuestionModel getSecurityQuestionByQid(int qid) throws SQLException, NamingException {
    SecurityQuestionModel tempSQM = new SecurityQuestionModel();
    try {
      openConnection();
      String query = "SELECT * FROM SecurityQuestions WHERE qid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, qid);
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        rs.next();
        tempSQM.setQid(rs.getInt("qid"));
        tempSQM.setUid(rs.getInt("uid"));
        tempSQM.setQuestion(rs.getString("question"));
        tempSQM.setSHAnswer(rs.getString("shanswer"));
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
    return tempSQM;
  }

  public void deleteSecurityQuestionsByUid(int uid) throws SQLException, NamingException {
    ///// DELETE RECORD FROM DB /////
    try {
      openConnection();
      String query = "DELETE FROM SecurityQuestions WHERE uid = ?;";
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

  public void updateSecurityQuestion(SecurityQuestionModel updateSQM) throws SQLException, NamingException, IllegalArgumentException {
    int qid = updateSQM.getQid();
    int uid = updateSQM.getUid();
    String question = updateSQM.getQuestion();
    String shanswer = updateSQM.getSHAnswer();

    ///// VALIDATE INPUT /////

    if (!ValidateUtility.validateSecQuestion(question)) {
      throw new IllegalArgumentException("Invalid question");
    } else if (!ValidateUtility.validateSHAnswer(shanswer)) {
      throw new IllegalArgumentException("Invalid shanswer");
    } else if (getSecurityQuestionByQid(qid).isEmpty()) {
      throw new IllegalArgumentException("No such question");
    }

    ///// UPDATE RECORD IN DB /////
    try {
      openConnection();
      String query = "UPDATE SecurityQuestions SET question = ?, shanswer = ? WHERE qid = ?;";
      stmt = con.prepareStatement(query);
      stmt.setString(1, question);
      stmt.setString(2, shanswer);
      stmt.setInt(3, qid);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public void createLoginRecord(LoginAttemptModel lam) throws SQLException, NamingException {

    int uid = lam.getUid();
    Boolean succeeded = lam.getSucceeded();

    ///// CREATE RECORD IN DB /////
    try {
      openConnection();
      String query = "INSERT INTO LoginAttempts (uid, logintime, succeeded) VALUES (?, NOW(), ?);";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      stmt.setBoolean(2, succeeded);
      stmt.execute();
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
  }

  public List<LoginAttemptModel> getLoginRecordsByUidAndTime(int uid, Instant recentTime) throws SQLException, NamingException {
    List<LoginAttemptModel> outputLAMArray = new ArrayList<LoginAttemptModel>();
    try {
      openConnection();
      String query = "SELECT * FROM LoginAttempts WHERE uid = ? AND logintime > ?;";
      stmt = con.prepareStatement(query);
      stmt.setInt(1, uid);
      stmt.setTimestamp(2, Timestamp.from(recentTime));
      rs = stmt.executeQuery();
      if (rs.isBeforeFirst() && rs.getRow() == 0) {
        while (rs.next()) {
          LoginAttemptModel tempLAM = new LoginAttemptModel();
          tempLAM.setLid(rs.getInt("logid"));
          tempLAM.setUid(rs.getInt("uid"));
          tempLAM.setAttemptDate(rs.getTimestamp("logintime").toInstant());
          tempLAM.setSucceeded(rs.getBoolean("succeeded"));
          outputLAMArray.add(tempLAM);
        }
      }
    }
    catch (SQLException|NamingException ex) {
      throw ex;
    }
    finally {
      closeConnection();
    }
    return outputLAMArray;
  }

  public void deleteLoginRecordsByUid(int uid) throws SQLException, NamingException {
    try {
      openConnection();
      String query = "DELETE FROM LoginAttempts WHERE uid = ?;";
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
}

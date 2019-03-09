package com.myforum.login;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.myforum.login.models.UserModel;
import com.myforum.login.models.SecurityQuestionModel;

public class tester extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.write("<html><body>HELLO! <br>");
    out.write("testing13<br><br><br>");
    LoginService testLogin = new LoginService();

    UserModel user;

    /* = new UserModel("testytester", "testytester", "myPassword", "myemail@email.com");
    List<SecurityQuestionModel> sqmList = new ArrayList<SecurityQuestionModel>();
    sqmList.add(new SecurityQuestionModel("question1", "answer1"));
    sqmList.add(new SecurityQuestionModel("question2", "answer2"));
    sqmList.add(new SecurityQuestionModel("question3", "answer3")); */
    try {
      user = testLogin.getUser("tuser1");

      // display user info here
      out.write("UID: " + user.getUid() + "<br>");
      out.write("Username: " + user.getUsername() + "<br>");
      out.write("Password: " + user.getSHPassword() + "<br>");
      out.write("DisplayName: " + user.getDisplayName() + "<br>");
      out.write("Email: " + user.getEmail() + "<br>");
      out.write("Karma: " + user.getKarma() + "<br><br>");
/*
      user.setKarma(5);
      user.setDisplayName("Jojo");
      user.setEmail("hisemail@email.com");
      testLogin.updateUserNoPW(user);
      user = testLogin.getUser("testytester");

      // display user info here again
      out.write("UID: " + user.getUid() + "<br>");
      out.write("Username: " + user.getUsername() + "<br>");
      out.write("Password: " + user.getSHPassword() + "<br>");
      out.write("DisplayName: " + user.getDisplayName() + "<br>");
      out.write("Email: " + user.getEmail() + "<br>");
      out.write("Karma: " + user.getKarma() + "<br><br>");


      sqmList = testLogin.getSecQs("testytester");

      // display sec q info here
      for(SecurityQuestionModel sqm: sqmList) {
        out.write("QID:" + sqm.getQid() + "<br>");
        out.write("UID:" + sqm.getUid() + "<br>");
        out.write("Quesiton:" + sqm.getQuestion() + "<br>");
        out.write("Answer:" + sqm.getSHAnswer() + "<br><br>");
      }

      SecurityQuestionModel sqmTemp = sqmList.get(0);
      sqmTemp.setSHAnswer("answer1");
      sqmList.set(0, sqmTemp);
      sqmTemp = sqmList.get(1);
      sqmTemp.setSHAnswer("answer2");
      sqmList.set(1, sqmTemp);
      sqmTemp = sqmList.get(2);
      sqmTemp.setSHAnswer("answer3");
      sqmList.set(2, sqmTemp);

      Boolean validated = false;

      testLogin.resetPassword("testytester", "abcd", sqmList);

      validated = testLogin.validateCredentials("testytester", "asdf");

      // display validated here again
      out.write("credential Validator after sec q password reset, should be false: " + validated + "<br><br>");

      validated = testLogin.validateCredentials("testytester", "abcd");

      // display validated here again
      out.write("credential Validator after sec q password reset, should be true: " + validated + "<br><br>");

      testLogin.deleteUser("testytester");

      // then validate with mysql */
    } catch (IllegalArgumentException|SQLException|NamingException|IllegalStateException ex) {
      out.write("Error" + ex);
    }


    out.write("FINISHED!");
  }
}

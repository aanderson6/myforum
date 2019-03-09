package com.myforum.messaging;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import javax.naming.NamingException;

/* With existing user account - create a couple each of conversation, message membership, filter, settings, and notification
how to verify created?

userX:
create conversation
create membership
create message
create filter
create settings
create notification

userY:
create conversation
create membership
create message
create filter
create settings
create notification

UserX:
get conversation
get membership
get message
get filter
get settings
get notification

UserY:
get conversation
get membership
get message
get filter
get settings
get notification

Do the befores and afters match? (yes = pass, no = fail)



UserX:
update membership
update setting
update comversation
update message

UserX:
get membership
get setting
get conversation
get message

Do the befores and afters match? (yes = pass, no = fail)



getMembershipByUidCount
getOrderedMembershipByUid
getMessagesByCID
getSettingsByUID
getNotificationsByUID

Does return match original expected input? (yes = pass, no = fail)



Delete some items:

deleteFilterByFID
deleteFilterByUID
deleteFilterByUidMidBa
deleteNotificationByNid
deleteNotificationsByUid
deleteSettingsByUID
deleteMembershipByUID


Try to 'get' them, verify that they do not exist (none exist = pass, some exist = fail)


*/



public class tester extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.write("<html><body>HELLO! <br>");
    out.write("testing13<br><br><br>");

    try {

    } catch (IllegalArgumentException|SQLException|NamingException ex) {

    }


    out.write("FINISHED!");
  }
}

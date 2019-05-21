package com.myforum.rest;

import com.myforum.login.dtos.*;
import com.myforum.login.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class LoginRestController {
    @Context
    private HttpServletRequest request;

    private LoginInterface loginService = new LoginService();

    @POST
    @Path("/create")
    public Response createUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("displayName") String displayName, @FormParam("email") String email, @FormParam("question1") String question1, @FormParam("answer1") String answer1, @FormParam("question2") String question2,
    @FormParam("answer2") String answer2, @FormParam("question3") String question3, @FormParam("answer3") String answer3) {
      HttpSession session = request.getSession();
      if(session.getAttribute("validatedUsername") != null) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      int created;
      String[][] sqeArray = {{question1, answer1}, {question2, answer2}, {question3, answer3}};
      try {
        created = loginService.createUser(username, password, displayName, email, sqeArray);
        session.setAttribute("validatedUsername", username);
        return Response.status(Response.Status.OK).entity(created).build();
      }
      catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("Invalid Username") || ex.getMessage().equals("Invalid Display Name") || ex.getMessage().equals("Invalid Email") || ex.getMessage().equals("Invalid Password") || ex.getMessage().equals("Invalid Security Info") || ex.getMessage().equals("Username Taken")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      }
      catch(Exception ex) {
        // log it!
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/login")
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
      Boolean validated;
      try {
        validated = loginService.validateCredentials(username, password);
        if(validated) {
          HttpSession session = request.getSession();
          session.setAttribute("validatedUsername", username);
        }
        return Response.status(Response.Status.OK).entity(Boolean.toString(validated)).build();

      }
      catch(IllegalArgumentException|IllegalStateException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("User Locked")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      }
      catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/logout")
    public Response logout(@FormParam("username") String username) {
      HttpSession session = request.getSession(false);
      if(session != null) {
        session.invalidate();
      }
      return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/changepassword")
    public Response changePassword(@FormParam("username") String username, @FormParam("oldPassword") String oldPassword, @FormParam("newPassword") String newPassword) {
      Boolean validated;
      try {
        validated = loginService.changePassword(username, oldPassword, newPassword);
        if(validated) {
          HttpSession session = request.getSession();
          session.setAttribute("validatedUsername", username);
        }
        return Response.status(Response.Status.OK).entity(Boolean.toString(validated)).build();
      } catch(IllegalArgumentException|IllegalStateException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("User Locked") || ex.getMessage().equals("Invalid Password")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/resetpassword")
    public Response resetPassword(@FormParam("username") String username, @FormParam("newPassword") String newPassword, @FormParam("question1") String question1, @FormParam("answer1") String answer1, @FormParam("question2") String question2,
    @FormParam("answer2") String answer2, @FormParam("question3") String question3, @FormParam("answer3") String answer3) {
      Boolean validated;
      String[][] sqeArray = {{question1, answer1},{question2, answer2}, {question3, answer3}};
      try {
        validated = loginService.resetPassword(username, newPassword, sqeArray);
        if(validated) {
          HttpSession session = request.getSession();
          session.setAttribute("validatedUsername", username);
        }
        return Response.status(Response.Status.OK).entity(Boolean.toString(validated)).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("Invalid Password")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/updatedisplayname")
    public Response updateUserDisplayName(@FormParam("username") String username, @FormParam("displayName") String displayName) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        loginService.updateUserDisplayName(username, displayName);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("Invalid Display Name")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/updateemail")
    public Response updateUserEmail(@FormParam("username") String username, @FormParam("email") String email) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        loginService.updateUserEmail(username, email);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("Invalid Email")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getinfo")
    public Response getUserInfo(@FormParam("username") String username) {
      UserDTO user;
      try {
        user = loginService.getUserInfo(username);
        return Response.status(Response.Status.OK).entity(user).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getqs")
    public Response getUserQuestions(@FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      String[] questions;
      try {
        questions = loginService.getUserQuestions(username);
        return Response.status(Response.Status.OK).entity(questions).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/updateqs")
    public Response updateSecQs(@FormParam("username") String username, @FormParam("password") String password, @FormParam("question1") String question1, @FormParam("answer1") String answer1, @FormParam("question2") String question2,
    @FormParam("answer2") String answer2, @FormParam("question3") String question3, @FormParam("answer3") String answer3) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      Boolean validInput;
      String[][] sqeUpdateArray = {{question1, answer1},{question2, answer2}, {question3, answer3}};
      try {
        validInput = loginService.updateSecQs(username, password, sqeUpdateArray);
        return Response.status(Response.Status.OK).entity(Boolean.toString(validInput)).build();
      } catch(IllegalArgumentException|IllegalStateException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("Invalid Security Info") || ex.getMessage().equals("User Locked")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/delete")
    public Response deleteUser(@FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        loginService.deleteUser(username);
        return Response.status(Response.Status.OK).build();
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

}

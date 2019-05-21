package com.myforum.rest;

import com.myforum.messaging.dtos.*;
import com.myforum.messaging.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

@Path("/messaging")
@Produces(MediaType.APPLICATION_JSON)
public class MessagingRestController {
    @Context
    private HttpServletRequest request;

    private MessagingInterface messageService = new MessagingService();

    @POST
    @Path("/startconversation")
    public Response startConversation(@FormParam("fromUsername") String fromUsername, @FormParam("toUsernameList") List<String> toUsernameList, @FormParam("subject") String subject, @FormParam("content") String content) {
      HttpSession session = request.getSession();
      if(!fromUsername.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        messageService.startConversation(fromUsername, toUsernameList, subject, content);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such From User") || ex.getMessage().equals("Invalid Subject") || ex.getMessage().equals("Invalid Content") || ex.getMessage().startsWith("No Such To User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/replymessage")
    public Response replyMessage(@FormParam("fromUsername") String fromUsername, @FormParam("conversationId") int conversationId, @FormParam("subject") String subject, @FormParam("content") String content) {
      HttpSession session = request.getSession();
      if(!fromUsername.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        messageService.replyMessage(fromUsername, conversationId, subject, content);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such From User") || ex.getMessage().equals("Invalid Subject") || ex.getMessage().equals("Invalid Content") || ex.getMessage().startsWith("No Such Conversation") || ex.getMessage().startsWith("User Not Conversation Member")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getorderedconversationsrange")
    public Response getOrderedConversationsRange(@FormParam("username") String username, @FormParam("start") int start, @FormParam("returnCount") int returnCount) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        List<ConversationDTO> conversationList = messageService.getOrderedConversationsRange(username, start, returnCount);
        return Response.status(Response.Status.OK).entity(conversationList).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("Out of Range")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/hideconversationbyuser")
    public Response hideConversationByUser(@FormParam("username") String username, @FormParam("conversationId") int conversationId) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        messageService.hideConversationByUser(username, conversationId);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Conversation") || ex.getMessage().equals("User Not Conversation Member")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/hasreadmessage")
    public Response hasReadMessage(@FormParam("username") String username, @FormParam("messageId") int messageId) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        messageService.hasReadMessage(username, messageId);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Message") || ex.getMessage().equals("User Not Conversation Member")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/updatesettings")
    public Response updateSettings(@FormParam("username") String username, @FormParam("collapseReadMessages") boolean collapseReadMessages, @FormParam("allOrWhiteList") boolean allOrWhiteList, @FormParam("notifyUnreadMessages") boolean notifyUnreadMessages, @FormParam("markReadIfOpened") boolean markReadIfOpened) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        messageService.updateSettings(username, collapseReadMessages, allOrWhiteList, notifyUnreadMessages, markReadIfOpened);
        return Response.status(Response.Status.OK).build();
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
    @Path("/addfilter")
    public Response addFilter(@FormParam("username") String username, @FormParam("managedUsername") String managedUsername, @FormParam("blockAllow") boolean blockAllow) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        messageService.addFilter(username, managedUsername, blockAllow);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Managed User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/removefilter")
    public Response removeFilter(@FormParam("username") String username, @FormParam("managedUsername") String managedUsername, @FormParam("blockAllow") boolean blockAllow) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        messageService.removeFilter(username, managedUsername, blockAllow);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Managed User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getsettings")
    public Response getSettings(@FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        SettingsDTO settings = messageService.getSettings(username);
        return Response.status(Response.Status.OK).entity(settings).build();
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
    @Path("/getfilters")
    public Response getFilters(@FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        List<FilterDTO> filters = messageService.getFilters(username);
        return Response.status(Response.Status.OK).entity(filters).build();
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
    @Path("/getnotifications")
    public Response getNotifications(@FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        List<NotificationDTO> notifications = messageService.getNotifications(username);
        return Response.status(Response.Status.OK).entity(notifications).build();
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
    @Path("/getconversationcount")
    public Response getConversationCount(@FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        int count = messageService.getConversationCount(username);
        return Response.status(Response.Status.OK).entity(count).build();
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

}

package com.myforum.rest;

import com.myforum.subforum.dtos.*;
import com.myforum.subforum.*;

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

@Path("/subforums")
@Produces(MediaType.APPLICATION_JSON)
public class SubforumRestController {
    @Context
    private HttpServletRequest request;

    private SubforumInterface subforumService = new SubforumService();

    @POST
    @Path("/create")
    public Response createSubforum(@FormParam("username") String username, @FormParam("name") String name, @FormParam("description") String description, @FormParam("flair") String flair, @FormParam("subforumRules") String subforumRules) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      try {
        subforumService.createSubforum(username, name, description, flair, subforumRules);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("Invalid Subforum Name") || ex.getMessage().equals("Invalid Description") || ex.getMessage().equals("Invalid Flair") || ex.getMessage().equals("Invalid Rules") || ex.getMessage().equals("No Such User") || ex.getMessage().equals("Subforum Name Taken")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/updatedescription")
    public Response updateSubforumDescription(@FormParam("subforumName") String subforumName, @FormParam("description") String description) {
      HttpSession session = request.getSession();
      if(subforumService.subforumExists(subforumName)){
        try {
          Boolean authorized = false;
          List<ModDTO> modList = subforumService.getModsBySubforumOrderedByRank(subforumName);
          for(ModDTO mod : modList) {
            if(mod.getUsername().equals(session.getAttribute("validatedUsername"))) {
              authorized = true;
            }
          }
          if(!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
          }

          subforumService.updateSubforumDescription(subforumName, description);
          return Response.status(Response.Status.OK).build();
        } catch(IllegalArgumentException ex) {
          if(ex.getMessage().equals("Invalid Description") || ex.getMessage().equals("No Such Subforum")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
          }
        } catch (Exception ex) {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

      } else {
        return Response.status(Response.Status.BAD_REQUEST).entity("No Such Subforum").build();
      }
    }

    @POST
    @Path("/updateflair")
    public Response updateSubforumFlair(@FormParam("subforumName") String subforumName, @FormParam("flair") String flair) {
      HttpSession session = request.getSession();
      if(subforumService.subforumExists(subforumName)){
        try {
          Boolean authorized = false;
          List<ModDTO> modList = subforumService.getModsBySubforumOrderedByRank(subforumName);
          for(ModDTO mod : modList) {
            if(mod.getUsername().equals(session.getAttribute("validatedUsername"))) {
              authorized = true;
            }
          }
          if(!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
          }

          subforumService.updateSubforumFlair(subforumName, flair);
          return Response.status(Response.Status.OK).build();
        } catch(IllegalArgumentException ex) {
          if(ex.getMessage().equals("Invalid Flair") || ex.getMessage().equals("No Such Subforum")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
          }
        } catch (Exception ex) {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

      } else {
        return Response.status(Response.Status.BAD_REQUEST).entity("No Such Subforum").build();
      }
    }

    @POST
    @Path("/updaterules")
    public Response updateSubforumRules(@FormParam("subforumName") String subforumName, @FormParam("rules") String rules) {
      HttpSession session = request.getSession();
      if(subforumService.subforumExists(subforumName)){
        try {
          Boolean authorized = false;
          List<ModDTO> modList = subforumService.getModsBySubforumOrderedByRank(subforumName);
          for(ModDTO mod : modList) {
            if(mod.getUsername().equals(session.getAttribute("validatedUsername"))) {
              authorized = true;
            }
          }
          if(!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
          }

          subforumService.updateSubforumRules(subforumName, rules);
          return Response.status(Response.Status.OK).build();
        } catch(IllegalArgumentException ex) {
          if(ex.getMessage().equals("Invalid Rules") || ex.getMessage().equals("No Such Subforum")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
          }
        } catch (Exception ex) {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

      } else {
        return Response.status(Response.Status.BAD_REQUEST).entity("No Such Subforum").build();
      }
    }

    @POST
    @Path("/subscribe")
    public Response subscribeToSubforum(@FormParam("username") String username, @FormParam("subforumName") String subforumName) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      try {
        subforumService.subscribeToSubforum(username, subforumName);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/unsubscribe")
    public Response unsubscribeFromSubforum(@FormParam("username") String username, @FormParam("subforumName") String subforumName) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      try {
        subforumService.unsubscribeFromSubforum(username, subforumName);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/addmod")
    public Response addModToSubforum(@FormParam("username") String username, @FormParam("subforumName") String subforumName) {
      HttpSession session = request.getSession();
      if(subforumService.subforumExists(subforumName)){
        try {
          Boolean authorized = false;
          List<ModDTO> modList = subforumService.getModsBySubforumOrderedByRank(subforumName);
          for(ModDTO mod : modList) {
            if(mod.getUsername().equals(session.getAttribute("validatedUsername"))) {
              authorized = true;
            }
          }
          if(!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
          }

          subforumService.addModToSubforum(username, subforumName);
          return Response.status(Response.Status.OK).build();
        } catch(IllegalArgumentException ex) {
          if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
          }
        } catch (Exception ex) {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

      } else {
        return Response.status(Response.Status.BAD_REQUEST).entity("No Such Subforum").build();
      }
    }

    @POST
    @Path("/removemod")
    public Response removeModFromSubforum(@FormParam("username") String username, @FormParam("subforumName") String subforumName) {
      HttpSession session = request.getSession();
      if(subforumService.subforumExists(subforumName)){
        try {
          int requestRank = 0;
          int requestedRank = 0;
          Boolean authorized = false;
          List<ModDTO> modList = subforumService.getModsBySubforumOrderedByRank(subforumName);
          for(ModDTO mod : modList) {
            if(mod.getUsername().equals(session.getAttribute("validatedUsername"))) {
              authorized = true;
            }
            if(mod.getUsername().equals(username)) {
              requestedRank = mod.getRank();
            }
            if(mod.getUsername().equals(session.getAttribute("validatedUsername"))) {
              requestRank = mod.getRank();
            }
          }
          if(!authorized || !(requestRank > requestedRank)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
          }

          subforumService.removeModFromSubforum(username, subforumName);
          return Response.status(Response.Status.OK).build();
        } catch(IllegalArgumentException ex) {
          if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
          }
        } catch (Exception ex) {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

      } else {
        return Response.status(Response.Status.BAD_REQUEST).entity("No Such Subforum").build();
      }
    }

    @POST
    @Path("/ismod")
    public Response isMod(@FormParam("subforumName") String subforumName, @FormParam("username") String username) {
      Boolean isMod;
      try {
        isMod = subforumService.isMod(subforumName, username);
        return Response.status(Response.Status.OK).entity(Boolean.toString(isMod)).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/issubscribed")
    public Response isSubscribed(@FormParam("subforumName") String subforumName, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      Boolean isSubscribed;
      try {
        isSubscribed = subforumService.isMod(subforumName, username);
        return Response.status(Response.Status.OK).entity(Boolean.toString(isSubscribed)).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getsubscriptionsbyuser")
    public Response getSubforumSubscriptionsByUser(@FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        List<SubforumDTO> subforums = subforumService.getSubforumSubscriptionsByUser(username);
        return Response.status(Response.Status.OK).entity(subforums).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getorderedpaginated")
    public Response getSubforumsOrderedPaginated(@FormParam("start") int start, @FormParam("returnCount") int returnCount) {
      try {
        List<SubforumDTO> subforums = subforumService.getSubforumsOrderedPaginated(start, returnCount);
        return Response.status(Response.Status.OK).entity(subforums).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("Out of Range")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getcount")
    public Response getSubforumsCount() {
      try {
        int count = subforumService.getSubforumsCount();
        return Response.status(Response.Status.OK).entity(count).build();
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getmodsorderedbyrank")
    public Response getModsBySubforumOrderedByRank(@FormParam("subforumName") String subforumName) {
      try {
        List<ModDTO> mods = subforumService.getModsBySubforumOrderedByRank(subforumName);
        return Response.status(Response.Status.OK).entity(mods).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Subforum")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/exists")
    public Response subforumExists(@FormParam("subforumName") String subforumName) {
      try {
        Boolean exists = subforumService.subforumExists(subforumName);
        return Response.status(Response.Status.OK).entity(Boolean.toString(exists)).build();
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getbyname")
    public Response getSubforumByName(@FormParam("subforumName") String subforumName) {
      try {
        SubforumDTO subforum = subforumService.getSubforumByName(subforumName);
        return Response.status(Response.Status.OK).entity(subforum).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Subforum")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getsettingsbyname")
    public Response getSubSettingsByName(@FormParam("subforumName") String subforumName) {
      try {
        SubSettingsDTO subsettings = subforumService.getSubSettingsByName(subforumName);
        return Response.status(Response.Status.OK).entity(subsettings).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Subforum")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

}

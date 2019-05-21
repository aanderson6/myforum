package com.myforum.rest;

import com.myforum.posts.dtos.*;
import com.myforum.posts.*;

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

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
public class PostRestController {
    @Context
    private HttpServletRequest request;

    private PostInterface postService = new PostService();

    @POST
    @Path("/create")
    public Response createPost(@FormParam("username") String username, @FormParam("subforumName") String subforumName, @FormParam("title") String title, @FormParam("content") String content) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      try {
        int id = postService.createPost(username, subforumName, title, content);
        return Response.status(Response.Status.OK).entity(id).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("Invalid Title") || ex.getMessage().equals("Invalid Content") || ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/updatetitle")
    public Response updatePostTitle(@FormParam("id") int id, @FormParam("title") String title, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        postService.updatePostTitle(id, title, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException|IllegalStateException ex) {
        if(ex.getMessage().equals("Invalid Title") || ex.getMessage().equals("No Such Post") || ex.getMessage().equals("Not Authorized to Update")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/updatecontent")
    public Response updatePostContent(@FormParam("id") int id, @FormParam("content") String content, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        postService.updatePostContent(id, content, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException|IllegalStateException ex) {
        if(ex.getMessage().equals("Invalid Content") || ex.getMessage().equals("No Such Post") || ex.getMessage().equals("Not Authorized to Update")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/upvote")
    public Response upvotePost(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        postService.upvotePost(id, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Post") || ex.getMessage().equals("No Such User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/downvote")
    public Response downvotePost(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        postService.downvotePost(id, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Post") || ex.getMessage().equals("No Such User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/removevote")
    public Response removeVoteOnPost(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        postService.removeVoteOnPost(id, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Post") || ex.getMessage().equals("No Such User")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/removeuser")
    public Response removeUserFromPost(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        postService.removeUserFromPost(id, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException|IllegalStateException ex) {
        if(ex.getMessage().equals("No Such Post") || ex.getMessage().equals("No Such User") || ex.getMessage().equals("Not Authorized to Delete")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getbysubforumorderedpaginated")
    public Response getBySubforumOrderedPaginated(@FormParam("subforumName") String subforumName, @FormParam("start") int start, @FormParam("returnCount") int returnCount, @FormParam("byUsername") String byUsername) {
      if(byUsername != null) {
        HttpSession session = request.getSession();
        if(!byUsername.equals(session.getAttribute("validatedUsername"))) {
          return Response.status(Response.Status.UNAUTHORIZED).build();
        }
      }

      try {
        List<PostDTO> posts = postService.getBySubforumOrderedPaginated(subforumName, start, returnCount, byUsername);
        return Response.status(Response.Status.OK).entity(posts).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum") || ex.getMessage().equals("Out of Range")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getcountbysubforumfiltered")
    public Response getCountBySubforumFiltered(@FormParam("subforumName") String subforumName, @FormParam("byUsername") String byUsername) {
      if(byUsername != null) {
        HttpSession session = request.getSession();
        if(!byUsername.equals(session.getAttribute("validatedUsername"))) {
          return Response.status(Response.Status.UNAUTHORIZED).build();
        }
      }

      try {
        int count = postService.getCountBySubforumFiltered(subforumName, byUsername);
        return Response.status(Response.Status.OK).entity(count).build();
      }catch(IllegalArgumentException ex) {
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
    @Path("/getbyuserorderedpaginated")
    public Response getByUserOrderedPaginated(@FormParam("username") String username, @FormParam("start") int start, @FormParam("returnCount") int returnCount) {
      try {
        List<PostDTO> posts = postService.getByUserOrderedPaginated(username, start, returnCount);
        return Response.status(Response.Status.OK).entity(posts).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("Out of Range")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getcountbyuser")
    public Response getCountByUser(@FormParam("username") String username) {
      try {
        int count = postService.getCountByUser(username);
        return Response.status(Response.Status.OK).entity(count).build();
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
    @Path("/getforusersubscribedorderedpaginated")
    public Response getForUserSubscribedOrderedPaginated(@FormParam("username") String username, @FormParam("start") int start, @FormParam("returnCount") int returnCount) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      try {
        List<PostDTO> posts = postService.getForUserSubscribedOrderedPaginated(username, start, returnCount);
        return Response.status(Response.Status.OK).entity(posts).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("Out of Range")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/getcountforusersubscribed")
    public Response getCountForUserSubscribed(@FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      try {
        int count = postService.getCountForUserSubscribed(username);
        return Response.status(Response.Status.OK).entity(count).build();
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
    @Path("/getbyid")
    public Response getById(@FormParam("id") int id) {
      try {
        PostDTO post = postService.getById(id);
        return Response.status(Response.Status.OK).entity(post).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Post")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch (Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

}

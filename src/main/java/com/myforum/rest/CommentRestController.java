package com.myforum.rest;

import com.myforum.comments.dtos.*;
import com.myforum.comments.*;

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

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
public class CommentRestController {
    @Context
    private HttpServletRequest request;

    private CommentInterface commentService = new CommentService();

    @POST
    @Path("/create")
    public Response createComment(@FormParam("username") String username, @FormParam("postId") int postId, @FormParam("subforumName") String subforumName, @FormParam("content") String content, @FormParam("parentCommentId") Integer parentCommentId) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      try {
        int id = commentService.createComment(username, postId, subforumName, content, parentCommentId);
        return Response.status(Response.Status.OK).entity(id).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such User") || ex.getMessage().equals("No Such Subforum") || ex.getMessage().equals("No Such Post") || ex.getMessage().equals("Invalid Content")) {
          return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } else {
          return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      } catch(Exception ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      }
    }

    @POST
    @Path("/hasRead")
    public Response hasReadComment(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        commentService.hasReadComment(id, username);
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
    @Path("/updatecontent")
    public Response updateCommentContent(@FormParam("id") int id, @FormParam("content") String content, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        commentService.updateCommentContent(id, content, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException|IllegalStateException ex) {
        if(ex.getMessage().equals("Not Authorized to Update") || ex.getMessage().equals("No Such Comment") || ex.getMessage().equals("Invalid Content")) {
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
    public Response upvoteComment(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        commentService.upvoteComment(id, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Comment") || ex.getMessage().equals("No Such User")) {
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
    public Response downvoteComment(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        commentService.downvoteComment(id, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Comment") || ex.getMessage().equals("No Such User")) {
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
    public Response removeVoteOnComment(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        commentService.removeVoteOnComment(id, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Comment") || ex.getMessage().equals("No Such User")) {
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
    public Response removeUserFromComment(@FormParam("id") int id, @FormParam("username") String username) {
      HttpSession session = request.getSession();
      if(!username.equals(session.getAttribute("validatedUsername"))) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      try {
        commentService.removeUserFromComment(id, username);
        return Response.status(Response.Status.OK).build();
      } catch(IllegalArgumentException|IllegalStateException ex) {
        if(ex.getMessage().equals("No Such Comment") || ex.getMessage().equals("No Such User") || ex.getMessage().equals("Not Authorized to Delete")) {
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
        CommentDTO comment = commentService.getById(id);
        return Response.status(Response.Status.OK).entity(comment).build();
      } catch(IllegalArgumentException ex) {
        if(ex.getMessage().equals("No Such Comment")) {
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
        List<CommentDTO> comments = commentService.getByUserOrderedPaginated(username, start, returnCount);
        return Response.status(Response.Status.OK).entity(comments).build();
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
        int count = commentService.getCountByUser(username);
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
    @Path("/getbypost")
    public Response getByPost(@FormParam("postId") int postId, @FormParam("requestingUsername") String requestingUsername, @FormParam("start") int start, @FormParam("returnCount") int returnCount, @FormParam("depth") int depth) {
      if(requestingUsername != null) {
        HttpSession session = request.getSession();
        if(!requestingUsername.equals(session.getAttribute("validatedUsername"))) {
          return Response.status(Response.Status.UNAUTHORIZED).build();
        }
      }

      try {
        List<CommentDTO> comments = commentService.getByPost(postId, requestingUsername, start, returnCount, depth);
        return Response.status(Response.Status.OK).entity(comments).build();
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
    @Path("/getcountbypostfiltered")
    public Response getCountByPostFiltered(@FormParam("postId") int postId, @FormParam("requestingUsername") String requestingUsername) {
      if(requestingUsername != null) {
        HttpSession session = request.getSession();
        if(!requestingUsername.equals(session.getAttribute("validatedUsername"))) {
          return Response.status(Response.Status.UNAUTHORIZED).build();
        }
      }

      try {
        int count = commentService.getCountByPostFiltered(postId, requestingUsername);
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
    @Path("/getchildrenof")
    public Response getChildrenOf(@FormParam("commentId") int commentId, @FormParam("requestingUsername") String requestingUsername, @FormParam("start") int start, @FormParam("returnCount") int returnCount, @FormParam("depth") int depth) {
      if(requestingUsername != null) {
        HttpSession session = request.getSession();
        if(!requestingUsername.equals(session.getAttribute("validatedUsername"))) {
          return Response.status(Response.Status.UNAUTHORIZED).build();
        }
      }

      try {
        List<CommentDTO> comments = commentService.getChildrenOf(commentId, requestingUsername, start, returnCount, depth);
        return Response.status(Response.Status.OK).entity(comments).build();
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
    @Path("/getcountchildrenof")
    public Response getCountChildrenOf(@FormParam("commentId") int commentId, @FormParam("requestingUsername") String requestingUsername) {
      if(requestingUsername != null) {
        HttpSession session = request.getSession();
        if(!requestingUsername.equals(session.getAttribute("validatedUsername"))) {
          return Response.status(Response.Status.UNAUTHORIZED).build();
        }
      }

      try {
        int count = commentService.getCountByPostFiltered(commentId, requestingUsername);
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

}

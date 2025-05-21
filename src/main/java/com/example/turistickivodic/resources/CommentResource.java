package com.example.turistickivodic.resources;

import com.example.turistickivodic.entities.Comment;
import com.example.turistickivodic.services.CommentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @GET
    @Path("/article/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsByArticleId(@PathParam("articleId") int articleId) {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);
        if (comments == null || comments.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"No comments found\"}").build();
        }
        return Response.ok(comments).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(Comment comment) {
        try {
            Comment newComment = commentService.addComment(comment);
            return Response.status(Response.Status.CREATED).entity(newComment).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @DELETE
    @Path("/article/{articleId}")
    public Response deleteCommentsByArticleId(@PathParam("articleId") int articleId) {
        try {
            commentService.deleteCommentsByArticleId(articleId);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}

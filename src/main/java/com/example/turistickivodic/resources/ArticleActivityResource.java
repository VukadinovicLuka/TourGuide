package com.example.turistickivodic.resources;

import com.example.turistickivodic.entities.Activity;
import com.example.turistickivodic.requests.ArticleActivityRequest;
import com.example.turistickivodic.services.ArticleActivityService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/article_activities")
public class ArticleActivityResource {

    @Inject
    private ArticleActivityService articleActivitiesService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addArticleActivity(ArticleActivityRequest request) {
        try {
            articleActivitiesService.addArticleActivity(request.getArticleId(), request.getActivityId());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/activity/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivityIdsByArticleId(@PathParam("articleId") int articleId) {
        List<Integer> activityIds = articleActivitiesService.getActivityIdsByArticleId(articleId);
        if (activityIds == null || activityIds.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("No activities found for the given destination").build();
        }
        return Response.ok(activityIds).build();
    }

    @GET
    @Path("/{activityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleIdsByActivityId(@PathParam("activityId") int activityId) {
        List<Integer> articleIds = articleActivitiesService.getArticleIdsByActivityId(activityId);
        if (articleIds.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No articles found for this activity").build();
        }
        return Response.ok(articleIds).build();
    }

    @DELETE
    @Path("/{articleId}")
    public Response deleteArticleActivity(@PathParam("articleId") int articleId) {
        boolean deleted = articleActivitiesService.deleteArticleActivity(articleId);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}

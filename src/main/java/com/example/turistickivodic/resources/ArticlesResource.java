package com.example.turistickivodic.resources;

import com.example.turistickivodic.entities.Article;
import com.example.turistickivodic.services.ArticleService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/articles")
public class ArticlesResource {

    @Inject
    private ArticleService articleService;

    @GET
    @Path("/{destinationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticlesByDestination(
            @PathParam("destinationId") int destinationId,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size) {

        System.out.println("DestinationId: " + destinationId);
        int offset = (page - 1) * size;
        List<Article> articles = articleService.getArticlesByDestination(destinationId, offset, size);
        int totalArticles = articleService.getTotalArticlesByDestination(destinationId);

        System.out.println("Vracena velicina u destinacijama:" + articles.size());
        if (articles == null || articles.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"No articles found\"}").build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("total", totalArticles);

        return Response.ok(response).build();
    }


    @POST
    @Path("/article/{id}/visit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVisitCount(@PathParam("id") int id, Map<String, Integer> visitCount) {
        boolean updated = articleService.updateVisitCount(id, visitCount.get("visitCount"));
        if (updated) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Article not found\"}").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticles(@QueryParam("page") @DefaultValue("1") int page,
                                @QueryParam("size") @DefaultValue("10") int size) {
        List<Article> articles = articleService.getArticles(page, size);
        int totalArticles = articleService.getTotalArticles();
        System.out.println("Velicina dovucenih artikala:" + articles.size());
        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("total", totalArticles);
        return Response.ok(response).build();
    }


    @GET
    @Path("/article/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticleById(@PathParam("id") int id) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Article not found\"}").build();
        }
        return Response.ok(article).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteArticle(@PathParam("id") Integer id) {
        System.out.println("Received request to delete article with ID: " + id);
        boolean deleted = articleService.deleteArticle(id);
        if (deleted) {
            System.out.println("Article with ID " + id + " deleted successfully.");
            return Response.ok().build();
        } else {
            System.out.println("Article with ID " + id + " not found.");
            return Response.status(Response.Status.NOT_FOUND).entity("Article not found").build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addArticle(Article article) {
        try {
            System.out.println("Received article data: " + article); // Log received article data
            Article newArticle = articleService.addArticle(article);
            if (newArticle == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid article data").build();
            }
            return Response.status(Response.Status.CREATED).entity(newArticle).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateArticle(@PathParam("id") int id, Article article) {
        Article updatedArticle = articleService.updateArticle(id, article);
        if (updatedArticle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedArticle).build();
    }

    @GET
    @Path("/top-10")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getTop10Articles() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return articleService.findTop10ArticlesSince(thirtyDaysAgo);
    }

}

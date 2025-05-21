package com.example.turistickivodic.resources;


import com.example.turistickivodic.entities.Activity;
import com.example.turistickivodic.services.ActivityService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/activity")
public class ActivityResource {

    @Inject
    private ActivityService activityService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivities() {
        List<Activity> activities = activityService.getAllActivities();
        if (activities == null || activities.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(activities).build();
    }


    @GET
    @Path("/{destinationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivitiesByDestinationId(@PathParam("destinationId") int destinationId) {
        List<Activity> activities = activityService.getActivitiesByDestinationId(destinationId);
        if (activities == null || activities.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(activities).build();
    }

    @GET
    @Path("/name/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivityById(@PathParam("id") int id) {
        Activity activity = activityService.getActivityById(id); // Note the singular method
        if (activity == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(activity).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addActivity(Activity activity) {
        System.out.println("Received activity: " + activity.getName() + " for destination: " + activity.getDestinationId());
        Activity newActivity = activityService.addActivity(activity);
        if (newActivity == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid activity data").build();
        }
        return Response.status(Response.Status.CREATED).entity(newActivity).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivityByDestinationIdAndName(@QueryParam("destinationId") int destinationId, @QueryParam("name") String name) {
        Activity activity = activityService.getActivityByDestinationIdAndName(destinationId, name);
        if (activity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Activity not found").build();
        }
        return Response.ok(activity).build();
    }


    @DELETE
    @Path("/destination/{destinationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteActivitiesByDestination(@PathParam("destinationId") int destinationId) {
        boolean success = activityService.deleteActivitiesByDestination(destinationId);
        if (success) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete activities for the destination").build();
        }
    }

}

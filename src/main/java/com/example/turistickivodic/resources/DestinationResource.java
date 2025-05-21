package com.example.turistickivodic.resources;

import com.example.turistickivodic.entities.Destination;
import com.example.turistickivodic.entities.User;
import com.example.turistickivodic.services.DestinationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/destinations")
public class DestinationResource {

    @Inject
    private DestinationService destinationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestinations() {
        List<Destination> destinations = destinationService.getAllUsers();
        return Response.ok(destinations).build();
    }


    @GET
    @Path("/pagination")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestinations(@QueryParam("page") @DefaultValue("1") int page,
                                    @QueryParam("size") @DefaultValue("10") int size) {
        List<Destination> destinations = destinationService.getDestinationsWithPagination(page, size);
        int total = destinationService.getTotalDestinationsCount();
        Map<String, Object> response = new HashMap<>();
        response.put("destinations", destinations);
        response.put("total", total);
        return Response.ok(response).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestinationById(@PathParam("id") int id) {
        Destination destination = destinationService.getDestinationById(id);
        if (destination == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Destination not found").build();
        }
        return Response.ok(destination).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDestination(Destination destination) {
        destinationService.addDestination(destination);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDestination(@PathParam("id") Integer id) {
        boolean deleted = destinationService.deleteDestination(id);
        if (deleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Destination not found").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDestination(@PathParam("id") int id, Destination destination) {
        // Ensure the ID in the URL matches the ID in the destination object
        if (destination.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST).entity("URL ID and entity ID do not match").build();
        }
        destinationService.updateDestination(destination);
        return Response.ok().build();
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestinationByName(@PathParam("name") String name) {
        Destination destination = destinationService.getDestinationByName(name);
        if (destination == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(destination).build();
    }


}

package com.example.turistickivodic.resources;

import com.example.turistickivodic.entities.User;
import com.example.turistickivodic.requests.LogInRequest;
import com.example.turistickivodic.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;


//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getUsers() {
//        List<User> users = userService.getAllUsers();
//        return Response.ok(users).build();
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size) {
        List<User> users = userService.getUsersWithPagination(page, size);
        int totalUsers = userService.getTotalUserCount();

        System.out.println("Velicina vracenih korisnika je: " + users.size());
        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("total", totalUsers);

        return Response.ok(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        userService.addUser(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserStatus(Map<String, Object> params) {
        String email = (String) params.get("email");
        boolean status = (boolean) params.get("status");
        userService.updateUserStatus(email, status);
        return Response.ok().build();
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @PUT
    @Path("/{email}")  // Include path parameter to identify the user
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("email") String email, User user) {
        if (!user.getEmail().equals(email)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("URL email and entity email do not match").build();
        }
        userService.updateUser(user);
        return Response.ok().build();
    }


    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LogInRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();
        String jwt = this.userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

}

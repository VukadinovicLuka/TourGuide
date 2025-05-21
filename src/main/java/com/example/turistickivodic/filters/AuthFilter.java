package com.example.turistickivodic.filters;

import com.example.turistickivodic.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    private static final Map<String, Set<String>> UNPROTECTED_PATHS = new HashMap<>();

    static {
        Set<String> getUnprotectedPaths = new HashSet<>();
        getUnprotectedPaths.add("articles");
        getUnprotectedPaths.add("activity");
        getUnprotectedPaths.add("destinations");
        getUnprotectedPaths.add("comments");
        getUnprotectedPaths.add("article_activities");
        getUnprotectedPaths.add("users/email");

        Set<String> postUnprotectedPaths = new HashSet<>();
        postUnprotectedPaths.add("users/login");

        UNPROTECTED_PATHS.put("GET", getUnprotectedPaths);
        UNPROTECTED_PATHS.put("POST", postUnprotectedPaths);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();

//        System.out.println("Path: " + path);
//        System.out.println("Method: " + method);

        if (!isAuthRequired(path, method)) {
            return;
        }

        String token = requestContext.getHeaderString("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            try {
                if (!userService.isAuthorized(token)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                } else {
                    String role = userService.getRoleFromToken(token);
                    if (!isRoleAllowed(role, path)) {
                        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                    }
                }
            } catch (Exception e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(String path, String method) {
        Set<String> unprotectedPaths = UNPROTECTED_PATHS.get(method);
        if (unprotectedPaths != null) {
            for (String unprotectedPath : unprotectedPaths) {
                if (path.startsWith(unprotectedPath) || path.endsWith("visit")) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRoleAllowed(String role, String path) {
        // Allow only admins to access user management pages
        if (path.contains("users") && !role.equals("admin")) {
            return false;
        }
        return true;
    }
}

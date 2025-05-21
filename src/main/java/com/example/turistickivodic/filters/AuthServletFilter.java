package com.example.turistickivodic.filters;

import com.example.turistickivodic.services.UserService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthServletFilter implements Filter {

    @Inject
    UserService userService;

    private static final List<String> UNPROTECTED_PATHS = Arrays.asList(
            "/index.html",
            "/login.html",
            "/najcitaniji.html",
            "/view-article-comment.html",
            "/articles-destination.html",
            "/activity-article.html"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        System.out.println("Servlet filter path: " + path);

        if (isAuthRequired(path)) {
            String token = httpRequest.getHeader("Authorization");
            System.out.println("Authorization Header: " + token);
            if (token == null || !token.startsWith("Bearer ")) {
                System.out.println("Missing or invalid Authorization header");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else {
                token = token.substring(7);
                if (!isValidToken(token)) {
                    System.out.println("Invalid token");
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                } else {
                    String role = userService.getRoleFromToken(token);
                    System.out.println("Role: " + role);
                    if (!isRoleAllowed(role, path)) {
                        System.out.println("Role not allowed for this path");
                        httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isAuthRequired(String path) {
        for (String unprotectedPath : UNPROTECTED_PATHS) {
            if (path.startsWith(unprotectedPath)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRoleAllowed(String role, String path) {
        // Example: allow only admins to access certain pages
        if (path.contains("newDestination.html") && !"admin".equals(role)) {
            return false;
        }
        return true;
    }

    private boolean isValidToken(String token) {
        boolean isValid = userService.isAuthorized(token);
        System.out.println("Token valid: " + isValid);
        return isValid;
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}

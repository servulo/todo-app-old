package com.todoapp.resource;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        String allowedOrigin = System.getenv("CORS_ALLOWED_ORIGIN");
        if (allowedOrigin == null) {
            allowedOrigin = "http://localhost:4200";
        }

        String origin = requestContext.getHeaderString("Origin");
        if (origin != null && origin.equals(allowedOrigin)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            responseContext.getHeaders().add("Access-Control-Allow-Headers", "Accept, Authorization, Content-Type");
            responseContext.getHeaders().add("Access-Control-Expose-Headers", "Authorization");
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        }
    }
}
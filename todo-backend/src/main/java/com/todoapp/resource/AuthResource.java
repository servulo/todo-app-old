package com.todoapp.resource;

import java.util.Map;

import com.todoapp.dto.AuthDTO;
import com.todoapp.service.AuthService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    public Response register(@Valid AuthDTO dto) {
        try {
            authService.register(dto.username, dto.password);
            return Response.status(Response.Status.CREATED)
            .entity(Map.of("message", "Usuário criado com sucesso"))
            .build();
        } catch(IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT)
            .entity(Map.of("error", e.getMessage()))
            .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@Valid AuthDTO dto) {
        try {
            String token = authService.login(dto.username, dto.password);
            return Response.ok(Map.of("token", token))
            .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
            .entity(Map.of("error ", e.getMessage()))
            .build();
        }
    }

}
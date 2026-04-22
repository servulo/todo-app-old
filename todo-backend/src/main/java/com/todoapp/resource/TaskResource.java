package com.todoapp.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.todoapp.dto.TaskDTO;
import com.todoapp.entity.Task;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("user")
public class TaskResource {

    @Inject
    JsonWebToken jwt;

    private Long currentUserId() {
        return Long.parseLong(jwt.getSubject());
    }

    @GET
    public List<Task> listAll() {
        return Task.find("ownerId", currentUserId()).list();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Task task = Task.findById(id);
        if (task == null || !task.ownerId.equals(currentUserId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(task).build();
    }

    @POST
    @Transactional
    public Response create(@Valid TaskDTO dto) {
        Task task = new Task();
        task.title = dto.title;
        task.description = dto.description;
        task.ownerId = currentUserId();
        task.persist();
        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid TaskDTO dto) {
        Task task = Task.findById(id);
        if (task == null || !task.ownerId.equals(currentUserId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        task.title = dto.title;
        task.description = dto.description;
        task.completed = dto.completed;
        return Response.ok(task).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Task task = Task.findById(id);
        if (task == null || !task.ownerId.equals(currentUserId())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        task.delete();
        return Response.noContent().build();
    }
}
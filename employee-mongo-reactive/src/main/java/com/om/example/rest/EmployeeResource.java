package com.om.example.rest;

import com.om.example.entity.EmployeeEntity;
import com.om.example.service.EmployeeService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@Path("/api/v1/employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @Inject
    EmployeeService service;

    @GET
    @Path("/{employeeId}")
    public CompletionStage<Response> getEmployee(@PathParam final Integer employeeId) {
        return service.getEmployee(employeeId).thenApplyAsync(res -> {
            if (res.isPresent()) {
                return Response.ok(res.get()).build();
            }
            return Response.status(404).build();

        });
    }

    @GET
    public CompletionStage<Response> getEmployees() {
        return service.getEmployees().thenApplyAsync(res -> Response.ok(res).build());
    }

    @POST
    @Transactional
    public CompletionStage<Response> addEmployee(final EmployeeEntity entity) {
        return service.addEmployee(entity).thenApplyAsync((res) -> Response.status(201).build());
    }

    @PUT
    @Path("/{employeeId}")
    @Transactional
    public CompletionStage<Response> updateEmployee(@PathParam final Integer employeeId, final EmployeeEntity entity) {
        return service.updateEmployee(employeeId, entity).thenApplyAsync(result -> {
            if (result == null) {
                return Response.status(404).build();
            }
            return Response.ok(result).build();
        });
    }

    @DELETE
    @Path("/{employeeId}")
    @Transactional
    public CompletionStage<Response> deleteEmployee(@PathParam final Integer employeeId) {
        return service.deleteEmployee(employeeId).thenApplyAsync(result -> {
            if (result == null) {
                return Response.status(404).build();
            }
            return Response.ok(result).build();
        });
    }
}
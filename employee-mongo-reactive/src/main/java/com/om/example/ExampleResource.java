package com.om.example;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@Path("/hello")
public class ExampleResource {
    @Inject
    EmployeeService service;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> hello(@PathParam final String name) {
        return service.greeting(name);
    }
}
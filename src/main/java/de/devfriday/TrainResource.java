package de.devfriday;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.cli.annotations.Description;

@Path("/train")
public class TrainResource {

  @Inject
  TrainRepository repo;

  @GET
  @Description("Get all Trains we know.")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Multi<Train> getAllTrains() {
    return repo.findAll();
  }

  @GET
  @Path("/{id}")
  @Description("Find one Train by its id.")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  // Use response because we want to set status code when no item is found
  public Uni<Response> getTrainById(@PathParam Long id) {
    // get item by id
    // if item is a train, return 200 and the item
    // else set response status code to NOT_FOUND
    // don't forget to actually build the response
    return repo.findById(id).onItem()
        .apply(train -> train != null ? Response.ok(train) : Response.status(Status.NOT_FOUND)).onItem()
        .apply(ResponseBuilder::build);
  }
}
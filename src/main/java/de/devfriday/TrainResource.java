package de.devfriday;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Multi;

@Path("/train")
public class TrainResource {

  @Inject
  TrainRepositoryImpl trainRepos;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Multi<Train> getAllTrains() {
    return trainRepos.getAllTrains();
  }
}
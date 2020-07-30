package de.devfriday;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import io.vertx.core.cli.annotations.Description;

@GraphQLApi
public class TrainGraphqlResource {

  @Inject
  TrainRepositoryImpl trainRepo;

  @Query("allTrains")
  @Description("Get all Trains we know.")
  public List<Train> getAllTrains() {
    return trainRepo.getAllTrains().collectItems().asList().await().indefinitely();
  }
}
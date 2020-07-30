package de.devfriday;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import io.vertx.core.cli.annotations.Description;

@GraphQLApi
public class TrainGraphqlResource {

  @Inject
  TrainRepository repo;

  @Query("allTrains")
  @Description("Get all Trains we know.")
  public List<Train> getAllTrains() {
    return repo.findAll().collectItems().asList().await().indefinitely();
  }

  @Query("getTrain")
  @Description("Get a specific train.")
  public Train getTrain(int trainId) {
    return repo.findById(Long.valueOf(trainId)).await().indefinitely();
  }
}
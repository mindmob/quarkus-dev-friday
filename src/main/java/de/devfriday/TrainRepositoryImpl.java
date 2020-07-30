package de.devfriday;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

@ApplicationScoped
public class TrainRepositoryImpl implements TrainRepository {

  @Inject
  PgPool pgClient;

  @Override
  public Multi<Train> getAllTrains() {
    Uni<RowSet<Row>> rowSet = pgClient.query("SELECT id, name FROM trains").execute();
    return rowSet.onItem().produceMulti(set -> Multi.createFrom().iterable(set)).onItem().apply(Train::from);
  }
}
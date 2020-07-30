package de.devfriday;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

@ApplicationScoped
public class TrainRepository {

  @Inject
  PgPool pgclient;

  public Multi<Train> findAll() {
    Uni<RowSet<Row>> rowSet = pgclient.query("SELECT id, name FROM trains").execute();
    return rowSet.onItem().produceMulti(set -> Multi.createFrom().iterable(set)).onItem().apply(Train::from);
  }

  public Uni<Train> findById(Long id) {
    // create a prepared query
    // execute the query with the parmeters of given Tuple
    // get an iterator for the resulting RowSet
    // iterate over the RowSet (as long as there is something to iterate)
    // for each iteration create a train
    return pgclient.preparedQuery("SELECT id, name FROM trains WHERE id = $1").execute(Tuple.of(id)).onItem()
        .apply(RowSet::iterator).onItem().apply(iterator -> iterator.hasNext() ? Train.from(iterator.next()) : null);
  }
}

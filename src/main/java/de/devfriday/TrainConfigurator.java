package de.devfriday;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.java.Log;

@Startup
@ApplicationScoped
@Log
public class TrainConfigurator {

  @Inject
  PgPool pgClient;

  @Inject
  @ConfigProperty(name = "de.devfriday.schemaDropAndCreate", defaultValue = "false")
  boolean schemaDropAndCreate;

  void onStart(@Observes StartupEvent ev) {
    if (schemaDropAndCreate) {
      initDB();
    }
  }

  private void initDB() {
    log.info("Trying to create Tables...");
    pgClient.query("DROP TABLE IF EXISTS trains").execute()
        .flatMap(r -> pgClient.query("CREATE TABLE trains (id SERIAL PRIMARY KEY, name TEXT NOT NULL)").execute())
        .flatMap(r -> pgClient.query("INSERT INTO trains (name) VALUES ('ICE')").execute())
        .flatMap(r -> pgClient.query("INSERT INTO trains (name) VALUES ('ICX')").execute())
        .flatMap(r -> pgClient.query("INSERT INTO trains (name) VALUES ('RE')").execute()).await().indefinitely();
  }
}
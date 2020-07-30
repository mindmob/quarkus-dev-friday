package de.devfriday;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import lombok.extern.java.Log;

@Log
public class PostgresResource implements QuarkusTestResourceLifecycleManager {

  private final PostgreSQLContainer<?> pgContainer = new PostgreSQLContainer<>("postgres:latest");

  @Override
  public Map<String, String> start() {
    pgContainer.start();
    Map<String, String> map = new HashMap<String, String>();
    map.put("quarkus.datasource.reactive.url", "postgresql://" + pgContainer.getContainerIpAddress() + ":"
        + pgContainer.getFirstMappedPort() + "/" + pgContainer.getDatabaseName());
    log.info("postgresql://" + pgContainer.getContainerIpAddress() + ":" + pgContainer.getFirstMappedPort() + "/"
        + pgContainer.getDatabaseName());
    map.put("quarkus.datasource.username", pgContainer.getUsername());
    map.put("quarkus.datasource.password", pgContainer.getPassword());
    return map;
  }

  @Override
  public void stop() {
    pgContainer.stop();

  }

}
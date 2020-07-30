package de.devfriday;

import io.smallrye.mutiny.Multi;

public interface TrainRepository {
  public Multi<Train> getAllTrains();
}
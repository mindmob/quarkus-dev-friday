package de.devfriday;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertx.mutiny.sqlclient.Row;

public class Train {

  private Long id;
  private String name;

  public Train(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isFernverkehr() {
    switch (name) {
      case "ICE":
        return true;
      case "ICX":
        return true;
      default:
        return false;
    }
  }

  public String toString() {
    return "_id:" + id + "; Name:" + name;
  }

  public static Train from(Row row) {
    return new Train(row.getLong("id"), row.getString("name"));
  }
}
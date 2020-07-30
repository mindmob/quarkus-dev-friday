package de.devfriday;

import javax.json.bind.annotation.JsonbTransient;

import io.vertx.mutiny.sqlclient.Row;
import lombok.Data;

@Data
public class Train {

  private Long id;
  private String name;

  public Train(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Train from(Row row) {
    return new Train(row.getLong("id"), row.getString("name"));
  }

  @JsonbTransient
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

}
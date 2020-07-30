package de.devfriday;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
public class TestTrainResource {

  @Test
  public void testAllTrains() {
    String s = "[{\"id\":1,\"name\":\"ICE\"},{\"id\":2,\"name\":\"ICX\"},{\"id\":3,\"name\":\"RE\"}]";
    String response = given().when().get("/train").then().statusCode(200).extract().response().asString();
    assertEquals(response, s);
  }

  @Test
  public void testAllrainsExpcted() {

    // Given
    Set<Train> expectedTrains = new HashSet<>();
    expectedTrains.add(new Train(1L, "ICE"));
    expectedTrains.add(new Train(2L, "ICX"));
    expectedTrains.add(new Train(3L, "RE"));
    // When
    Set<Train> trains = new HashSet<>();
    trains.addAll(Arrays.asList(given().when().get("/train").jsonPath().getObject("$", Train[].class)));

    // Then

    assertTrue(trains.containsAll(expectedTrains));

  }

}
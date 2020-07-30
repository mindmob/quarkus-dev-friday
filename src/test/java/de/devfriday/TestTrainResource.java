package de.devfriday;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TestTrainResource {

  @Test
  public void testAllTrains() {
    String s = "[{'fernverkehr':true,'id':1,'name':'ICE'},{'fernverkehr':true'id':2,'name':'ICE'},{'fernverkehr':false'id':3,'name':'RE'}]";
    given().when().get("/train").then().statusCode(200).body(is(s));
  }

  // @Test
  // public void testAllrainsExpcted() {

  // Set<Train> trains = new HashSet<>();
  // trains.add(new Train(1L, "ICE"));
  // trains.add(new Train(2L, "ICE"));
  // given().when().get("/train").then().statusCode(200);

  // }

}
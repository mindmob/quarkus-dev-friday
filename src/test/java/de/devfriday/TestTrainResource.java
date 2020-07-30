package de.devfriday;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TestTrainResource {

  @Test
  public void testAllTrains() {
    String s = "[{'fernverkehr':true,'id':1,'name':'ICE'},{'fernverkehr':true'id':2,'name':'ICE'},{'fernverkehr':false'id':3,'name':'RE'}]";
    given().when().get("/train").then().statusCode(200).body(is(s));
  }

  @Test
  public void testAllrainsExpcted() {

    //Given
    Set<Train> expextedTrains = new HashSet<>();
    expextedTrains.add(new Train(1L, "ICE"));
    expextedTrains.add(new Train(2L, "ICX"));
    
    //When
    Set<Train> trains= new HashSet<>();
    trains.addAll(Arrays.asList(given().when().get("/train").jsonPath().getObject("$", Train[].class)));



    //Then

    assertTrue(trains.containsAll(expextedTrains));

  }

}
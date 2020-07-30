package de.devfriday;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItem;
import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
public class TestTrainResourceGraphql {

  @Test
  public void testAllTrainsGraphql() {
    String requestBody = "{\"query\":" + "\"" + "{" + " allTrains  {" + " id" + " name" + "}" + "}" + "\"" + "}";

    given().body(requestBody).post("/graphql/").then().contentType(ContentType.JSON)
        .body("data.allTrains.size()", is(3)).body("data.allTrains.name", hasItem("ICX")).statusCode(200);
  }
}
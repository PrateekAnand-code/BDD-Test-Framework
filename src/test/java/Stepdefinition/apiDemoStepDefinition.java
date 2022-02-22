package Stepdefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import generatedPojos.APIdemoSchema;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;


public class apiDemoStepDefinition {

    private  Response Response;

    @When("^I create data with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_create_data_with_and(String name, String job) throws JsonProcessingException {

        APIdemoSchema Data =new APIdemoSchema();
        Data.setJob(job);
        Data.setName(name);

        ObjectMapper objectMapper =new ObjectMapper();
        String RequestBody =objectMapper.writeValueAsString(Data);

        RestAssured.baseURI="https://reqres.in/api";
        Response =
                RestAssured.given().
                        contentType(ContentType.JSON).body(RequestBody).
                                        post("/users");
    }

    @Then("^I should get response \"([^\"]*)\"$")
    public void i_should_get_response(int respCode) throws Throwable {
         System.out.println(Response.getStatusCode());
        assertEquals(respCode,Response.getStatusCode());
    }

    @Then("^I should see the response that data is created with \"([^\"]*)\"$")
    public void i_should_see_the_response_that_data_is_created_with(String name) throws Throwable {
      System.out.print(Response.getBody().asString());
      Response.then().body("name",equalTo(name));
    }
}
package framework.steps;

import framework.api.UsersClient;
import framework.support.TestContext;
import io.cucumber.java.en.*;
import java.io.IOException;
import static org.hamcrest.Matchers.*;

public final class ApiSteps {
    private final TestContext context;
    public ApiSteps(TestContext context) { this.context = context; }
    @When("I create a user named {string} with job {string}")
    public void create(String name, String job) throws IOException {
        context.response(new UsersClient(context.baseUrl("api")).create(name, job));
    }
    @Then("the response status is {int}") public void status(int expected) {
        context.response().then().statusCode(expected);
    }
    @Then("the response contains user {string} with job {string}") public void user(String name, String job) {
        context.response().then().contentType("application/json")
            .body("name", equalTo(name), "job", equalTo(job), "id", not(emptyOrNullString()));
    }
    @Then("the API error is {string}") public void error(String expected) {
        context.response().then().contentType("application/json").body("error", equalTo(expected));
    }
}

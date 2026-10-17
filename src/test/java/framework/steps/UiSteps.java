package framework.steps;

import framework.pages.LoginPage;
import framework.support.TestContext;
import io.cucumber.java.en.*;
import java.io.IOException;

public final class UiSteps {
    private final TestContext context;
    public UiSteps(TestContext context) { this.context = context; }
    @Given("I open the login page") public void openLogin() throws IOException {
        new LoginPage(context.page()).open(context.baseUrl("ui"));
    }
    @When("I sign in as {string} with password {string}") public void login(String username, String password) {
        new LoginPage(context.page()).login(username, password);
    }
    @Then("the login message is {string}") public void message(String expected) {
        new LoginPage(context.page()).assertMessage(expected);
    }
}

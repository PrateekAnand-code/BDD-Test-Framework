package Stepdefinition;

import DriverUtil.WebDriverUtil;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DemoStepDefinition {

    WebDriver driver = WebDriverUtil.Webdriver();

    public DemoStepDefinition() throws Exception {
    }

    @After
    public void teardown(){
        driver.quit();
    }


    @Given("^I navigate to \"([^\"]*)\"$")
    public void i_navigate_to(String url) throws Throwable {
        driver.get(url);
    }

    @When("^I login with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_login_with_and(String username, String password) throws Throwable {
     driver.findElement(By.id("username")).sendKeys(username);
     driver.findElement(By.id("password")).sendKeys(password);
     driver.findElement( By.xpath("//*[@id=\"login\"]/button")).click();
    }

    @Then("^I should see home page$")
    public void i_should_see_home_page() throws Throwable {
       System.out.print(driver.getTitle());
    }
}

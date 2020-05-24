package Stepdefinition;

import DriverUtil.WebDriverUtil;
import Utilities.YAMLReader;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashMap;

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

    @When("^I login with \"([^\"]*)\"$")
    public void i_login_credentials(String Credentials) throws Throwable {
        YAMLReader yamlReader =new YAMLReader("src\\test\\resources\\Data\\Demo.yaml");
        LinkedHashMap DataMap= yamlReader.GetDataSet(Credentials);
     driver.findElement(By.id("username")).sendKeys(DataMap.get("Username").toString());
     driver.findElement(By.id("password")).sendKeys(DataMap.get("Username").toString());
     driver.findElement( By.xpath("//*[@id=\"login\"]/button")).click();
    }

    @Then("^I should see home page$")
    public void i_should_see_home_page() throws Throwable {
       System.out.print(driver.getTitle());
    }
}

package Stepdefinition;

import DriverUtil.webDriverUtil;
import Utilities.pageObject;
import Utilities.yamlReader;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashMap;

public class uiDemoStepDefinition {

    WebDriver driver = webDriverUtil.Webdriver();

    public uiDemoStepDefinition() throws Exception {
    }

    @Given("^I navigate to \"([^\"]*)\"$")
    public void i_navigate_to(String url) throws Throwable {
        driver.get(url);
    }

    @When("^I login with \"([^\"]*)\"$")
    public void i_login_credentials(String Credentials) throws Throwable {
        yamlReader yamlReader =new yamlReader("src\\test\\resources\\Data\\Demo.yaml");
        LinkedHashMap DataMap= yamlReader.GetDataSet(Credentials);

        pageObject pageObject =new pageObject(driver,"src\\test\\resources\\PageObjects\\DemoPageObject.yaml");
        pageObject.GetElement("Username_textbox").sendKeys(DataMap.get("Username").toString());
        pageObject.GetElement("password_textbox").sendKeys(DataMap.get("Password").toString());
        pageObject.GetElement("login_button").click();
    }

    @Then("^I should see home page$")
    public void i_should_see_home_page()  {
       System.out.print(driver.getTitle());
    }

    @After("@UIDemo")
    public void teardown(){
        driver.quit();
    }
}

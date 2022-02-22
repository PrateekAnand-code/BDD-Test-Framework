package Stepdefinition;

import DriverUtil.WebDriverUtil;
import Utilities.YAMLReader;
import Utilities.pageObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashMap;

public class uiDemoStepDefinition {

    public uiDemoStepDefinition() throws Exception {
    }

     WebDriver driver= WebDriverUtil.Webdriver();

    @Given("^I navigate to \"([^\"]*)\"$")
    public void i_navigate_to(String url) throws Throwable {
        driver.get(url);
    }

    @When("^I login with \"([^\"]*)\"$")
    public void i_login_credentials(String Credentials) throws Throwable {
        YAMLReader yamlReader =new YAMLReader("src\\test\\resources\\Data\\Demo.yaml");
        LinkedHashMap DataMap= yamlReader.GetDataSet(Credentials);
        pageObject.GetElement("DemoPageObject.Username_textbox").sendKeys(DataMap.get("Username").toString());
        pageObject.GetElement("DemoPageObject.password_textbox").sendKeys(DataMap.get("Password").toString());
        pageObject.GetElement("DemoPageObject2.login_button").click();
    }

    @Then("^I should see home page$")
    public void i_should_see_home_page()  {
       System.out.print(driver.getTitle());
    }
}

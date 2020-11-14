package Runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@CucumberOptions(
        tags = {"@UIDemo"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        features="src\\test\\resources\\Features",
        glue= "Stepdefinition"
)



@RunWith(Cucumber.class)
public class demoRunnerTest {


}


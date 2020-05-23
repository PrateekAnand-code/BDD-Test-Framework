package Runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@CucumberOptions(
        tags = {"@Demo"},
        plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        features="src\\test\\resources\\Features",
        glue="Stepdefinition"
)

@RunWith(Cucumber.class)
public class DemoRunnerTest {
}

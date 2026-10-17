package framework;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("features")
@ConfigurationParameter(key = "cucumber.glue", value = "framework.steps,framework.support")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty,html:target/reports/cucumber.html,json:target/reports/cucumber.json,junit:target/reports/cucumber.xml")
@ConfigurationParameter(key = "cucumber.publish.quiet", value = "true")
public class RunCucumberTest {}

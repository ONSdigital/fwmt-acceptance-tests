package uk.gov.ons.fwmt.acceptancetests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
    features = {"src/test/resources/acceptancetests/jobService.feature"},
    glue = {"uk.gov.ons.fwmt.acceptancetests"})
public class TestRunner {
}

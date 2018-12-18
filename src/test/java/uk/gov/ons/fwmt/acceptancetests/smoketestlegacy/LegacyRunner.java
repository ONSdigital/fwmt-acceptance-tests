package uk.gov.ons.fwmt.acceptancetests.smoketestlegacy;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
    features = {"src/test/resources/smoketest/legacy.feature"},
    glue = {"uk.gov.ons.fwmt.acceptancetests.smoketestlegacy"})
public class LegacyRunner {
}

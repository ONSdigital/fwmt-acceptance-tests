package uk.gov.ons.fwmt.acceptancetests.smoketest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        features = {"src/test/resources/smoketest/gateway.feature"},
        glue = {"uk.gov.ons.fwmt.acceptancetests.smoketest"})
public class GatewayRunner {
}

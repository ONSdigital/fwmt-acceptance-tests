package uk.gov.ons.fwmt.acceptancetests.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty","json:build/census-report.json"},
    features = {"src/test/resources/acceptancetests/CensusTests.feature"},
    glue = {"uk.gov.ons.fwmt.acceptancetests.steps.census"})
public class CensusTestRunner {

}

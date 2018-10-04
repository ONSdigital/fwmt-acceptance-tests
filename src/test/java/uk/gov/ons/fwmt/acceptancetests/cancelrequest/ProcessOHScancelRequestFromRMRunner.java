package uk.gov.ons.fwmt.acceptancetests.cancelrequest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty","json:build/cucumber-report.json"},
        features = {"src/test/resources/acceptancetests/cancelrequest/process_ohs_cancel_request_from_rm.feature"},
        glue = {"uk.gov.ons.fwmt.acceptancetests.cancelrequest"})
public class ProcessOHScancelRequestFromRMRunner {
}

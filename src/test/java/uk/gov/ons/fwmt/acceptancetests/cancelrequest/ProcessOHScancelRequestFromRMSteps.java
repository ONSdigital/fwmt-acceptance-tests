package uk.gov.ons.fwmt.acceptancetests.cancelrequest;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.gov.ons.fwmt.acceptancetests.SpringBootBaseAcceptanceTest;

public class ProcessOHScancelRequestFromRMSteps extends SpringBootBaseAcceptanceTest {


    @Given("^the respondent has completed online$")
    public void theRespondentHasCompletedOnline() {
        // Write code here that turns the phrase above into concrete actions
        testPathFromRMToJobSvc();
    }

    @And("^the outcome is 'fully completed'$")
    public void theOutcomeIsFullyCompleted() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^RM will notify gateway to all 'Stop visit'$")
    public void rmWillNotifyGatewayToAllStopVisit() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Gateway will instruct TM to 'stop all visits' related to the case$")
    public void gatewayWillInstructTMToStopAllVisitsRelatedToTheCase() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

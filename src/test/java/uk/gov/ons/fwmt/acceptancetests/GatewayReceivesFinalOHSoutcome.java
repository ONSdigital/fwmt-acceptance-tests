package uk.gov.ons.fwmt.acceptancetests;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GatewayReceivesFinalOHSoutcome {
  @Given("^the case outcome and the caseid has been passed from TM to the Gateway$")
  public void theCaseOutcomeAndTheCaseidHasBeenPassedFromTMToTheGateway() {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @When("^The Gateway receives the case outcome$")
  public void gateway_transforms_the_data() {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @And("^convert the outcome from TM message format to RM format$")
  public void convertTheOutcomeFromTMMessageFormatToRMFormat() {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Then("^the Gateway will immediately send the outcome to RM with the case id$")
  public void theGatewayWillImmediatelySendTheOutcomeToRMWithTheCaseId() {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }
}

package uk.gov.ons.fwmt.acceptancetests.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static uk.gov.ons.fwmt.acceptancetests.utils.Constatnts.LMS_RESPONSE;

public class CommonSteps {

  @Given("^TM sends a \"([^\"]*)\" case outcome to the Job Service$")
  public void tm_sends_a_LMS_case_outcome_to_the_Job_Service(String caseType) throws Exception {
    String data = "";
    switch (caseType) {
    case "LMS":
      data = LMS_RESPONSE;
      break;
    default:
      throw new IllegalArgumentException("Must be LMS, Census.");
    }
  }

  @Given("^the response contains the outcome and caseId$")
  public void the_response_contains_the_outcome_and_caseId() throws Exception {

  }

  @Then("^the message is in the RM composite format$")
  public void the_message_is_in_the_RM_composite_format() throws Exception {

  }

  @Then("^the message will be put on the queue to RM$")
  public void the_message_will_be_put_on_the_queue_to_RM() throws Exception {
    //read from RM_QUEUE
  }

}

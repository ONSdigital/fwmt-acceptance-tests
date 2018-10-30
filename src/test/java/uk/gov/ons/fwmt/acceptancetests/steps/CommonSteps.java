package uk.gov.ons.fwmt.acceptancetests.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import uk.gov.ons.fwmt.acceptancetests.utils.MessageSender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CommonSteps {

  MessageSender ms;

  public static final String LMS_RESPONSE= "";
  private static final String EXPECTED_XML = "";
  public CommonSteps() throws IOException, TimeoutException {
    ms =  new MessageSender();
  }

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

    int response = ms.sendResponseMessage(LMS_RESPONSE);
    assertEquals(200, response);
  }

  @Given("^the response contains the outcome and caseId$")
  public void the_response_contains_the_outcome_and_caseId() throws Exception {
    assertTrue(LMS_RESPONSE.contains("&lt;name&gt;caseId&lt;/name&gt;&lt;value&gt;df670a12-1c5e-4530-b863-3ffc64d9159f&lt;/value&gt;"));
    assertTrue(LMS_RESPONSE.contains("&lt;outcomeCategory&gt;Will complete&lt;/outcomeCategory&gt;"));
    assertTrue(LMS_RESPONSE.contains("&lt;outcomeReason&gt;Will complete&lt;/outcomeReason&gt;"));
  }

  @Then("^the message is in the RM composite format$")
  public void the_message_is_in_the_RM_composite_format() throws Exception {
    assertTrue(ms.getMessage().equals(EXPECTED_XML));

  }

  @Then("^the message will be put on the queue to RM$")
  public void the_message_will_be_put_on_the_queue_to_RM() throws Exception {
    assertEquals(ms.getMessageCount(), 1);
  }

}

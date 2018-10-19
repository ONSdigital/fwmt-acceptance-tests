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

  public static final String LMS_RESPONSE = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body><request xmlns=\"http://schemas.consiliumtechnologies.com/services/mobile/2009/03/messaging\"><Id>IDENTITY</Id><Content>&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;element xmlns:ns1=\"http://ons.gov.uk/fwmt/NonContactDetail\" xmlns:ns2=\"http://ons.gov.uk/fwmt/PropertyDetails\" xmlns:ns3=\"http://ons.gov.uk/fwmt/AdditionalProperties\" xmlns:ns7=\"http://schemas.consiliumtechnologies.com/mobile/2009/07/FormsTypes.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"\"&gt;&lt;eventDate&gt;2018-10-12T16:41:42.22&lt;/eventDate&gt;&lt;jobIdentity&gt;IDENTITY&lt;/jobIdentity&gt;&lt;nonContactDetail/&gt;&lt;propertyDetails&gt;&lt;description&gt;No physical impediments or barriers&lt;/description&gt;&lt;/propertyDetails&gt;&lt;username&gt;USER&lt;/username&gt;&lt;additionalProperties&gt;&lt;name&gt;CaseId&lt;/name&gt;&lt;/additionalProperties&gt;&lt;/element&gt;]]&gt;</Content><Format>fwmtOHSJobStatusNotification</Format></request></soap:Body></soap:Envelope>";
  private static final String EXPECTED_XML ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:FwmtOHSJobStatusNotification xmlns:ns2=\"http://ons.gov.uk/fwmt/FwmtOHSJobStatusNotification\"><eventDate>2018-10-12T15:41:42.220Z</eventDate><jobIdentity>IDENTITY</jobIdentity><nonContactDetail/><propertyDetails><description>No physical impediments or barriers</description></propertyDetails><username>USER</username><additionalProperties><name>CaseId</name></additionalProperties></ns2:FwmtOHSJobStatusNotification>";

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
    assertTrue(LMS_RESPONSE.contains("CaseId&lt;/name"));
    //assertTrue(LMS_RESPONSE.contains("OUTCOME"));
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

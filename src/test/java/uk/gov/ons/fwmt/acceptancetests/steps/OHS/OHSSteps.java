package uk.gov.ons.fwmt.acceptancetests.steps.OHS;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import uk.gov.ons.fwmt.acceptancetests.utils.MessageSender;
import uk.gov.ons.fwmt.acceptancetests.utils.MockMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static uk.gov.ons.fwmt.acceptancetests.utils.Config.MOCK_URL;

@Slf4j
public class OHSSteps {

  MessageSender ms;

  public static final String LMS_RESPONSE = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body><request xmlns=\"http://schemas.consiliumtechnologies.com/services/mobile/2009/03/messaging\"><Id>IDENTITY</Id><Content>&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?&gt;&lt;ns2:FwmtOHSJobStatusNotification xmlns:ns2=\"http://ons.gov.uk/fwmt/FwmtOHSJobStatusNotification\"&gt;    &lt;eventDate&gt;2018-10-29T08:03:40.269Z&lt;/eventDate&gt;    &lt;jobIdentity&gt;1000000000431094&lt;/jobIdentity&gt;    &lt;nonContactDetail/&gt;    &lt;outcomeCategory&gt;Will complete&lt;/outcomeCategory&gt;    &lt;outcomeReason&gt;Will complete&lt;/outcomeReason&gt;    &lt;propertyDetails&gt;        &lt;description&gt;No physical impediments or barriers&lt;/description&gt;    &lt;/propertyDetails&gt;    &lt;username&gt;USERNAME&lt;/username&gt;    &lt;additionalProperties&gt;        &lt;name&gt;caseId&lt;/name&gt;        &lt;value&gt;CASEID&lt;/value&gt;    &lt;/additionalProperties&gt;    &lt;additionalProperties&gt;        &lt;name&gt;TLA&lt;/name&gt;        &lt;value&gt;OHS&lt;/value&gt;    &lt;/additionalProperties&gt;    &lt;additionalProperties&gt;        &lt;name&gt;wave&lt;/name&gt;        &lt;value&gt;1&lt;/value&gt;    &lt;/additionalProperties&gt;&lt;/ns2:FwmtOHSJobStatusNotification&gt;]]&gt;</Content><Format>fwmtOHSJobStatusNotification</Format></request></soap:Body></soap:Envelope>";
  private static final String EXPECTED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:FwmtOHSJobStatusNotification xmlns:ns2=\"http://ons.gov.uk/fwmt/FwmtOHSJobStatusNotification\"><eventDate>2018-10-29T08:03:40.269Z</eventDate><jobIdentity>1000000000431094</jobIdentity><nonContactDetail/><outcomeCategory>Will complete</outcomeCategory><outcomeReason>Will complete</outcomeReason><propertyDetails><description>No physical impediments or barriers</description></propertyDetails><username>USERNAME</username><additionalProperties><name>caseId</name><value>CASEID</value></additionalProperties><additionalProperties><name>TLA</name><value>OHS</value></additionalProperties><additionalProperties><name>wave</name><value>1</value></additionalProperties></ns2:FwmtOHSJobStatusNotification>";

  private String username = "guest";
  private String password = "guest";
  private String hostname = "localhost";
  private int rmPort = 6672;
  private String virtualHost = "/";

  @Before
  public void resetMock() throws IOException, TimeoutException {
    URL url = new URL(MOCK_URL +"logger/reset");
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    httpURLConnection.setRequestMethod("GET");
    //checkConnection(httpURLConnection);
    ms = new MessageSender();
  }

  private void checkConnection(HttpURLConnection httpURLConnection) throws IOException {
    if (httpURLConnection.getResponseCode() != 200) {
      throw new RuntimeException("Failed : HTTP error code : "
          + httpURLConnection.getResponseCode());
    }
  }

  @Given("^RM sends OHS (\\d+) \"([^\"]*)\" case samples to the Gateway$")
  public void rm_sends_LMS_case_samples_to_the_Gateway(int noOfJobs, String typeOfMessage)
      throws IOException, TimeoutException, InterruptedException {
    ConnectionFactory factory = getConnectionFactory();

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    String message = setMessage(typeOfMessage);

    sendToQueue(noOfJobs, message, channel);

    channel.close();
    connection.close();
  }

  private String readFile(String filename) throws IOException {
    File file = new File(filename);

    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    StringBuilder stringBuilder = new StringBuilder();
    String line;

    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line).append("\n");
    }
    return stringBuilder.toString();
  }

  private ConnectionFactory getConnectionFactory() {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(hostname);
    factory.setUsername(username);
    factory.setPassword(password);
    factory.setPort(rmPort);
    factory.setVirtualHost(virtualHost);
    return factory;
  }

  private String setMessage(String typeOfMessage) throws IOException {
    String message;
    switch (typeOfMessage) {
    case "create":
      message = readFile("src/test/resources/files/xmlcreate.xml");
      return message;
    case "cancel":
      message = readFile("src/test/resources/files/xmlcancel.xml");
      return message;
    default:
      log.error("Unable to map message type");
      throw new IllegalArgumentException("Unable to map message type");
    }
  }

  private void sendToQueue(int noOfJobs, String message, Channel channel) throws IOException, InterruptedException {
    for (int i = 0; i < noOfJobs; i++) {
      String EXCHANGE_NAME = "action-outbound-exchange";
      String ROUTING_KEY = "Action.Field.binding";
      channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
    }
  }

  @Then("^loaded in TM (\\d+)$")
  public void loaded_in_TM(int noOfJobs) throws IOException {
    URL url = new URL(MOCK_URL +"/logger/allMessages");
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    httpURLConnection.setRequestMethod("GET");
    httpURLConnection.setRequestProperty("Accept", "application/json");

    checkConnection(httpURLConnection);

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
        (httpURLConnection.getInputStream())));
    StringBuilder stringBuilder = new StringBuilder();
    String line;

    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line).append("\n");
    }

    String result = stringBuilder.toString();

    ObjectMapper mapper = new ObjectMapper();
    List<MockMessage> message = mapper.readValue(result, new TypeReference<List<MockMessage>>() {
    });

    assertEquals(noOfJobs, message.size());
  }

  @Given("^TM sends a LMS case outcome to the Job Service$")
  public void tm_sends_a_LMS_case_outcome_to_the_Job_Service() throws Exception {
    int response = ms.sendResponseMessage(LMS_RESPONSE);
    assertEquals(200, response);
  }

  @Given("^the response contains the outcome and caseId$")
  public void the_response_contains_the_outcome_and_caseId() {
    assertTrue(LMS_RESPONSE.contains("&lt;name&gt;caseId&lt;/name&gt;        &lt;value&gt;CASEID&lt;/value&gt;"));
    assertTrue(LMS_RESPONSE.contains("&lt;outcomeCategory&gt;Will complete&lt;/outcomeCategory&gt;"));
    assertTrue(LMS_RESPONSE.contains("&lt;outcomeReason&gt;Will complete&lt;/outcomeReason&gt;"));
  }

  @Then("^the response is an LMS job$")
  public void the_response_is_a_Census_job() {
    assertTrue(LMS_RESPONSE.contains("&lt;value&gt;OHS&lt;/value&gt;"));
  }

  @Then("^the message is in the RM composite format$")
  public void the_message_is_in_the_RM_composite_format() throws Exception {
    assertTrue(ms.getMessage().equals(EXPECTED_XML));
  }

  @Then("^the message will be put on the queue to RM$")
  public void the_message_will_be_put_on_the_queue_to_RM() {
    assertEquals(ms.getMessageCount(), 1);
  }
}

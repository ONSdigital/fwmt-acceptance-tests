package uk.gov.ons.fwmt.acceptancetests;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeoutException;

public class ProcessOHSrequestsFromRM {

  private final String EXCHANGE_NAME = "action-outbound-exchange";
  private final String ROUTING_KEY = "Action.Field.binding";
  private final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
      + "<ns2:actionInstruction\n"
      + "    xmlns:ns2=\"http://ons.gov.uk/ctp/response/action/message/instruction\">\n"
      + "    <actionRequest>\n"
      + "        <actionId>c97f4de4-2198-47fb-8c76-a940c803bbe0</actionId>\n"
      + "        <responseRequired>false</responseRequired>\n"
      + "        <actionPlan>77aa08b6-bf81-4de2-98b8-881e10356a14</actionPlan>\n"
      + "        <actionType>SOCIALICF</actionType>\n"
      + "        <address>\n"
      + "            <sampleUnitRef>LMS00001</sampleUnitRef>\n"
      + "            <locality></locality>\n"
      + "            <line1>11 HILL VIEW</line1>\n"
      + "            <line2></line2>\n"
      + "            <townName>STOCKTON-ON-TEES</townName>\n"
      + "            <postcode>AA1 1AA</postcode>\n"
      + "            <country>E</country>\n"
      + "        </address>\n"
      + "        <legalBasis>Statistics of Trade Act 1947</legalBasis>\n"
      + "        <caseGroupStatus>NOTSTARTED</caseGroupStatus>\n"
      + "        <caseId>95db01c8-f117-4c5b-9672-757ab661220e</caseId>\n"
      + "        <priority>highest</priority>\n"
      + "        <caseRef>1000000000000101</caseRef>\n"
      + "        <iac>37j59xnrjnkc</iac>\n"
      + "        <events>\n"
      + "            <event>CASE_CREATED : null : SYSTEM : Case created when Initial creation of case</event>\n"
      + "        </events>\n"
      + "        <exerciseRef>202103</exerciseRef>\n"
      + "        <userDescription>March 2021</userDescription>\n"
      + "        <surveyName>Monthly Wages and Salaries Survey</surveyName>\n"
      + "        <surveyRef>LMS</surveyRef>\n"
      + "    </actionRequest>\n"
      + "</ns2:actionInstruction>";


  private String username = "guest";
  private String password = "guest";
  private String hostname = "localhost";
  private int rmPort = 6672;
  private String virtualHost = "/";

  @Given("^RM sends LMS (\\d+) case samples to the Gateway$")
  public void rm_sends_LMS_case_samples_to_the_Gateway(int arg1)
      throws IOException, TimeoutException, InterruptedException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(hostname);
    factory.setUsername(username);
    factory.setPassword(password);
    factory.setPort(rmPort);
    factory.setVirtualHost(virtualHost);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    for (int i = 0; i < arg1; i++) {
      channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, XML.getBytes());
      Thread.sleep(2000);
    }

    channel.close();
    connection.close();
	}

	@When("^Gateway transforms the data$")
  public void gateway_transforms_the_data() {
		// Write code here that turns the phrase above into concrete actions

	}

	@Then("^an LMS job is created$")
  public void an_LMS_job_is_created() {
		// Write code here that turns the phrase above into concrete actions

  }

	@Then("^loaded in TM$")
  public void loaded_in_TM() throws IOException {
    URL url = new URL("http://localhost:9099/logger/allMessages");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Accept", "application/json");

    if (conn.getResponseCode() != 200) {
      throw new RuntimeException("Failed : HTTP error code : "
          + conn.getResponseCode());
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(
        (conn.getInputStream())));

    int count = 0;
    while (br.readLine() != null) {
      count++;
    }

    System.out.println("messages logged?" + count);
  }
}

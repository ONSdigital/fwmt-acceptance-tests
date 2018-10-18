package uk.gov.ons.fwmt.acceptancetests.steps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProcessOHScancelRequestFromRM {

  private final String EXCHANGE_NAME = "action-outbound-exchange";
  private final String ROUTING_KEY = "Action.Field.binding";
  private final String XML =
      "<ins:actionInstruction xmlns:ins=\"http://ons.gov.uk/ctp/response/action/message/instruction\">\n"
          + "\t<actionCancel>\n"
          + "\t\t<actionId>5a9f4323</actionId>\n"
          + "\t\t<responseRequired>true</responseRequired>\n"
          + "\t\t<reason>deleted for test</reason>\n"
          + "\t</actionCancel>\n"
          + "</ins:actionInstruction>";

  private String username = "guest";
  private String password = "guest";
  private String hostname = "localhost";
  private int rmPort = 6672;
  private String virtualHost = "/";

  @Given("^the respondent has completed online$")
  public void theRespondentHasCompletedOnline() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(hostname);
    factory.setUsername(username);
    factory.setPassword(password);
    factory.setPort(rmPort);
    factory.setVirtualHost(virtualHost);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, XML.getBytes());

    channel.close();
    connection.close();
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

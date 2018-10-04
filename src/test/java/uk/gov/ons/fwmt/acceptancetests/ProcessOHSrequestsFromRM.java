package uk.gov.ons.fwmt.acceptancetests;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProcessOHSrequestsFromRM {

  private static final String EXCHANGE_NAME = "action-outbound-exchange";
  private static final String XML = "";
  private static final String ROUTING_KEY = "Action.Field.binding";
  private static final String EXCHANGE_TYPE = "direct";
  private static final String ACTION_FIELD_QUEUE = "Action.Field";

  private String username = "guest";
  private String password = "guest";
  private String hostname = "localhost";
  private int rmPort = 6672;
  private String virtualHost = "/";

  @Given("^RM sends LMS (\\d+) case samples to the Gateway$")
  public void rm_sends_LMS_case_samples_to_the_Gateway(int arg1) throws IOException, TimeoutException {
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
    }

    channel.close();
    connection.close();
	}

	@When("^Gateway transforms the data$")
  public void gateway_transforms_the_data() {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^an LMS job is created$")
  public void an_LMS_job_is_created() {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^loaded in TM$")
  public void loaded_in_TM() {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}

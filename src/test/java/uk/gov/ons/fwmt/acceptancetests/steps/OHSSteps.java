package uk.gov.ons.fwmt.acceptancetests.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.extern.slf4j.Slf4j;
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

import static org.junit.Assert.assertEquals;

@Slf4j
public class OHSSteps {

  private String username = "guest";
  private String password = "guest";
  private String hostname = "localhost";
  private int rmPort = 6672;
  private String virtualHost = "/";

  @Before("Clears any previous jobs from mock")
  public void resetMock() throws IOException {
    URL url = new URL("http://localhost:9099/logger/reset");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    if (conn.getResponseCode() != 200) {
      throw new RuntimeException("Failed : HTTP error code : "
          + conn.getResponseCode());
    }
  }

  @Given("^RM sends OHS (\\d+) \"([^\"]*)\" case samples to the Gateway$")
  public void rm_sends_LMS_case_samples_to_the_Gateway(int noOfJobs, String type)
      throws IOException, TimeoutException, InterruptedException {
    ConnectionFactory factory = getConnectionFactory();

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    String message = setMessage(noOfJobs, type, channel);

    sendToQueue(noOfJobs, message, channel);

    channel.close();
    connection.close();
  }

  private String readFile(String filename) throws IOException {
    File file = new File(filename);

    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
    StringBuilder stringBuilder = new StringBuilder();
    String line;

    try {
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
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

  private String setMessage(String type) throws IOException {
    String message;
    switch (type) {
    case "create":
      message = readFile("src/text/resources/files/xmlcreate.xml");
      return message;
    case "cancel":
      message = readFile("src/text/resources/files/xmlcancel.xml");
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
      Thread.sleep(2000);
    }
  }

  @Then("^loaded in TM (\\d+)$")
  public void loaded_in_TM(int jobs) throws IOException {
    URL url = new URL("http://localhost:9099/logger/allMessages");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Accept", "application/json");

    if (conn.getResponseCode() != 200) {
      throw new RuntimeException("Failed : HTTP error code : "
          + conn.getResponseCode());
    }

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
        (conn.getInputStream())));
    StringBuilder stringBuilder = new StringBuilder();
    String line;

    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line).append("\n");
    }

    String result = stringBuilder.toString();

    ObjectMapper mapper = new ObjectMapper();
    List<MockMessage> message = mapper.readValue(result, new TypeReference<List<MockMessage>>() {
    });

    assertEquals(jobs, message.size());
  }
}

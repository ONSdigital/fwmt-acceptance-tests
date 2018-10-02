package uk.gov.ons.fwmt.acceptancetests.smoketest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

public class GatewaySmokeTestSteps {

    static final String RMA_URL = System.getenv("RMA_URL");;
    static final String JS_USERNAME = System.getenv("JS_USERNAME");
    static final String JS_PASSWORD = System.getenv("JS_PASSWORD");
    static final String JS_URL = System.getenv("JS_URL");;
    static final String TM_USERNAME = System.getenv("TM_USERNAME");
    static final String TM_PASSWORD = System.getenv("TM_PASSWORD");
    static final String TM_URL = System.getenv("TM_URL");;

    @Given("^Check RmAdpater is running$")
    public void checkAdpaterRunning() throws Exception {

        final String uri = "http://localhost:9094/actuator/health";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        Assert.assertTrue(result.contains("\"status\":\"UP\""));
    }

    @Given("^Check Jobservice is running$")
    public void checkJobserviceRunning() throws Exception {

        final String plainCreds = "user:password";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        final String url = "http://localhost:9999/jobs/health";
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        Assert.assertTrue(result.contains("\"status\":\"UP\""));
    }

    @Given("^Check Rabbit is running$")
    public void checkRabbitRunning() throws Exception {

      CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
      RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
      String result1 = rabbitAdmin.getQueueProperties("rm-adapter").getProperty("QUEUE_NAME");
      String result2 = rabbitAdmin.getQueueProperties("jobsvc-adapter").getProperty("QUEUE_NAME");
      String result3 = rabbitAdmin.getQueueProperties("adapter-jobSvc").getProperty("QUEUE_NAME");
      String result4 = rabbitAdmin.getQueueProperties("adapter-rm").getProperty("QUEUE_NAME");
      //String result5 = rabbitAdmin.getQueueProperties("Action.Field").getProperty("QUEUE_NAME");

      Assert.assertEquals(result1,"rm-adapter");
      Assert.assertEquals(result2,"jobsvc-adapter");
      Assert.assertEquals(result3,"adapter-jobSvc");
      Assert.assertEquals(result4,"adapter-rm");
      //Assert.assertEquals(result5,"Action.Field");
    }

    @Given("^Check Tmoblie is running$")
    public void checkTmobileRunning() throws Exception {

        final String plainCreds = TM_USERNAME+":"+TM_PASSWORD;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        final String url = TM_URL;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        HttpStatus result = response.getStatusCode();

        Assert.assertEquals(result, HttpStatus.OK);
    }
}
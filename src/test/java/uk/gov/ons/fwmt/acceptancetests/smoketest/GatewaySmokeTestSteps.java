package uk.gov.ons.fwmt.acceptancetests.smoketest;

import cucumber.api.java.en.Given;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.gov.ons.fwmt.acceptancetests.utils.Config;

public class GatewaySmokeTestSteps {

  @Given("^Check RmAdpater is running$")
  public void checkAdpaterRunning() {

    final String uri = Config.RMA_URL;

    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(uri, String.class);

    Assert.assertTrue(result.contains("\"status\":\"UP\""));
  }

  @Given("^Check Job Service is running$")
  public void checkJobserviceRunning() {

    final String plainCreds = Config.JS_USERNAME+":"+Config.JS_PASSWORD;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    final String url = Config.JS_URL;
    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    String result = response.getBody();

    Assert.assertTrue(result.contains("\"status\":\"UP\""));
  }


  @Given("^Check RM Rabbit is running$")
  public void checkRMRabbitRunning() {
    final String uri = Config.RMA_RABBIT_URL;

    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(uri, String.class);

    Assert.assertTrue(result.equalsIgnoreCase("true"));

  }

  @Given("^Check Rabbit is running$")
  public void checkRabbitRunning() {


    final String plainCreds = Config.JS_USERNAME+":"+Config.JS_PASSWORD;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    final String url = Config.JS_RABBIT_URL;
    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    String result = response.getBody();

    Assert.assertEquals("[\"jobsvc-adapter\",\"adapter-jobSvc\",\"adapter-rm\"]",result);

  }

  @Given("^Check Tmoblie is running$")
  public void checkTmobileRunning() {

    final String plainCreds = Config.TM_USERNAME + ":" + Config.TM_PASSWORD;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);
    final String url = Config.TM_URL;
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    HttpStatus result = response.getStatusCode();

    Assert.assertEquals(result, HttpStatus.OK);
  }
}
package uk.gov.ons.fwmt.acceptancetests.smoketest;

import cucumber.api.java.en.Given;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:application.properties")
public class GatewaySmokeTestSteps {

  @Value("${service.rmadmpter.url}")
  private String rmAdapterURL;

  @Value("${service.rmadmpter.rabbitmqcheck.url}")
  private String rmAdapterRabbitMQCheckURL;

  @Value("${service.jobservice.url}")
  private String jobserviceURL;

  @Value("${service.jobservice.username}")
  private String jobserviceUsername;

  @Value("${service.jobservice.password}")
  private String jobservicePassword;

  @Value("${service.tm.username}")
  private String tmUsername;

  @Value("${service.tm.password}")
  private String tmPassword;

  @Value("${service.tm.url}")
  private String tmUrl;

  @Value("${service.tm.wsdl}")
  private String tmWsdl;

  @Given("^Check RM Adapter is running$")
  public void checkAdapterRunning() {
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(rmAdapterURL + "/actuator/health", String.class);

    Assert.assertTrue(result.contains("\"status\":\"UP\""));
  }

  @Given("^Check Job Service is running$")
  public void checkJobServiceRunning() {
    final String plainCreds = jobserviceUsername + ":" + jobservicePassword;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate
        .exchange(jobserviceURL + "/health", HttpMethod.GET, request, String.class);
    String result = response.getBody();

    Assert.assertTrue(result.contains("\"status\":\"UP\""));
  }

  @Given("^Check RM Rabbit is running$")
  public void checkRMRabbitRunning() {
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(rmAdapterRabbitMQCheckURL + "/queue?qname=Action.Field", String.class);

    Assert.assertTrue(result.equalsIgnoreCase("true"));
  }

  @Given("^Check Rabbit is running$")
  public void checkRabbitRunning() {
    final String plainCreds = jobserviceUsername + ":" + jobservicePassword;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate
        .exchange(rmAdapterRabbitMQCheckURL + "/queue?qname=Action.Field", HttpMethod.GET, request, String.class);
    String result = response.getBody();

    Assert.assertEquals("true", result);
  }

  @Given("^Check TMoblie is running$")
  public void checkTMobileRunning() {
    final String plainCreds = tmUsername + ":" + tmPassword;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(tmUrl + tmWsdl, HttpMethod.GET, request, String.class);
    HttpStatus result = response.getStatusCode();

    Assert.assertEquals(result, HttpStatus.OK);
  }
}
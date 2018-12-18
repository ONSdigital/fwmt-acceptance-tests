package uk.gov.ons.fwmt.acceptancetests.smoketestlegacy;

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
public class LegacySmokeTestSteps {

  @Value("${service.legacy.jobservice.url}")
  private String jobserviceURL;

  @Value("${service.legacy.jobservice.username}")
  private String jobserviceUsername;

  @Value("${service.legacy.jobservice.password}")
  private String jobservicePassword;

  @Value("${service.legacy.resource.url}")
  private String resourceURL;

  @Value("${service.legacy.resource.username}")
  private String resourceUsername;

  @Value("${service.legacy.resource.password}")
  private String resourcePassword;

  @Value("${service.legacy.staff.url}")
  private String staffURL;

  @Value("${service.legacy.staff.username}")
  private String staffUsername;

  @Value("${service.legacy.staff.password}")
  private String staffPassword;

  @Value("${service.legacy.tm.url}")
  private String tmURL;

  @Value("${service.legacy.tm.username}")
  private String tmUsername;

  @Value("${service.legacy.tm.password}")
  private String tmPassword;

  public void setServiceCredentials(String type) throws Exception {
    String username = "unknown";
    String password = "unknown";
    String url = "unknown";

    switch (type) {
    case "Resource Serivce":
      username = resourceUsername;
      password = resourcePassword;
      url = resourceURL;
      break;
    case "Legacy Job Serivce":
      username = jobserviceUsername;
      password = jobservicePassword;
      url = jobserviceURL;
      break;
    case "Staff Serivce":
      username = staffUsername;
      password = staffPassword;
      url = staffURL;
      break;
    case "TMoblie":
      username = tmUsername;
      password = tmPassword;
      url = tmURL;
      break;
    default:
      break;
    }
    checkServiceRunning(type, username, password, url);
  }

  public void checkServiceRunning(String type, String username, String password, String url) {
    final String plainCreds = username + ":" + password;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request = new HttpEntity<String>(headers);
    if (type.equals("TMoblie")) {
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

      Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    } else {
      ResponseEntity<String> response = restTemplate.exchange(url + "/health", HttpMethod.GET, request, String.class);
      String result = response.getBody();

      Assert.assertTrue(result.contains("\"status\":\"UP\""));
    }
  }

  @Given("^Check \"([^\"]*)\" is running$")
  public void checkResourceServiceRunning(String service) throws Exception {
    setServiceCredentials(service);
  }

  @Given("^Check Legacy Jobservice is running$")
  public void checkLegacyJobserviceRunning() throws Exception {
    final String plainCreds = jobserviceUsername + ":" + jobservicePassword;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(jobserviceURL, HttpMethod.GET, request, String.class);
    String result = response.getBody();

    Assert.assertTrue(result.contains("\"status\":\"UP\""));
  }

  @Given("^Check Staff Service is running$")
  public void checkStaffserviceRunning() throws Exception {
    final String plainCreds = staffUsername + ":" + staffPassword;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(staffURL, HttpMethod.GET, request, String.class);
    String result = response.getBody();

    Assert.assertTrue(result.contains("\"status\":\"UP\""));
  }

  @Given("^Check Tmoblie is running$")
  public void checkTmobileRunning() throws Exception {
    final String plainCreds = tmUsername + ":" + tmPassword;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(tmURL, HttpMethod.GET, request, String.class);
    HttpStatus result = response.getStatusCode();

    Assert.assertEquals(result, HttpStatus.OK);
  }
}

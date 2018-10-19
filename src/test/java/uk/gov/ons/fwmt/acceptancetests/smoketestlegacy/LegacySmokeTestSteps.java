package uk.gov.ons.fwmt.acceptancetests.smoketestlegacy;

import cucumber.api.java.en.Given;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LegacySmokeTestSteps {

    static final String RS_USERNAME = System.getenv("RS_USERNAME");
    static final String RS_PASSWORD = System.getenv("RS_PASSWORD");
    static final String RS_URL = System.getenv("RS_URL");;

    static final String LS_USERNAME = System.getenv("LS_USERNAME");
    static final String LS_PASSWORD = System.getenv("LS_PASSWORD");
    static final String LS_URL = System.getenv("LS_URL");;

    static final String SS_USERNAME = System.getenv("SS_USERNAME");
    static final String SS_PASSWORD = System.getenv("SS_PASSWORD");
    static final String SS_URL = System.getenv("SS_URL");;

    static final String TM_USERNAME = System.getenv("TM_USERNAME");
    static final String TM_PASSWORD = System.getenv("TM_PASSWORD");
    static final String TM_URL = System.getenv("TM_URL");;

    @Given("^Check Resource Service is running$")
    public void checkResourceServiceRunning() throws Exception {

        final String plainCreds = RS_USERNAME+":"+RS_PASSWORD;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        final String url = RS_URL;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        Assert.assertTrue(result.contains("\"status\":\"UP\""));
    }

    @Given("^Check Legacy Jobservice is running$")
    public void checkLegacyJobserviceRunning() throws Exception {

        final String plainCreds = LS_USERNAME+":"+LS_PASSWORD;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        final String url = LS_URL;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        Assert.assertTrue(result.contains("\"status\":\"UP\""));
    }

    @Given("^Check Staff Service is running$")
    public void checkStaffserviceRunning() throws Exception {

        final String plainCreds = SS_USERNAME+":"+SS_PASSWORD;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        final String url = SS_URL;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        Assert.assertTrue(result.contains("\"status\":\"UP\""));
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
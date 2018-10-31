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
import uk.gov.ons.fwmt.acceptancetests.utils.Config;

public class LegacySmokeTestSteps {


    public void setServiceCredentials(String type) throws Exception {

        String username = "unknown";
        String password = "unknown";
        String url = "unknown";

        switch (type) {
            case "Resource Serivce":
                username = Config.RS_USERNAME;
                password = Config.RS_PASSWORD;
                url = Config.RS_URL;
                break;
            case "Legacy Job Serivce":
                username = Config.LS_USERNAME;
                password = Config.LS_PASSWORD;
                url = Config.LS_URL;
                break;
            case "Staff Serivce":
                username = Config.SS_USERNAME;
                password = Config.SS_PASSWORD;
                url = Config.SS_URL;
                break;
            case "TMoblie":
                username = Config.TM_USERNAME;
                password = Config.TM_PASSWORD;
                url = Config.TM_URL;
                break;
            default:
                break;
        }
        checkServiceRunning(username,password,url);


    }

    public void checkServiceRunning (String username, String password, String url){

        final String plainCreds = username+":"+password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        Assert.assertTrue(result.contains("\"status\":\"UP\""));
    }

    @Given("^Check \"([^\"]*)\" is running$")
    public void checkResourceServiceRunning(String service) throws Exception {
        setServiceCredentials(service);
    }

    @Given("^Check Legacy Jobservice is running$")
    public void checkLegacyJobserviceRunning() throws Exception {

        final String plainCreds = Config.LS_USERNAME+":"+Config.LS_PASSWORD;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        final String url = Config.LS_URL;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        Assert.assertTrue(result.contains("\"status\":\"UP\""));
    }

    @Given("^Check Staff Service is running$")
    public void checkStaffserviceRunning() throws Exception {

        final String plainCreds = Config.SS_USERNAME+":"+Config.SS_PASSWORD;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        final String url = Config.SS_URL;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String result = response.getBody();

        Assert.assertTrue(result.contains("\"status\":\"UP\""));
    }

    @Given("^Check Tmoblie is running$")
    public void checkTmobileRunning() throws Exception {

        final String plainCreds = Config.TM_USERNAME+":"+Config.TM_PASSWORD;
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

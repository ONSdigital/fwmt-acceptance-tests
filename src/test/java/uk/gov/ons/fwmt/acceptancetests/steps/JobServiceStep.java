package uk.gov.ons.fwmt.acceptancetests.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.gov.ons.fwmt.acceptancetests.clients.JobServiceClient;

import java.io.File;

@Component
@Slf4j
public class JobServiceStep {

  private RestTemplate restTemplate;
  private String baseUrl;
  private String storeCSVUrl;

  @Given("^I have submitted a sample CSV of type LFS named \"([^\"]*)\"$")
  public void iHaveASampleCSVOfType(String fileName) throws Throwable {
    try {
      restTemplate = new RestTemplate();
      File file = new File(String.valueOf("src/test/resources/data/"+fileName));
      Resource fileConvert = new FileSystemResource(file);
      MultiValueMap<String,Object> bodyMap = new LinkedMultiValueMap<>();
      bodyMap.add("file", fileConvert);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      final HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(bodyMap, headers);
      restTemplate.exchange("http://localhost:9091/jobs/", HttpMethod.POST, request, String.class);
    } catch (org.springframework.web.client.HttpClientErrorException HttpClientErrorException) {
      log.error("An error occurred while communicating with the resource service", HttpClientErrorException);
    }
  }

  @When("^the CSV is validated$")
  public void theCSVIsValidated() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
  }

  @And("^the ingested$")
  public void theIngested() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Then("^A job should be created$")
  public void aJobShouldBeCreated() throws Throwable {


    throw new PendingException();
  }
}

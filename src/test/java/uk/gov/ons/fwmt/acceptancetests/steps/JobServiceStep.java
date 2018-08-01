package uk.gov.ons.fwmt.acceptancetests.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.ons.fwmt.acceptancetests.clients.JobServiceClient;

@Component
public class JobServiceStep {


  @Autowired JobServiceClient jobServiceClient;

  @Given("^I have submitted a sample CSV of type LFS named \"([^\"]*)\"$")
  public void iHaveASampleCSVOfType(String arg0) throws Throwable {
    jobServiceClient.sendCSV(arg0);
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

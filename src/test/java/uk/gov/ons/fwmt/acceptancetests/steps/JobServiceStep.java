package uk.gov.ons.fwmt.acceptancetests.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.ons.fwmt.acceptancetests.SpringIntegrationSteps;

@Component
@Slf4j
public class JobServiceStep extends SpringIntegrationSteps {

  public JobServiceStep(String baseUrl, String findPath, String createPath, String createUserPath) {
    super(baseUrl, findPath, createPath, createUserPath);
  }

  @Given("^I have created the following test user in the gateway with authno \"([^\"]*)\" and TM username \"([^\"]*)\"$")
  public void iHaveCreatedTheFollowingTestUserInTheGatewayWithAuthnoAndTmId(String authNo, String tmUsername) {
    createUserInTm(authNo, tmUsername);
  }

  @When("^I submit a sample CSV of type LFS named \"([^\"]*)\"$")
  public void iHaveASampleCSVOfType(String fileName) {
    CSVPost(fileName);
  }

  @Then("^A database record for the job should contain TM id \"([^\"]*)\"$")
  public void aDatabaseRecordForTheJobShouldContainTMId(String tmJobId) {
    checkDatabase(tmJobId);
  }
}

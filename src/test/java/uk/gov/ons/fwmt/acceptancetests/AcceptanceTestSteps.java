package uk.gov.ons.fwmt.acceptancetests;

import cucumber.api.java.en.Given;

import static junit.framework.TestCase.assertTrue;

public class AcceptanceTestSteps {

  @Given("^pass$")
  public void passTest() throws Exception {
    assertTrue(false);
  }
}

package uk.gov.ons.fwmt.acceptancetests;

import cucumber.api.java.en.Given;

public class AcceptanceTestSteps {

  @Given("^pass$")
  public void passTest() throws Exception {
    assert(true);
  }

  @Given("^fail$")
  public void failTest() throws Exception {
    assert(false);
  }
}

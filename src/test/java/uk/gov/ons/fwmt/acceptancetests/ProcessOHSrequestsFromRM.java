package uk.gov.ons.fwmt.acceptancetests;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProcessOHSrequestsFromRM {
	@Given("^RM sends LMS (\\d+) case samples to the Gateway$")
	public void rm_sends_LMS_case_samples_to_the_Gateway(int arg1) throws Exception {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^Gateway transforms the data$")
	public void gateway_transforms_the_data() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^an LMS job is created$")
	public void an_LMS_job_is_created() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^loaded in TM$")
	public void loaded_in_TM() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}

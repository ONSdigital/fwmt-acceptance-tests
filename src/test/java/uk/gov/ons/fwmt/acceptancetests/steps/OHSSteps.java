package uk.gov.ons.fwmt.acceptancetests.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.json.JSONObject;
import uk.gov.ons.fwmt.acceptancetests.utils.MockMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

public class OHSSteps {

    private final String EXCHANGE_NAME = "action-outbound-exchange";
    private final String ROUTING_KEY = "Action.Field.binding";
    private final String XMLCREATE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<ns2:actionInstruction\n"
            + "    xmlns:ns2=\"http://ons.gov.uk/ctp/response/action/message/instruction\">\n"
            + "    <actionRequest>\n"
            + "        <actionId>c97f4de4-2198-47fb-8c76-a940c803bbe0</actionId>\n"
            + "        <responseRequired>false</responseRequired>\n"
            + "        <actionPlan>77aa08b6-bf81-4de2-98b8-881e10356a14</actionPlan>\n"
            + "        <actionType>SOCIALICF</actionType>\n"
            + "        <address>\n"
            + "            <sampleUnitRef>LMS00001</sampleUnitRef>\n"
            + "            <locality></locality>\n"
            + "            <line1>11 HILL VIEW</line1>\n"
            + "            <line2></line2>\n"
            + "            <townName>STOCKTON-ON-TEES</townName>\n"
            + "            <postcode>AA1 1AA</postcode>\n"
            + "            <country>E</country>\n"
            + "        </address>\n"
            + "        <returnByDate>11/11/2018</returnByDate>\n"
            + "        <legalBasis>Statistics of Trade Act 1947</legalBasis>\n"
            + "        <caseGroupStatus>NOTSTARTED</caseGroupStatus>\n"
            + "        <caseId>95db01c8-f117-4c5b-9672-757ab661220e</caseId>\n"
            + "        <priority>highest</priority>\n"
            + "        <caseRef>1000000000000101</caseRef>\n"
            + "        <iac>37j59xnrjnkc</iac>\n"
            + "        <events>\n"
            + "            <event>CASE_CREATED : null : SYSTEM : Case created when Initial creation of case</event>\n"
            + "        </events>\n"
            + "        <exerciseRef>202103</exerciseRef>\n"
            + "        <userDescription>March 2021</userDescription>\n"
            + "        <surveyName>Monthly Wages and Salaries Survey</surveyName>\n"
            + "        <surveyRef>LMS</surveyRef>\n"
            + "    </actionRequest>\n"
            + "</ns2:actionInstruction>";
    private final String XMLCANCEL =
            "<ins:actionInstruction xmlns:ins=\"http://ons.gov.uk/ctp/response/action/message/instruction\">\n"
                    + "\t<actionCancel>\n"
                    + "\t\t<actionId>5a9f4323</actionId>\n"
                    + "\t\t<responseRequired>true</responseRequired>\n"
                    + "\t\t<reason>deleted for test</reason>\n"
                    + "\t</actionCancel>\n"
                    + "</ins:actionInstruction>";


    private String username = "guest";
    private String password = "guest";
    private String hostname = "localhost";
    private int rmPort = 6672;
    private String virtualHost = "/";


    @Before
    public void rest_mock() throws IOException {
        URL url = new URL("http://localhost:9099/logger/reset");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
    }

    @Given("^RM sends LMS (\\d+) \"([^\"]*)\" case samples to the Gateway$")
    public void rm_sends_LMS_case_samples_to_the_Gateway(int arg1, String type)
            throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostname);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(rmPort);
        factory.setVirtualHost(virtualHost);

        String message = "";

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        if (type.equals("create")) {
            message = XMLCREATE;
        } else if (type.equals("cancel")) {
            message = XMLCANCEL;
        } else {
            System.out.println(type+" is an unknown message please use create or cancel");
        }
        if (message != "") {
            for (int i = 0; i < arg1; i++) {
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
                Thread.sleep(2000);
            }
        }

        channel.close();
        connection.close();
    }

    @Then("^loaded in TM (\\d+)$")
    public void loaded_in_TM(int jobs) throws IOException {
        URL url = new URL("http://localhost:9099/logger/allMessages");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String line;

        try  {
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line + "\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        String result = sb.toString();

        ObjectMapper mapper = new ObjectMapper();
        List<MockMessage> message = mapper.readValue(result, new TypeReference<List<MockMessage>>(){});


        assertEquals(message.size(),jobs);
    }
}

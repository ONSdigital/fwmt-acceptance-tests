package uk.gov.ons.fwmt.acceptancetests;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class SpringBootBaseAcceptanceTest {

    private RestTemplate restTemplate;

    private final String XML = "<ins:actionInstruction xmlns:ins=\"http://ons.gov.uk/ctp/response/action/message/instruction\"><actionCancel><actionId>5a9f4323</actionId><responseRequired>true</responseRequired><reason>deleted for test</reason></actionCancel></ins:actionInstruction>";

    private RabbitTemplate rabbitTemplate;

    private static final String ACTION_FIELD_QUEUE = "Action.Field";
    private static final String ACTION_FIELD_BINDING = "Action.Field.binding";

    public void testPathFromRMToJobSvc() {

        rabbitTemplate.convertAndSend(ACTION_FIELD_QUEUE, ACTION_FIELD_BINDING, XML.getBytes());
    }

}

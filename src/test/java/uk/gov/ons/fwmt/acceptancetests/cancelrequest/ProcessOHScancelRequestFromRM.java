package uk.gov.ons.fwmt.acceptancetests.cancelrequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ProcessOHScancelRequestFromRM {

    private final String XML = "<ins:actionInstruction xmlns:ins=\"http://ons.gov.uk/ctp/response/action/message/instruction\"><actionCancel><actionId>5a9f4323</actionId><responseRequired>true</responseRequired><reason>deleted for test</reason></actionCancel></ins:actionInstruction>";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String ACTION_FIELD_QUEUE = "Action.Field";
    private static final String ACTION_FIELD_BINDING = "Action.Field.binding";

    public void testPathFromRMToJobSvc() {

      rabbitTemplate.convertAndSend(ACTION_FIELD_QUEUE, ACTION_FIELD_BINDING, XML.getBytes());
    }

}

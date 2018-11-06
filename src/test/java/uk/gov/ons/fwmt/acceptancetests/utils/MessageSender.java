package uk.gov.ons.fwmt.acceptancetests.utils;

import static uk.gov.ons.fwmt.acceptancetests.utils.Config.BASIC_AUTH_PASSWORD;
import static uk.gov.ons.fwmt.acceptancetests.utils.Config.BASIC_AUTH_USER;
import static uk.gov.ons.fwmt.acceptancetests.utils.Config.JOB_SVC_URL;
import static uk.gov.ons.fwmt.acceptancetests.utils.Config.TM_RESPONSE_ENDPOINT;
import static uk.gov.ons.fwmt.acceptancetests.utils.Config.MOCK_URL;
import static uk.gov.ons.fwmt.fwmtgatewaycommon.config.QueueNames.ADAPTER_TO_RM_QUEUE;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSender {


  public MessageSender() throws IOException, TimeoutException {
  }

  public int sendResponseMessage(String data) throws IOException, AuthenticationException {

    CloseableHttpClient client = HttpClients.createDefault();
    int resopnseCode;

    try {
      HttpPost httpPost = new HttpPost(JOB_SVC_URL + TM_RESPONSE_ENDPOINT);

      httpPost.setEntity(new StringEntity(data));
      UsernamePasswordCredentials creds
          = new UsernamePasswordCredentials(BASIC_AUTH_USER, BASIC_AUTH_PASSWORD);
      httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
      httpPost.addHeader("Content-type", "text/xml");

      CloseableHttpResponse response = client.execute(httpPost);
      resopnseCode = response.getStatusLine().getStatusCode();
    } finally {
      client.close();
    }

    return resopnseCode;
  }

  public long getMessageCount() {
    RestTemplate restTemplate = new RestTemplate();
    String messageCountUrl = MOCK_URL + "/queue/count/" + ADAPTER_TO_RM_QUEUE;
    ResponseEntity<Long> messageCount = restTemplate.getForEntity(messageCountUrl, Long.class);
    return messageCount.getBody();
  }

  public String getMessage() throws InterruptedException {
    RestTemplate restTemplate = new RestTemplate();
    String messageUrl = MOCK_URL + "/queue/message/" + ADAPTER_TO_RM_QUEUE;
    String message = null;
    for(int i=0;i<10;i++) {
      ResponseEntity<String> messageEntity = restTemplate.getForEntity(messageUrl, String.class);
      message = messageEntity.getBody();
      
      if(message != null) {
        break;
      }
      Thread.sleep(500);
    }
    return message;
  }
}

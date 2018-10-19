package uk.gov.ons.fwmt.acceptancetests.utils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.NoArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static uk.gov.ons.fwmt.acceptancetests.utils.Config.BASIC_AUTH_PASSWORD;
import static uk.gov.ons.fwmt.acceptancetests.utils.Config.BASIC_AUTH_USER;
import static uk.gov.ons.fwmt.acceptancetests.utils.Config.JOB_SVC_URL;
import static uk.gov.ons.fwmt.acceptancetests.utils.Config.TM_RESPONSE_ENDPOINT;
import static uk.gov.ons.fwmt.fwmtgatewaycommon.config.QueueNames.ADAPTER_TO_RM_QUEUE;


public class MessageSender {

  private final String USER_AGENT = "Mozilla/5.0";

  private static String message;

  private static int messageCount;

  public MessageSender() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclarePassive(ADAPTER_TO_RM_QUEUE);
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
          AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        message = new String(body, "UTF-8");
        messageCount++;
      }
    };

    channel.basicConsume(ADAPTER_TO_RM_QUEUE, true, consumer);
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

  public int getMessageCount() {
    return messageCount;
  }

  public String getMessage() throws InterruptedException {

    for(int i=0;i<10;i++) {
      if(message != null) {
        break;
      }
      Thread.sleep(500);
    }
    return message;
  }
}

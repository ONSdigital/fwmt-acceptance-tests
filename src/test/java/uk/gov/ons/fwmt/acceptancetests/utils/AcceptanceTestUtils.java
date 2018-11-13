package uk.gov.ons.fwmt.acceptancetests.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import uk.gov.ons.fwmt.fwmtgatewaycommon.config.QueueNames;

@Component
@Slf4j
public final class AcceptanceTestUtils {

  @Value("${service.mocktm.url}")
  private String mockTmURL;
  
  private RestTemplate restTemplate = new RestTemplate();

  public void clearQueues() throws URISyntaxException{
    clearQueue("Action.Field");
    clearQueue("Action.FieldDLQ");
    clearQueue("adapter-jobSvc");
    clearQueue("adapter-jobSvc");
    clearQueue("adapter-jobSvc.DLQ");
    clearQueue("adapter-rm");
    clearQueue("adapter-rm.DLQ");
    clearQueue("jobsvc-adapter");
    clearQueue("jobSvc-adapter.DLQ");
    clearQueue("rm-adapter.DLQ");
  }

  public void clearQueue(String queueName) throws URISyntaxException{
    URI uri = new URI(mockTmURL+"/queue/?qname="+ queueName);
    restTemplate.delete(uri);
  }
  
  public void resetMock() throws IOException, TimeoutException {
    URL url = new URL(mockTmURL +"/logger/reset");
    log.info("rest-mock_url:" + url.toString());
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    httpURLConnection.setRequestMethod("GET");
      if (httpURLConnection.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : "
            + httpURLConnection.getResponseCode());
      }
  }

}

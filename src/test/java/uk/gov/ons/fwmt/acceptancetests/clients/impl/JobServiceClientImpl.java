package uk.gov.ons.fwmt.acceptancetests.clients.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.gov.ons.fwmt.acceptancetests.clients.JobServiceClient;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Service
public class JobServiceClientImpl implements JobServiceClient{
  private transient RestTemplate restTemplate;
  private transient String storeCSVUrl;

  @Autowired
  public JobServiceClientImpl(
      RestTemplate restTemplate,
      @Value("${service.resource.baseUrl}") String baseUrl,
      @Value("${service.resource.operation.jobs.create.path}") String sendCSVPath) {
    this.restTemplate = restTemplate;
    this.storeCSVUrl = baseUrl + sendCSVPath;
  }


  @Override
  public void sendCSV(String sendCSVName){
    try {

      File file = new File(String.valueOf("src/test/resources/data/"+sendCSVName));
      Resource fileConvert = new FileSystemResource(file);
      MultiValueMap<String,Object> bodyMap = new LinkedMultiValueMap<>();
      bodyMap.add("file", fileConvert);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      final HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(bodyMap, headers);
      restTemplate.exchange(storeCSVUrl, HttpMethod.POST, request, String.class);
    } catch (HttpClientErrorException HttpClientErrorException) {
      log.error("An error occurred while communicating with the resource service", HttpClientErrorException);
    }
  }
}

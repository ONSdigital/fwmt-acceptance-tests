package uk.gov.ons.fwmt.acceptancetests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uk.gov.ons.fwmt.acceptancetests.dto.JobDto;
import uk.gov.ons.fwmt.acceptancetests.dto.UserDto;
import uk.gov.ons.fwmt.acceptancetests.helper.ResourceRESTHelper;

import java.io.File;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration
@Service
public class SpringIntegrationSteps {
  private transient RestTemplate restTemplate;

  private transient String findUrl;
  private transient String createUrl;
  private transient String createUserUrl;
  //  @Value("${service.resource.username:user}")
  private String userName = "user";
  //  @Value("${service.resource.password:password}")
  private String password = "password";

  @Autowired
  public SpringIntegrationSteps(
      @Value("${service.resource.baseUrl}") String baseUrl,
      @Value("${service.resource.operation.jobs.find.path}") String findPath,
      @Value("${service.resource.operation.jobs.create.path}") String createPath,
      @Value("${service.resource.operation.users.create.path}") String createUserPath) {
    this.findUrl = baseUrl + findPath;
    this.createUrl = baseUrl + createPath;
    this.createUserUrl = baseUrl + createUserPath;
  }

  @Bean
  private RestTemplate resourcesRestTemplate(RestTemplateBuilder builder) {
    return builder
        .basicAuthorization(userName, password)
        .build();
  }

  protected void createUserInTm(String authNo, String tmUsername) {
    UserDto userDto = UserDto.builder().authNo(authNo).tmUsername(tmUsername).active(true).alternateAuthNo(null)
        .build();

    final RestTemplate restTemplate = resourcesRestTemplate(new RestTemplateBuilder());
    final Optional<UserDto> resultUserDto = ResourceRESTHelper
        .post(restTemplate, createUserUrl, userDto, UserDto.class);
  }

  protected void CSVPost(String fileName) {
    int expectedHttpStatusCode = 200;

    final RestTemplate restTemplate = resourcesRestTemplate(new RestTemplateBuilder());
    File file = new File(String.valueOf("src/test/resources/data/" + fileName));
    Resource fileConvert = new FileSystemResource(file);
    MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
    bodyMap.add("file", fileConvert);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    final HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(bodyMap, headers);
    ResponseEntity result = restTemplate
        .exchange(createUrl, HttpMethod.POST, request, String.class);

    assertEquals(expectedHttpStatusCode, result.getStatusCode().value());
  }

  protected void checkDatabase(String tmJobId) {
    final RestTemplate restTemplate = resourcesRestTemplate(new RestTemplateBuilder());
    final Optional<JobDto> jobDto = ResourceRESTHelper.get(restTemplate, findUrl, JobDto.class, tmJobId);

    assertTrue(jobDto.isPresent());
    assertEquals(tmJobId, jobDto.toString());
  }
}
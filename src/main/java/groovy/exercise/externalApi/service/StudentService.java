package groovy.exercise.externalApi.service;

import groovy.exercise.externalApi.models.ExternalAPiResponse;
import groovy.exercise.externalApi.models.ExternalApiRequest;
import groovy.exercise.externalApi.models.StudentRequest;
import groovy.exercise.externalApi.models.StudentResponse;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.codehaus.groovy.control.CompilationFailedException;
import java.io.File;
import java.io.IOException;

@Service
public class StudentService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    private GroovyObject fieldMapper;

    @SuppressWarnings("CallToPrintStackTrace")
    public StudentService() {
        try {
            GroovyClassLoader classLoader = new GroovyClassLoader();
            Class<?> groovyClass = classLoader.parseClass(new File("src/main/java/groovy/exercise/externalApi/util/FieldMapper.groovy"));
            this.fieldMapper = (GroovyObject) groovyClass.newInstance();
        } catch (IOException | CompilationFailedException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public StudentResponse getStudentDetails(StudentRequest studentRequest) {
        StudentResponse studentResponse = new StudentResponse();
        try {
            ExternalApiRequest externalRequest = (ExternalApiRequest) fieldMapper.invokeMethod("mapToExternalApiRequest", studentRequest);
            String externalApiUrl = env.getProperty("external_api_url");
            String externalApiAuthorization = env.getProperty("authorization_value");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", externalApiAuthorization);

            HttpEntity<ExternalApiRequest> entity = new HttpEntity<>(externalRequest, headers);

            assert externalApiUrl != null;
            ResponseEntity<ExternalAPiResponse> responseEntity = restTemplate.postForEntity(externalApiUrl, entity, ExternalAPiResponse.class);
            ExternalAPiResponse externalResponse = responseEntity.getBody();
            studentResponse = (StudentResponse) fieldMapper.invokeMethod("mapToStudentResponse", externalResponse);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return studentResponse;
    }
}

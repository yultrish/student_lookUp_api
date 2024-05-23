package groovy.exercise.externalApi.service;

import groovy.exercise.externalApi.models.ExternalAPiResponse;
import groovy.exercise.externalApi.models.ExternalApiRequest;
import groovy.exercise.externalApi.models.StudentRequest;
import groovy.exercise.externalApi.models.StudentResponse;
import groovy.exercise.externalApi.util.FieldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@Service
public class StudentService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    public StudentResponse getStudentDetails(StudentRequest studentRequest) {
        ExternalApiRequest externalRequest = FieldMapper.mapToExternalApiRequest(studentRequest);
        String externalApiUrl = env.getProperty("external_api_url");
        String externalApiAuthorization = env.getProperty("authorization_value");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", externalApiAuthorization);

        HttpEntity<ExternalApiRequest> entity = new HttpEntity<>(externalRequest, headers);
        StudentResponse studentResponse = new StudentResponse();

        try {
            assert externalApiUrl != null;
            ResponseEntity<ExternalAPiResponse> responseEntity = restTemplate.postForEntity(externalApiUrl, entity, ExternalAPiResponse.class);
            ExternalAPiResponse externalResponse = responseEntity.getBody();
            studentResponse = FieldMapper.mapToStudentResponse(externalResponse);
        } catch (RestClientException e) {
            e.printStackTrace();
            studentResponse.setSuccess(false);
            studentResponse.setMessage("Failed to retrieve student details: " + e.getMessage());
        }
        return studentResponse;
    }
}


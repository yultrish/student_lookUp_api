package groovy.exercise.externalApi.service;

import groovy.exercise.externalApi.models.ExternalAPiResponse;
import groovy.exercise.externalApi.models.ExternalApiRequest;
import groovy.exercise.externalApi.models.StudentRequest;
import groovy.exercise.externalApi.models.StudentResponse;
import groovy.exercise.externalApi.util.FieldMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public StudentResponse getStudentDetails(StudentRequest studentRequest) {

        ExternalApiRequest externalRequest = FieldMapper.mapToExternalApiRequest(studentRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic dXNlcjp0MzV0cEA1NQ==");

        HttpEntity<ExternalApiRequest> entity = new HttpEntity<>(externalRequest, headers);
        String externalApiUrl = "https://business.mykowri.com/billing/lookup";
        StudentResponse studentResponse = new StudentResponse();

        try {
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


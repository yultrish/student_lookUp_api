package groovy.exercise.externalApi.controller;

import groovy.exercise.externalApi.models.StudentRequest;
import groovy.exercise.externalApi.models.StudentResponse;
import groovy.exercise.externalApi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/lookup")
    public StudentResponse lookupStudent(@RequestBody StudentRequest studentRequest) {
        StudentResponse response = studentService.getStudentDetails(studentRequest);
        return response;
    }
}

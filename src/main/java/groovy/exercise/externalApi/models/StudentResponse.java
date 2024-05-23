package groovy.exercise.externalApi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse {
        private boolean success;
        private String message;
        private StudentDetails studentDetails;
}

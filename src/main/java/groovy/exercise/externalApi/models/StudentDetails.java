package groovy.exercise.externalApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class StudentDetails {
        private String studentId;
        private String studentName;
        private String gender;
        private String classs;
}

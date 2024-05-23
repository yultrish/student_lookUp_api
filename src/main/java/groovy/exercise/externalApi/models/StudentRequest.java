package groovy.exercise.externalApi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentRequest {
    private String schoolCode;
    private String studentId;
    private Boolean includeInvoices = false;
}

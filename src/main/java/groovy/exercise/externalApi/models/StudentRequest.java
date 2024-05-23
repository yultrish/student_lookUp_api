package groovy.exercise.externalApi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    private String schoolCode;
    private String studentId;
    private Boolean includeInvoices = false;
}

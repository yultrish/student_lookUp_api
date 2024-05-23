package groovy.exercise.externalApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalAPiResponse {
    private boolean status;
    private String message;
    private Data data;

    @Getter
    @Setter
    public static class Data {
        private String billReference;
        private String customerName;
        private String customerSegment;
        private Details details;
    }

    @Getter
    @Setter
    public static class Details {
        @JsonProperty("Gender")
        private String gender;
    }
    }





package groovy.exercise.externalApi.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ExternalAPiResponse {
    private boolean status;
    private String message;

    @JsonProperty("data")
    private Map<String, Object> data = new HashMap<>();

    @JsonAnySetter
    public void setData(String key, Object value) {
        this.data.put(key, value);
    }
}

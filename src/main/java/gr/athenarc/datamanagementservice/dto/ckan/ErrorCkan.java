package gr.athenarc.datamanagementservice.dto.ckan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorCkan {

    private String message;

    @JsonProperty("__type")
    private String type;
}

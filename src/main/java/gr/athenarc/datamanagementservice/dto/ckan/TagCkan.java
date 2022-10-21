package gr.athenarc.datamanagementservice.dto.ckan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TagCkan {

    private String id;

    @JsonProperty("display_name")
    private String displayName;

    private String name;

    private String state;

    @JsonProperty("vocabulary_id")
    private String vocabularyId;
}

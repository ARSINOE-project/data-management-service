package gr.athenarc.datamanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Group {

    private String id;

    private String created;

    private String title;

    private String description;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("number_of_datasets")
    private Integer numberOfDatasets;
}

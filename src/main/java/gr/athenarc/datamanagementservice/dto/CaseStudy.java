package gr.athenarc.datamanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CaseStudy {

    private String id;

    private String title;

    private String name;

    private String created;

    private String description;

    @JsonProperty("image_url")
    private String imageUrl;
}
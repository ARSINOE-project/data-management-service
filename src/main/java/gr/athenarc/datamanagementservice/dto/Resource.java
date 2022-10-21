package gr.athenarc.datamanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Resource {

    private String id;

    private String created;

    private String description;

    private String format;

    @JsonProperty("last_modified")
    private String lastModified;

    private String name;

    private Integer size;

    private String url;

    @JsonProperty("resource_type")
    private String resourceType;

}

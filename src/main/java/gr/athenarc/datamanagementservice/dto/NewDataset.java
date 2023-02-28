package gr.athenarc.datamanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NewDataset {

    private String title;

    private String name;

    private String description;

    @JsonProperty("license_id")
    private String licenseId;

    @JsonProperty("publication_date")
    private String publicationDate;

    private String author;

    @JsonProperty("author_email")
    private String authorEmail;

    private String maintainer;

    @JsonProperty("maintainer_email")
    private String maintainerEmail;

    private String doi;

    private String origin;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("dataset_type")
    private String datasetType;

    private List<String> tags;

    @JsonProperty("case_study")
    private String caseStudy;

    @JsonProperty("private")
    private boolean isPrivate;
}

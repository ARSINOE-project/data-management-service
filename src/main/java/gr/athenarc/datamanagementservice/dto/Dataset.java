package gr.athenarc.datamanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Dataset {

    private String id;

    private String title;

    private String name;

    private String description;

    @JsonProperty("license_id")
    private String licenseId;

    @JsonProperty("license_title")
    private String licenseTitle;

    @JsonProperty("license_url")
    private String licenseUrl;

    @JsonProperty("private")
    private boolean isPrivate;

    @JsonProperty("case_study_id")
    private String caseStudyId;

    @JsonProperty("number_of_resources")
    private Integer numResources;

    @JsonProperty("publication_date")
    private String publicationDate;

    private String author;

    @JsonProperty("authorEmail")
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

    private List<Resource> resources;
}

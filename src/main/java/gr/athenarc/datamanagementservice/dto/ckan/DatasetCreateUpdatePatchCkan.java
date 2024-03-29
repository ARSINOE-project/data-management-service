package gr.athenarc.datamanagementservice.dto.ckan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DatasetCreateUpdatePatchCkan {

    private String id;

    private String title;

    private String name;

    private String notes;

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

    @JsonProperty("dataset_type_arsinoe")
    private String datasetType;

    private List<TagForNewDatasetCkan> tags;

    @JsonProperty("owner_org")
    private String caseStudy;

    @JsonProperty("visibility_arsinoe")
    private String visibilityArsinoe;

    @JsonProperty("zenodo_publish")
    private Boolean zenodoPublish;
}

package gr.athenarc.datamanagementservice.dto.ckan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DatasetCkan {

    private String author;

    @JsonProperty("author_email")
    private String authorEmail;

    @JsonProperty("creator_user_id")
    private String creatorUserId;

    @JsonProperty("dataset_type_arsinoe")
    private String datasetType;

    private String doi;

    private String id;

    @JsonProperty("isopen")
    private boolean isOpen;

    @JsonProperty("license_id")
    private String licenseId;

    @JsonProperty("license_title")
    private String licenseTitle;

    @JsonProperty("license_url")
    private String licenseUrl;

    private String maintainer;

    @JsonProperty("maintainer_email")
    private String maintainerEmail;

    @JsonProperty("metadata_created")
    private String metadataCreated;

    @JsonProperty("metadata_modified")
    private String metadataModified;

    private String name;

    private String notes;

    @JsonProperty("num_resources")
    private Integer numResources;

    @JsonProperty("num_tags")
    private Integer numTags;

    private Map<String, Object> organization;

    private String origin;

    @JsonProperty("owner_org")
    private String ownerOrg;

    @JsonProperty("private")
    private Boolean isPrivate;

    @JsonProperty("visibility_arsinoe")
    private String visibilityArsinoe;

    @JsonProperty("zenodo_publish")
    private Boolean zenodoPublish;

    @JsonProperty("publication_date")
    private String publicationDate;

    @JsonProperty("resource_type")
    private String resourceType;

    private String state;

    private String title;

    private String type;

    private String url;

    private String version;

    private List<ResourceCkan> resources;

    private List<TagCkan> tags;

    private List<GroupCkan> groups;

    @JsonProperty("relationships_as_subject")
    private List<Map<String, Object>> relationshipsAsSubject;

    @JsonProperty("relationships_as_object")
    private List<Map<String, Object>> relationshipsAsObject;

}

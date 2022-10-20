package gr.athenarc.datamanagementservice.dto.ckan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrganizationCkan {

    @JsonProperty("approval_status")
    private String approvalStatus;

    private String created;

    private String description;

    @JsonProperty("display_name")
    private String displayName;

    private String id;

    @JsonProperty("image_display_url")
    private String imageDisplayUrl;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("is_organization")
    private boolean isOrganization;

    private String name;

    @JsonProperty("num_followers")
    private int numFollowers;

    @JsonProperty("package_count")
    private int packageCount;

    private String state;

    private String title;

    private String type;
}

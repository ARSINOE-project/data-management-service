package gr.athenarc.datamanagementservice.dto.ckan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GroupCkan {

    private String id;

    private String created;

    @JsonProperty("approval_status")
    private String approvalStatus;

    @JsonProperty("is_organization")
    private Boolean isOrganization;

    @JsonProperty("num_followers")
    private Integer numFollowers;

    @JsonProperty("package_count")
    private Integer packageCount;

    private String state;

    private String type;

    private String name;

    private String title;

    private String description;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("image_display_url")
    private String imageDisplayUrl;
}

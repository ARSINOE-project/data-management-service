package gr.athenarc.datamanagementservice.dto.ckan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResourceCkan {

    @JsonProperty("cache_last_updated")
    private String cacheLastUpdated;

    @JsonProperty("cache_url")
    private String cacheUrl;

    private String created;

    @JsonProperty("datastore_active")
    private String datastoreActive;

    private String description;

    private String format;

    private String hash;

    private String id;

    @JsonProperty("last_modified")
    private String lastModified;

    @JsonProperty("metadata_modified")
    private String metadataModified;

    private String mimetype;

    @JsonProperty("mimetype_inner")
    private String mimetypeInner;

    private String name;

    @JsonProperty("package_id")
    private String packageId;

    private String position;

    @JsonProperty("resource_type")
    private String resourceType;

    private Integer size;

    private String state;

    private String url;

    @JsonProperty("url_type")
    private String urlType;
}

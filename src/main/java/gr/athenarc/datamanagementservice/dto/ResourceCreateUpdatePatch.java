package gr.athenarc.datamanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResourceCreateUpdatePatch {

    // The id field will only be used in case of a patch request
    // Otherwise it must be null and will not be serialized when sending
    // the request to the server
    private String id;

    @SerializedName("dataset_id")
    private String datasetId;

    private String url;

    private String description;

    private String format;

    private String name;

    public Map<String, String> toMap(boolean keepNulls) {
        if(keepNulls) return toMapKeepNulls();
        else return toMapNoNulls();
    }

    private Map<String, String> toMapKeepNulls() {
        Map<String, String> map = new HashMap<>();

        map.put("id", id);
        map.put("package_id", datasetId);
        // This must be checked for null regardless, exclusivity between url and document
        if(url != null) {
            map.put("url", url);
        }
        map.put("description", description);
        map.put("format", format);
        map.put("name", name);
        return map;
    }

    private Map<String, String> toMapNoNulls() {
        Map<String, String> map = new HashMap<>();

        if(id != null) map.put("id", id);
        if(datasetId != null) map.put("package_id", datasetId);
        if(url != null) map.put("url", url);
        if(description != null) map.put("description", description);
        if(format != null) map.put("format", format);
        if(name != null) map.put("name", name);
        return map;
    }
}

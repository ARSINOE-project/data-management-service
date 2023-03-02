package gr.athenarc.datamanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NewResource {

    @SerializedName("dataset_id")
    private String datasetId;

    private String url;

    private String description;

    private String format;

    private String name;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("package_id", datasetId);
        if(url != null) {
            map.put("url", url);
        }
        map.put("description", description);
        map.put("format", format);
        map.put("name", name);
        System.out.println(map);
        return map;
    }
}

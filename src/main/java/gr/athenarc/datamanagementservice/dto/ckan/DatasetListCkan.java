package gr.athenarc.datamanagementservice.dto.ckan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DatasetListCkan {

    @JsonProperty("organization_found")
    private Boolean organizationFound;

    private int count;

    private Map<String, Object> facets;

    private List<DatasetCkan> results;

    private String sort;

    @JsonProperty("search_facets")
    private Map<String, Object> searchFacets;
}

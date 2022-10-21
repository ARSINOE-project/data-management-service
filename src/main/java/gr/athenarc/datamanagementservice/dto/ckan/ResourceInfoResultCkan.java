package gr.athenarc.datamanagementservice.dto.ckan;

import lombok.Data;

@Data
public class ResourceInfoResultCkan {

    private String help;

    private Boolean success;

    private ResourceCkan result;

}

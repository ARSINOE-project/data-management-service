package gr.athenarc.datamanagementservice.dto.ckan;

import lombok.Data;

@Data
public class ResourceDeleteResultCkan {

    private String help;

    private Boolean success;

    private Object result;
}

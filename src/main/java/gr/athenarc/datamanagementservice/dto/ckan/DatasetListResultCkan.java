package gr.athenarc.datamanagementservice.dto.ckan;

import lombok.Data;

@Data
public class DatasetListResultCkan {

    private String help;

    private Boolean success;

    private DatasetListCkan result;

    private ErrorCkan error;
}

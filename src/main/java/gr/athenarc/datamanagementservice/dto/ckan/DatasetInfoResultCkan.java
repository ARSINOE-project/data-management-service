package gr.athenarc.datamanagementservice.dto.ckan;

import lombok.Data;

@Data
public class DatasetInfoResultCkan {

    private String help;

    private Boolean success;

    private DatasetCkan result;

    private ErrorCkan error;
}

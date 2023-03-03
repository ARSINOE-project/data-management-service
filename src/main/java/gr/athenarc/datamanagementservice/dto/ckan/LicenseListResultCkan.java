package gr.athenarc.datamanagementservice.dto.ckan;

import gr.athenarc.datamanagementservice.dto.License;
import lombok.Data;

import java.util.List;

@Data
public class LicenseListResultCkan {

    private String help;

    private Boolean success;

    private List<License> result;

    private ErrorCkan error;
}

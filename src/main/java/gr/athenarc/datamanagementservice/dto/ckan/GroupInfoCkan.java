package gr.athenarc.datamanagementservice.dto.ckan;

import lombok.Data;

@Data
public class GroupInfoCkan {

    private String help;

    private Boolean success;

    private GroupCkan result;
}

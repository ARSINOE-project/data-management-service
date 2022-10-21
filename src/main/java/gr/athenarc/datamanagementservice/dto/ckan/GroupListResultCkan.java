package gr.athenarc.datamanagementservice.dto.ckan;

import lombok.Data;

import java.util.List;

@Data
public class GroupListResultCkan {

    private String help;

    private Boolean success;

    private List<GroupCkan> result;
}

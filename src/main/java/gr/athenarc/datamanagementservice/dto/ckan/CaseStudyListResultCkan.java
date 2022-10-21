package gr.athenarc.datamanagementservice.dto.ckan;

import lombok.Data;

import java.util.List;

@Data
public class CaseStudyListResultCkan {

    private String help;

    private Boolean success;

    private List<CaseStudyCkan> result;

    private ErrorCkan error;
}

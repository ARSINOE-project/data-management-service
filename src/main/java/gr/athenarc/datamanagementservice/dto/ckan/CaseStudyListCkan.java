package gr.athenarc.datamanagementservice.dto.ckan;

import lombok.Data;

import java.util.List;

@Data
public class CaseStudyListCkan {

    private String help;
    private boolean success;
    private List<CaseStudyCkan> result;
}

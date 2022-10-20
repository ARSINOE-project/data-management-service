package gr.athenarc.datamanagementservice.util;

import gr.athenarc.datamanagementservice.dto.CaseStudy;
import gr.athenarc.datamanagementservice.dto.ckan.CaseStudyCkan;

public class DTOConverter {

    public static CaseStudy convert(CaseStudyCkan csc) {

        CaseStudy cs = new CaseStudy();
        cs.setId(csc.getId());
        cs.setTitle(csc.getTitle());
        cs.setCreated(csc.getCreated());
        cs.setDescription(csc.getDescription());
        cs.setName(csc.getName());
        cs.setImageUrl(csc.getImageUrl());
        return cs;
    }
}

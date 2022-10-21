package gr.athenarc.datamanagementservice.util;

import gr.athenarc.datamanagementservice.dto.CaseStudy;
import gr.athenarc.datamanagementservice.dto.Dataset;
import gr.athenarc.datamanagementservice.dto.Group;
import gr.athenarc.datamanagementservice.dto.Resource;
import gr.athenarc.datamanagementservice.dto.ckan.CaseStudyCkan;
import gr.athenarc.datamanagementservice.dto.ckan.DatasetCkan;
import gr.athenarc.datamanagementservice.dto.ckan.GroupCkan;
import gr.athenarc.datamanagementservice.dto.ckan.ResourceCkan;

import java.util.stream.Collectors;

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

    public static Dataset convert(DatasetCkan dc) {

        Dataset d = new Dataset();
        d.setId(dc.getId());
        d.setTitle(dc.getTitle());
        d.setName(dc.getName());
        d.setDescription(dc.getNotes());
        d.setLicenseId(dc.getLicenseId());
        d.setLicenseTitle(dc.getLicenseTitle());
        d.setLicenseUrl(dc.getLicenseUrl());
        d.setPrivate(dc.getIsPrivate());
        d.setCaseStudyId(dc.getOwnerOrg());
        d.setNumResources(dc.getNumResources());
        d.setPublicationDate(dc.getPublicationDate());
        d.setAuthor(dc.getAuthor());
        d.setAuthorEmail(dc.getAuthorEmail());
        d.setMaintainer(dc.getMaintainer());
        d.setMaintainerEmail(dc.getMaintainerEmail());
        d.setDoi(dc.getDoi());
        d.setOrigin(dc.getOrigin());
        d.setDatasetType(dc.getDatasetType());
        d.setResources(dc.getResources().stream().map(DTOConverter::convert).collect(Collectors.toList()));
        return d;
    }

    public static Resource convert(ResourceCkan rc) {

        Resource r = new Resource();
        r.setId(rc.getId());
        r.setCreated(rc.getCreated());
        r.setDescription(rc.getDescription());
        r.setFormat(rc.getFormat());
        r.setLastModified(rc.getLastModified());
        r.setName(rc.getName());
        r.setSize(rc.getSize());
        r.setUrl(rc.getUrl());
        r.setResourceType(rc.getResourceType());
        return r;
    }

    public static Group convert(GroupCkan gc) {
        Group g = new Group();
        g.setId(gc.getId());
        g.setCreated(gc.getCreated());
        g.setTitle(gc.getTitle());
        g.setDescription(gc.getDescription());
        g.setImageUrl(gc.getImageUrl());
        g.setNumberOfDatasets(gc.getPackageCount());
        return g;
    }
}

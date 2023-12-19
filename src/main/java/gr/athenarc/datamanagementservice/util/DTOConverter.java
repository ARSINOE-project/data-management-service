package gr.athenarc.datamanagementservice.util;

import gr.athenarc.datamanagementservice.dto.*;
import gr.athenarc.datamanagementservice.dto.ckan.*;

import java.util.Objects;
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
        d.setResourceType(dc.getResourceType());
        d.setDatasetType(dc.getDatasetType());
        d.setAuthorEmail(dc.getAuthorEmail());
        d.setMaintainer(dc.getMaintainer());
        d.setMaintainerEmail(dc.getMaintainerEmail());
        d.setLicenseId(dc.getLicenseId());
        d.setLicenseTitle(dc.getLicenseTitle());
        d.setLicenseUrl(dc.getLicenseUrl());
        d.setVisibility(dc.getVisibilityArsinoe());
        d.setZenodoPublish(dc.getZenodoPublish());
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
        d.setTags(dc.getTags().stream().map(TagCkan::getName).collect(Collectors.toList()));
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

    public static DatasetCreateUpdatePatchCkan convert(DatasetCreateUpdatePatch nd, boolean noNulls) {
        DatasetCreateUpdatePatchCkan ndc;
        if(noNulls) ndc = new DatasetCreateUpdatePatchNoNullsCkan();
        else ndc = new DatasetCreateUpdatePatchCkan();
        ndc.setId(nd.getId());
        ndc.setCaseStudy(nd.getCaseStudy());
        ndc.setAuthor(nd.getAuthor());
        ndc.setAuthorEmail(nd.getAuthorEmail());
        ndc.setMaintainer(nd.getMaintainer());
        ndc.setMaintainerEmail(nd.getMaintainerEmail());
        ndc.setNotes(nd.getDescription());
        ndc.setDoi(nd.getDoi());
        ndc.setDatasetType(nd.getDatasetType());
        ndc.setOrigin(nd.getOrigin());
        ndc.setResourceType(nd.getResourceType());
        ndc.setVisibilityArsinoe(nd.getVisibility());
        ndc.setZenodoPublish(nd.getZenodoPublish());
        ndc.setTitle(nd.getTitle());
        ndc.setName(nd.getName());
        ndc.setLicenseId(nd.getLicenseId());
        ndc.setPublicationDate(nd.getPublicationDate());
        if(nd.getTags() != null) {
            ndc.setTags(nd.getTags().stream().map(TagForNewDatasetCkan::new).collect(Collectors.toList()));
        }

        return ndc;
    }
}

package gr.athenarc.datamanagementservice.controller;

import gr.athenarc.datamanagementservice.dto.CaseStudy;
import gr.athenarc.datamanagementservice.dto.Dataset;
import gr.athenarc.datamanagementservice.dto.Group;
import gr.athenarc.datamanagementservice.dto.Resource;
import gr.athenarc.datamanagementservice.exception.ResourceNotFoundException;
import gr.athenarc.datamanagementservice.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class HandleRequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/list-case-studies")
    public ResponseEntity<List<CaseStudy>> listCaseStudies(HttpServletRequest request) {
        return new ResponseEntity<>(requestService.listCaseStudies(request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }

    @GetMapping("/list-datasets")
    public ResponseEntity<List<Dataset>> listDatasets(@RequestParam(name = "case_study", required = false) String caseStudyId, HttpServletRequest request) {
        return new ResponseEntity<>(requestService.listDatasets(caseStudyId, request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }

    @GetMapping("/dataset-info")
    public ResponseEntity<Dataset> datasetInfo(@RequestParam("dataset_id") String datasetId, HttpServletRequest request) {
        return new ResponseEntity<>(requestService.getDatasetInfo(datasetId, request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }

    @GetMapping("/resource-info")
    public ResponseEntity<Resource> resourceInfo(@RequestParam("resource_id") String resourceId, HttpServletRequest request) {
        return new ResponseEntity<>(requestService.getResourceInfo(resourceId, request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }

    @GetMapping("/download-resource")
    public ResponseEntity<Void> downloadResource(@RequestParam("resource_id") String resourceId, HttpServletRequest request) {
        Resource r = requestService.getResourceInfo(resourceId, request.getHeader(HttpHeaders.AUTHORIZATION));

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(r.getUrl()))
                .build();
    }

    @GetMapping("/list-groups")
    public ResponseEntity<List<Group>> listGroups(HttpServletRequest request) {
        return new ResponseEntity<>(requestService.listGroups(request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }

    @GetMapping("/group-info")
    public ResponseEntity<Group> groupInfo(@RequestParam("group_id") String groupId, HttpServletRequest request) {
        return new ResponseEntity<>(requestService.getGroupInfo(groupId, request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }

    @GetMapping("/list-datasets-per-group")
    public ResponseEntity<List<Dataset>> listDatasetsPerGroup(@RequestParam("group_id") String groupId, HttpServletRequest request) {
        return new ResponseEntity<>(requestService.listDatasetsPerGroup(groupId, request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }
}

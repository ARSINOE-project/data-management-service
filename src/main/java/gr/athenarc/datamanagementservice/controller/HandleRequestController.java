package gr.athenarc.datamanagementservice.controller;

import gr.athenarc.datamanagementservice.dto.*;
import gr.athenarc.datamanagementservice.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public ResponseEntity<List<Dataset>> listDatasets(
            @RequestParam(name = "case_study_id", required = false) String caseStudyId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            HttpServletRequest request) {
        return new ResponseEntity<>(requestService.listDatasets(caseStudyId, page, request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
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

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
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

    @GetMapping("/list-licenses")
    public ResponseEntity<List<License>> listLicenses(HttpServletRequest request) {
        return new ResponseEntity<>(requestService.listLicenses(request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }

    @PostMapping("/create-dataset")
    public ResponseEntity<Dataset> createDataset(@RequestBody NewDataset newDataset, HttpServletRequest request) {
        return new ResponseEntity<>(requestService.upsertDataset(newDataset, null, request.getHeader(HttpHeaders.AUTHORIZATION), false), HttpStatus.CREATED);
    }

    @PutMapping("/update-dataset")
    public ResponseEntity<Dataset> updateDataset(@RequestBody UpdateDataset updateDataset, HttpServletRequest request) {
        return new ResponseEntity<>(requestService.upsertDataset(null, updateDataset, request.getHeader(HttpHeaders.AUTHORIZATION), true), HttpStatus.CREATED);
    }

    @PostMapping(path = "/create-resource", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Resource> createResource(@RequestParam("resource") NewResource newResource, @RequestParam(value = "file", required = false) MultipartFile document, HttpServletRequest request) throws IOException {
        boolean hasUrl = newResource.getUrl() != null;
        boolean hasFile = document != null;

        // Must provide exactly one of [newResource.url, document]
        if((hasUrl && hasFile) || (!hasUrl && !hasFile)) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(requestService.createResource(newResource, document, request.getHeader(HttpHeaders.AUTHORIZATION)), HttpStatus.OK);
    }
}

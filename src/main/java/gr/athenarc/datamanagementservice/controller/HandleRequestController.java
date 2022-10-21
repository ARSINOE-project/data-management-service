package gr.athenarc.datamanagementservice.controller;

import gr.athenarc.datamanagementservice.dto.CaseStudy;
import gr.athenarc.datamanagementservice.dto.Dataset;
import gr.athenarc.datamanagementservice.dto.Group;
import gr.athenarc.datamanagementservice.dto.Resource;
import gr.athenarc.datamanagementservice.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class HandleRequestController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.ckan-base-uri}")
    private String baseUri;

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
}

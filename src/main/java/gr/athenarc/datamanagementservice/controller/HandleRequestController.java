package gr.athenarc.datamanagementservice.controller;

import gr.athenarc.datamanagementservice.dto.*;
import gr.athenarc.datamanagementservice.service.RequestService;
import gr.athenarc.datamanagementservice.util.FilterFields;
import gr.athenarc.datamanagementservice.util.SolrQueryTerm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@Validated
public class HandleRequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/list-case-studies")
    public ResponseEntity<List<CaseStudy>> listCaseStudies(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth
    ) {
        return new ResponseEntity<>(requestService.listCaseStudies(auth), HttpStatus.OK);
    }

    @GetMapping("/list-datasets")
    public ResponseEntity<PageResponse<List<Dataset>>> listDatasets(
            @RequestParam(name = "case_study_id", required = false) String caseStudyId,
            @RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return new ResponseEntity<>(requestService.listDatasets(caseStudyId, page, auth), HttpStatus.OK);
    }

    @GetMapping("/search-datasets")
    public ResponseEntity<PageResponse<List<Dataset>>> searchDatasets(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "operator", defaultValue = "and") String operator,
            @RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        // If operator or page were provided, they are captured in their
        // respective arguments and are not needed in the map
        // along with the rest of the filter field parameters
        params.remove("operator");
        params.remove("page");

        Operator operatorEnum;
        try {
            operatorEnum = Operator.valueOf(operator);
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        List<SolrQueryTerm> solrQueryTerms = FilterFields.prepareFilterFieldsMap(params);
        String solrQuery = FilterFields.generateSolrQuery(solrQueryTerms, operatorEnum);
        return new ResponseEntity<>(requestService.searchDatasets(solrQuery, page, auth), HttpStatus.OK);
    }

    @GetMapping("/dataset-info")
    public ResponseEntity<Dataset> datasetInfo(
            @RequestParam("dataset_id") String datasetId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return new ResponseEntity<>(requestService.getDatasetInfo(datasetId, auth), HttpStatus.OK);
    }

    @GetMapping("/resource-info")
    public ResponseEntity<Resource> resourceInfo(
            @RequestParam("resource_id") String resourceId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return new ResponseEntity<>(requestService.getResourceInfo(resourceId, auth), HttpStatus.OK);
    }

    @GetMapping("/download-resource")
    public ResponseEntity<Void> downloadResource(
            @RequestParam("resource_id") String resourceId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        Resource r = requestService.getResourceInfo(resourceId, auth);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(r.getUrl()))
                .build();
    }

    @GetMapping("/list-groups")
    public ResponseEntity<List<Group>> listGroups(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth
    ) {
        return new ResponseEntity<>(requestService.listGroups(auth), HttpStatus.OK);
    }

    @GetMapping("/group-info")
    public ResponseEntity<Group> groupInfo(
            @RequestParam("group_id") String groupId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return new ResponseEntity<>(requestService.getGroupInfo(groupId, auth), HttpStatus.OK);
    }

    @GetMapping("/list-datasets-per-group")
    public ResponseEntity<List<Dataset>> listDatasetsPerGroup(
            @RequestParam("group_id") String groupId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return new ResponseEntity<>(requestService.listDatasetsPerGroup(groupId, auth), HttpStatus.OK);
    }

    @GetMapping("/list-licenses")
    public ResponseEntity<List<License>> listLicenses(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth
    ) {
        return new ResponseEntity<>(requestService.listLicenses(auth), HttpStatus.OK);
    }

    @GetMapping("/list-dataset-origins")
    public ResponseEntity<List<String>> listDatasetOrigins(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth
    ) {
        return new ResponseEntity<>(requestService.listDatasetOrigins(), HttpStatus.OK);
    }

    @GetMapping("/list-dataset-resource-types")
    public ResponseEntity<List<String>> listDatasetResourceTypes(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth
    ) {
        return new ResponseEntity<>(requestService.listDatasetResourceTypes(), HttpStatus.OK);
    }

    @GetMapping("/list-dataset-types")
    public ResponseEntity<List<String>> listDatasetTypes(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth
    ) {
        return new ResponseEntity<>(requestService.listDatasetTypes(), HttpStatus.OK);
    }

    @PostMapping("/create-dataset")
    public ResponseEntity<Dataset> createDataset(
            @RequestBody DatasetCreateUpdatePatch datasetCreateUpdatePatch,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return new ResponseEntity<>(requestService.upsertDataset(datasetCreateUpdatePatch, auth, HttpMethod.POST), HttpStatus.CREATED);
    }

    @PutMapping("/update-dataset")
    public ResponseEntity<Dataset> updateDataset(
            @RequestBody DatasetCreateUpdatePatch datasetCreateUpdatePatch,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return new ResponseEntity<>(requestService.upsertDataset(datasetCreateUpdatePatch, auth, HttpMethod.PUT), HttpStatus.CREATED);
    }

    @PatchMapping("/patch-dataset")
    public ResponseEntity<Dataset> patchDataset(
            @RequestBody DatasetCreateUpdatePatch datasetCreateUpdatePatch,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return new ResponseEntity<>(requestService.upsertDataset(datasetCreateUpdatePatch, auth, HttpMethod.PATCH), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-dataset")
    public ResponseEntity<Void> deleteDataset(
            @RequestParam("dataset_id") String datasetId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        requestService.deleteDataset(datasetId, auth);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/create-resource", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Resource> createResource(
            @RequestParam("resource") ResourceCreateUpdatePatch resourceCreateUpdatePatch,
            @RequestParam(value = "file", required = false) MultipartFile document,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        boolean hasUrl = resourceCreateUpdatePatch.getUrl() != null;
        boolean hasFile = document != null;

        // Must provide exactly one of [newResource.url, document]
        if((hasUrl && hasFile) || (!hasUrl && !hasFile)) {
            return ResponseEntity.badRequest().build();
        }

        byte[] fileBytes = null;
        String fileName = null;
        if(hasFile) {
            try {
                fileBytes = document.getBytes();
                fileName = document.getOriginalFilename();
            }
            catch(IOException e) {
                return ResponseEntity.badRequest().build();
            }

        }

        return new ResponseEntity<>(requestService.upsertResource(resourceCreateUpdatePatch, fileBytes, fileName, auth, HttpMethod.POST), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update-resource", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Resource> updateResource(
            @RequestParam("resource") ResourceCreateUpdatePatch resourceCreateUpdatePatch,
            @RequestParam(value = "file", required = false) MultipartFile document,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        boolean hasUrl = resourceCreateUpdatePatch.getUrl() != null;
        boolean hasFile = document != null;

        // Must provide exactly one of [newResource.url, document]
        if((hasUrl && hasFile) || (!hasUrl && !hasFile)) {
            return ResponseEntity.badRequest().build();
        }

        byte[] fileBytes = null;
        String fileName = null;
        if(hasFile) {
            try {
                fileBytes = document.getBytes();
                fileName = document.getOriginalFilename();
            }
            catch(IOException e) {
                return ResponseEntity.badRequest().build();
            }

        }

        return new ResponseEntity<>(requestService.upsertResource(resourceCreateUpdatePatch, fileBytes, fileName, auth, HttpMethod.PUT), HttpStatus.OK);
    }

    @PatchMapping(path = "/patch-resource", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Resource> patchResource(
            @RequestParam("resource") ResourceCreateUpdatePatch resourceCreateUpdatePatch,
            @RequestParam(value = "file", required = false) MultipartFile document,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {

        boolean hasUrl = resourceCreateUpdatePatch.getUrl() != null;
        boolean hasFile = document != null;

        // Must provide exactly one of [newResource.url, document] only if at least one was provided!, not the same case as create/update
        // Because in patch both could be omitted!
        if((hasUrl && hasFile)) {
            return ResponseEntity.badRequest().build();
        }

        byte[] fileBytes = null;
        String fileName = null;
        if(hasFile) {
            try {
                fileBytes = document.getBytes();
                fileName = document.getOriginalFilename();
            }
            catch(IOException e) {
                return ResponseEntity.badRequest().build();
            }

        }

        return new ResponseEntity<>(requestService.upsertResource(resourceCreateUpdatePatch, fileBytes, fileName, auth, HttpMethod.PATCH), HttpStatus.OK);
    }

    @DeleteMapping("/delete-resource")
    public ResponseEntity<Void> deleteResource(
            @RequestParam("resource_id") String resourceId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        requestService.deleteResource(resourceId, auth);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package gr.athenarc.datamanagementservice.service;

import com.google.gson.Gson;
import gr.athenarc.datamanagementservice.dto.*;
import gr.athenarc.datamanagementservice.dto.ckan.*;
import gr.athenarc.datamanagementservice.exception.*;
import gr.athenarc.datamanagementservice.util.DTOConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Value("${app.page-size}")
    private int pageSize;

    @Value("${app.ckan-base-uri}")
    private String baseUri;

    @Value("${app.ckan-list-case-studies-uri}")
    private String listCaseStudiesUri;

    @Value("${app.ckan-list-datasets-uri}")
    private String listDatasetsUri;

    @Value("${app.ckan-search-datasets-uri}")
    private String searchDatasetsUri;

    @Value("${app.ckan-get-dataset-info-uri}")
    private String getDatasetInfoUri;

    @Value("${app.ckan-get-resource-info-uri}")
    private String getResourceInfoUri;

    @Value("${app.ckan-list-groups-uri}")
    private String listGroupsUri;

    @Value("${app.ckan-get-group-info-uri}")
    private String groupInfoUri;

    @Value("${app.ckan-list-datasets-per-group-uri}")
    private String listDatasetsPerGroupUri;

    @Value("${app.ckan-list-licenses}")
    private String listLicensesUri;

    @Value("${app.ckan-create-dataset}")
    private String createDatasetUri;

    @Value("${app.ckan-update-dataset}")
    private String updateDatasetUri;

    @Value("${app.ckan-patch-dataset}")
    private String patchDatasetUri;

    @Value("${app.ckan-delete-dataset}")
    private String deleteDatasetUri;

    @Value("${app.ckan-create-resource}")
    private String createResourceUri;

    @Value("${app.ckan-update-resource}")
    private String updateResourceUri;

    @Value("${app.ckan-patch-resource}")
    private String patchResourceUri;

    @Value("${app.ckan-delete-resource}")
    private String deleteResourceUri;

    public List<CaseStudy> listCaseStudies(String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + listCaseStudiesUri)
                .queryParam("all_fields", "{all_fields}")
                .encode()
                .toUriString();

        // Create params map
        Map<String, String> params = new HashMap<>();
        params.put("all_fields", "true");

        // API call
        ResponseEntity<CaseStudyListResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), CaseStudyListResultCkan.class, params);

        return response.getBody().getResult().stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    @AllArgsConstructor
    private static class PageInfo {
        public int rows;
        public int start;
    }

    private PageInfo getPageInfo(int page) {
        return new PageInfo(pageSize, page*pageSize);
    }

    public PageResponse<List<Dataset>> listDatasets(String caseStudyId, int page, String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL and params map
        String urlTemplate;
        Map<String, String> params = new HashMap<>();

        PageInfo pageInfo = getPageInfo(page);
        params.put("start", Integer.toString(pageInfo.start));
        params.put("rows", Integer.toString(pageInfo.rows));

        if(ObjectUtils.isEmpty(caseStudyId)) {
            urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + listDatasetsUri)
                    .queryParam("start", "{start}")
                    .queryParam("rows", "{rows}")
                    .encode()
                    .toUriString();
        }
        else {
            urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + listDatasetsUri)
                    .queryParam("organization_id", "{organization_id}")
                    .queryParam("start", "{start}")
                    .queryParam("rows", "{rows}")
                    .encode()
                    .toUriString();
            params.put("organization_id", caseStudyId);
        }

        // API call
        ResponseEntity<DatasetListResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), DatasetListResultCkan.class, params);

        Boolean orgFound = response.getBody().getResult().getOrganizationFound();
        if(orgFound != null && !orgFound) {
            throw new CaseStudyNotFoundException(caseStudyId);
        }

        DatasetListCkan dlc = response.getBody().getResult();
        List<Dataset> results = dlc.getResults().stream().map(DTOConverter::convert).collect(Collectors.toList());

        Page pageObj = new Page();
        pageObj.setNumber(page);
        pageObj.setSize(results.size());
        pageObj.setTotalElements(dlc.getCount());
        int totalPages = (int) Math.ceil( (double) dlc.getCount()/pageSize);
        pageObj.setTotalPages(totalPages);

        PageResponse<List<Dataset>> ret = new PageResponse<>();
        ret.setResults(results);
        ret.setPageMetadata(pageObj);

        return ret;
    }

    public PageResponse<List<Dataset>> searchDatasets(String solrQuery, int page, String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + searchDatasetsUri)
                .queryParam("q", "{q}")
                .queryParam("include_private", "{include_private}")
                .queryParam("start", "{start}")
                .queryParam("rows", "{rows}")
                .encode()
                .toUriString();

        // Create params map
        Map<String, String> params = new HashMap<>();
        params.put("q", solrQuery);
        params.put("include_private", "true");

        PageInfo pageInfo = getPageInfo(page);
        params.put("start", Integer.toString(pageInfo.start));
        params.put("rows", Integer.toString(pageInfo.rows));

        // API call
        ResponseEntity<DatasetListResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), DatasetListResultCkan.class, params);

        DatasetListCkan dlc = response.getBody().getResult();
        List<Dataset> results = dlc.getResults().stream().map(DTOConverter::convert).collect(Collectors.toList());

        Page pageObj = new Page();
        pageObj.setNumber(page);
        pageObj.setSize(results.size());
        pageObj.setTotalElements(dlc.getCount());
        int totalPages = (int) Math.ceil( (double) dlc.getCount()/pageSize);
        pageObj.setTotalPages(totalPages);

        PageResponse<List<Dataset>> ret = new PageResponse<>();
        ret.setResults(results);
        ret.setPageMetadata(pageObj);

        return ret;
    }

    public Dataset getDatasetInfo(String datasetId, String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + getDatasetInfoUri)
                .queryParam("id", "{id}")
                .encode()
                .toUriString();

        // Create params map
        Map<String, String> params = new HashMap<>();
        params.put("id", datasetId);

        // API call
        ResponseEntity<DatasetInfoResultCkan> response;

        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), DatasetInfoResultCkan.class, params);
        }
        catch(RestClientResponseException e) {
            if(e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new DatasetNotFoundException(datasetId);
            }
            throw e;
        }

        return DTOConverter.convert(response.getBody().getResult());
    }

    public Resource getResourceInfo(String resourceId, String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + getResourceInfoUri)
                .queryParam("id", "{id}")
                .encode()
                .toUriString();

        // Create params map
        Map<String, String> params = new HashMap<>();
        params.put("id", resourceId);

        // API call
        ResponseEntity<ResourceInfoResultCkan> response;

        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), ResourceInfoResultCkan.class, params);
        }
        catch(RestClientResponseException e) {
            if(e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new ResourceNotFoundException(resourceId);
            }
            throw e;
        }

        return DTOConverter.convert(response.getBody().getResult());
    }

    public List<Group> listGroups(String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + listGroupsUri)
                .queryParam("all_fields", "{all_fields}")
                .encode()
                .toUriString();

        // Create params map
        Map<String, String> params = new HashMap<>();
        params.put("all_fields", "true");

        // API call
        ResponseEntity<GroupListResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), GroupListResultCkan.class, params);

        return response.getBody().getResult().stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public Group getGroupInfo(String groupId, String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + groupInfoUri)
                .queryParam("id", "{id}")
                .queryParam("include_users", "{include_users}")
                .queryParam("include_extras", "{include_extras}")
                .queryParam("include_tags", "{include_tags}")
                .queryParam("include_groups", "{include_groups}")
                .encode()
                .toUriString();

        // Create params map
        Map<String, String> params = new HashMap<>();
        params.put("id", groupId);
        params.put("include_users", "false");
        params.put("include_extras", "false");
        params.put("include_tags", "false");
        params.put("include_groups", "false");


        // API call
        ResponseEntity<GroupInfoCkan> response;

        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), GroupInfoCkan.class, params);
        }
        catch(RestClientResponseException e) {
            if(e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new GroupNotFoundException(groupId);
            }
            throw e;
        }

        return DTOConverter.convert(response.getBody().getResult());
    }

    public List<Dataset> listDatasetsPerGroup(String groupId, String auth) {

        // Make sure that the group exists
        Group group = getGroupInfo(groupId, auth);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + listDatasetsPerGroupUri)
                .queryParam("group_id", "{group_id}")
                .encode()
                .toUriString();

        // Create params map
        Map<String, String> params = new HashMap<>();
        params.put("group_id", groupId);

        // API call
        ResponseEntity<DatasetListResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), DatasetListResultCkan.class, params);

        return response.getBody().getResult().getResults().stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public List<License> listLicenses(String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + listLicensesUri).encode().toUriString();

        // API call
        ResponseEntity<LicenseListResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), LicenseListResultCkan.class);

        return response.getBody().getResult();
    }

    private final List<String> datasetOrigins =
            List.of("unknown", "primary", "secondary");
    public List<String> listDatasetOrigins() {
        return datasetOrigins;
    }

    private final List<String> datasetResourceTypes =
            List.of("model", "software", "sensor", "observational", "report", "images", "formulas", "statistical");

    public List<String> listDatasetResourceTypes() {
        return datasetResourceTypes;
    }

    private final List<String> datasetTypes =
            List.of("textual", "geospatial", "satellite_images", "tabular", "video", "scripts");

    public List<String> listDatasetTypes() {
        return datasetTypes;
    }

    public Dataset upsertDataset(DatasetCreateUpdatePatch datasetCreateUpdatePatch, String auth, HttpMethod httpMethod) {

        if(httpMethod.equals(HttpMethod.POST)) {
            // Ignore the id in case it was sent when creating
            datasetCreateUpdatePatch.setId(null);
            // Set null tags to empty array
            if(datasetCreateUpdatePatch.getTags() == null) {
                datasetCreateUpdatePatch.setTags(new ArrayList<>());
            }
        }
        else {
            // Make sure the dataset exists
            Dataset dataset = getDatasetInfo(datasetCreateUpdatePatch.getId(), auth);
        }

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String uri;
        if(httpMethod.equals(HttpMethod.POST)) uri = createDatasetUri;
        else if(httpMethod.equals(HttpMethod.PUT)) uri = updateDatasetUri;
        else if(httpMethod.equals(HttpMethod.PATCH)) uri = patchDatasetUri;
        else throw new RuntimeException("httpMethod: Was expecting one of: POST, PUT, PATCH");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + uri).encode().toUriString();

        DatasetCreateUpdatePatchCkan datasetCreateUpdatePatchCkan = DTOConverter.convert(datasetCreateUpdatePatch, httpMethod.equals(HttpMethod.PATCH));

        // API call
        ResponseEntity<DatasetInfoResultCkan> response;
        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.POST, new HttpEntity<>(datasetCreateUpdatePatchCkan, headers), DatasetInfoResultCkan.class);
        } catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == HttpStatus.CONFLICT.value() || e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()) {
                Map<String, Object> errorResponse = gson.fromJson(e.getResponseBodyAsString(), Map.class);
                Map<String, Object> errors = (Map<String, Object>) errorResponse.get("error");
                errors.remove("__type");
                throw new NewDatasetCreateException(errors);
            }
            throw e;
        }

        return DTOConverter.convert(response.getBody().getResult());
    }

    public void deleteDataset(String datasetId, String auth) {

        // Make sure the dataset exists
        Dataset dataset = getDatasetInfo(datasetId, auth);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + deleteDatasetUri)
                .encode()
                .toUriString();

        // API call
        ResponseEntity<DatasetDeleteResultCkan> response;
        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.POST, new HttpEntity<>(new DatasetDeleteCkan(datasetId), headers), DatasetDeleteResultCkan.class);
        }
        catch(RestClientResponseException e) {
            if(e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new DatasetNotFoundException(datasetId);
            }
            throw e;
        }
    }

    public Resource upsertResource(ResourceCreateUpdatePatch resourceCreateUpdatePatch, byte[] fileBytes, String fileName, String auth, HttpMethod httpMethod) {

        if(httpMethod.equals(HttpMethod.POST)) {
            // Ignore the id in case it was sent when creating
            resourceCreateUpdatePatch.setId(null);
        }
        else {
            // Make sure the resource exists
            Resource resource = getResourceInfo(resourceCreateUpdatePatch.getId(), auth);
        }

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        if(fileBytes != null) {
            // This nested HttpEntity is important to create the correct
            // Content-Disposition entry with metadata "name" and "filename"
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("form-data")
                    .name("upload")
                    .filename(fileName)
                    .build();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(fileBytes, fileMap);
            body.add("upload", fileEntity);
        }

        Map<String, String> resourceMap = resourceCreateUpdatePatch.toMap(!httpMethod.equals(HttpMethod.PATCH));
        for(Map.Entry<String, String> entry: resourceMap.entrySet()) {
            body.add(entry.getKey(), entry.getValue());
        }

        // Build URL
        String uri;
        if(httpMethod.equals(HttpMethod.POST)) uri = createResourceUri;
        else if(httpMethod.equals(HttpMethod.PUT)) uri = updateResourceUri;
        else if(httpMethod.equals(HttpMethod.PATCH)) uri = patchResourceUri;
        else throw new RuntimeException("httpMethod: Was expecting one of: POST, PUT, PATCH");
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + uri).encode().toUriString();

        // API call
        ResponseEntity<ResourceInfoResultCkan> response;
        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.POST, new HttpEntity<>(body, headers), ResourceInfoResultCkan.class);
            return DTOConverter.convert(response.getBody().getResult());
        }
        catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
                Map<String, Object> errorResponse = gson.fromJson(e.getResponseBodyAsString(), Map.class);
                Map<String, Object> errors = (Map<String, Object>) errorResponse.get("error");
                errors.remove("__type");
                throw new NewDatasetCreateException(errors);
            }
            throw e;
        }
    }

    public void deleteResource(String resourceId, String auth) {

        // Make sure the resource exists
        Resource resource = getResourceInfo(resourceId, auth);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + deleteResourceUri)
                .encode()
                .toUriString();

        // API call
        ResponseEntity<ResourceDeleteResultCkan> response;
        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.POST, new HttpEntity<>(new ResourceDeleteCkan(resourceId), headers), ResourceDeleteResultCkan.class);
        }
        catch(RestClientResponseException e) {
            if(e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new ResourceNotFoundException(resourceId);
            }
            throw e;
        }
    }
}

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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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

    @Value("${app.ckan-create-resource}")
    private String createResourceUri;

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
    private class PageInfo {
        public int rows;
        public int start;
    }

    private PageInfo getPageInfo(int page) {
        return new PageInfo(pageSize, page*pageSize);
    }

    public List<Dataset> listDatasets(String caseStudyId, int page, String auth) {

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
        if(orgFound != null && orgFound == false) {
            throw new CaseStudyNotFoundException(caseStudyId);
        }

        return response.getBody().getResult().getResults().stream().map(DTOConverter::convert).collect(Collectors.toList());
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
        ResponseEntity<ListLicensesResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), ListLicensesResultCkan.class);

        return response.getBody().getResult();
    }

    public Dataset upsertDataset(NewDataset newDataset, UpdateDataset updateDataset, String auth, boolean update) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + (update ? updateDatasetUri : createDatasetUri)).encode().toUriString();

        NewDatasetCkan ndc = new NewDatasetCkan();
        UpdateDatasetCkan udc = new UpdateDatasetCkan();

        if(update) {
            udc = DTOConverter.convert(updateDataset);
        }
        else {
            ndc = DTOConverter.convert(newDataset);
        }

        // API call
        ResponseEntity<DatasetInfoResultCkan> response;
        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.POST, new HttpEntity<>(update ? udc : ndc, headers), DatasetInfoResultCkan.class);
            return DTOConverter.convert(response.getBody().getResult());
        } catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
                Map<String, Object> errorResponse = gson.fromJson(e.getResponseBodyAsString(), Map.class);
                Map<String, List<String>> errors = (Map<String, List<String>>) errorResponse.get("error");
                errors.remove("__type");
                throw new NewDatasetCreateException(errors);
            }
            else {
                throw new RuntimeException("Unexpected error!");
            }
        }
    }

    public Resource createResource(NewResource newResource, MultipartFile document, String auth) throws IOException {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        if(document != null) {
            // This nested HttpEntity is important to create the correct
            // Content-Disposition entry with metadata "name" and "filename"
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("form-data")
                    .name("upload")
                    .filename(document.getOriginalFilename())
                    .build();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(document.getBytes(), fileMap);
            body.add("upload", fileEntity);
        }

        Map<String, String> newResourceMap = newResource.toMap();
        for(Map.Entry<String, String> entry: newResourceMap.entrySet()) {
            body.add(entry.getKey(), entry.getValue());
        }
        System.out.println(body);

        // Build URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + createResourceUri).encode().toUriString();

        // API call
        ResponseEntity<ResourceInfoResultCkan> response;
        try {
            response = restTemplate.exchange(urlTemplate, HttpMethod.POST, new HttpEntity<>(body, headers), ResourceInfoResultCkan.class);
            return DTOConverter.convert(response.getBody().getResult());
        }
        catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == HttpStatus.CONFLICT.value()) {
                Map<String, Object> errorResponse = gson.fromJson(e.getResponseBodyAsString(), Map.class);
                Map<String, List<String>> errors = (Map<String, List<String>>) errorResponse.get("error");
                errors.remove("__type");
                throw new NewDatasetCreateException(errors);
            } else {
                e.printStackTrace();
                throw new RuntimeException("Unexpected error!");
            }
        }
    }
}

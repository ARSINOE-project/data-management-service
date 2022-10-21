package gr.athenarc.datamanagementservice.service;

import gr.athenarc.datamanagementservice.dto.CaseStudy;
import gr.athenarc.datamanagementservice.dto.Dataset;
import gr.athenarc.datamanagementservice.dto.Group;
import gr.athenarc.datamanagementservice.dto.Resource;
import gr.athenarc.datamanagementservice.dto.ckan.*;
import gr.athenarc.datamanagementservice.util.DTOConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {

    @Autowired
    private RestTemplate restTemplate;

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

    public List<Dataset> listDatasets(String caseStudyId, String auth) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);

        // Build URL and params map
        String urlTemplate;
        Map<String, String> params = new HashMap<>();

        if(ObjectUtils.isEmpty(caseStudyId)) {
            urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + listDatasetsUri)
                    .encode()
                    .toUriString();
        }
        else {
            urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + listDatasetsUri)
                    .queryParam("organization_id", "{organization_id}")
                    .encode()
                    .toUriString();
            params.put("organization_id", caseStudyId);
        }

        // API call
        ResponseEntity<DatasetListResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), DatasetListResultCkan.class, params);

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
        ResponseEntity<DatasetInfoResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), DatasetInfoResultCkan.class, params);

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
        ResponseEntity<ResourceInfoResultCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), ResourceInfoResultCkan.class, params);

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
}

package gr.athenarc.datamanagementservice.controller;

import gr.athenarc.datamanagementservice.dto.CaseStudy;
import gr.athenarc.datamanagementservice.dto.ckan.CaseStudyListCkan;
import gr.athenarc.datamanagementservice.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<Object, Object>> listDatasets(@RequestParam("case_study") String caseStudy, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorizationHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + "/organization_show")
                .queryParam("id", "{id}")
                .queryParam("include_datasets", "{include_datasets}")
                .encode()
                .toUriString();

        log.info("Case study: {}", caseStudy);
        log.info("Url template: {}", urlTemplate);

        Map<String, String> params = new HashMap<>();
        params.put("include_datasets", "True");
        params.put("id", caseStudy);

        ResponseEntity<Map<Object, Object>> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<Object, Object>>(){}, params);
        log.info("Response for organization list: " + response.getBody());
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @GetMapping("/dataset-info")
    public ResponseEntity<Map<Object, Object>> datasetInfo(@RequestParam("dataset_id") String datasetId, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorizationHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUri + "/package_show")
                .queryParam("id", "{id}")
                .queryParam("include_datasets", "{include_datasets}")
                .encode()
                .toUriString();

        log.info("Dataset ID: {}", datasetId);
        log.info("Url template: {}", urlTemplate);

        Map<String, String> params = new HashMap<>();
        params.put("include_datasets", "True");
        params.put("id", datasetId);

        ResponseEntity<Map<Object, Object>> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<Object, Object>>(){}, params);
        log.info("Response for organization list: " + response.getBody());
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}

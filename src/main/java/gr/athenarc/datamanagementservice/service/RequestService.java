package gr.athenarc.datamanagementservice.service;

import gr.athenarc.datamanagementservice.dto.CaseStudy;
import gr.athenarc.datamanagementservice.dto.ckan.CaseStudyListCkan;
import gr.athenarc.datamanagementservice.util.DTOConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

        ResponseEntity<CaseStudyListCkan> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(headers), CaseStudyListCkan.class, params);

        return response.getBody().getResult().stream().map(DTOConverter::convert).collect(Collectors.toList());
    }
}

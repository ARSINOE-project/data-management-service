package gr.athenarc.datamanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class License {

    private String id;

    @JsonProperty("domain_content")
    private String domainContent;

    @JsonProperty("domain_data")
    private String domainData;

    @JsonProperty("domain_software")
    private String domainSoftware;

    private String family;

    @JsonProperty("is_generic")
    private String isGeneric;

    @JsonProperty("od_conformance")
    private String odConformance;

    @JsonProperty("osd_conformance")
    private String osdConformance;

    private String maintainer;

    private String status;

    private String url;

    private String title;

    @JsonProperty("is_okd_compliant")
    private Boolean isOkdCompliant;

    @JsonProperty("is_osi_compliant")
    private Boolean isOsiCompliant;

}

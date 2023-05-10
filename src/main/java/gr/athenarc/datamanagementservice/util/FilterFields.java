package gr.athenarc.datamanagementservice.util;

import gr.athenarc.datamanagementservice.dto.Operator;
import gr.athenarc.datamanagementservice.exception.IllegalFilterKey;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterFields {

    private final static Map<String, String> filterFields = new HashMap<>() {{
        put("visibility", "extras_visibility_arsinoe");
        put("author", "author");
        put("dataset_type", "extras_dataset_type_arsinoe");
        put("origin", "extras_origin");
        put("resource_type", "extras_resource_type");
        put("id", "id");
        put("name", "name");
        put("case_study_name", "organization");
        put("title", "title");
        put("tags", "tags");
        put("resource_format", "res_format");
    }};

    public static List<SolrQueryTerm> prepareFilterFieldsMap(MultiValueMap<String, String> receivedFilterFields) {

        List<SolrQueryTerm> ret = new ArrayList<>();

        for(MultiValueMap.Entry<String, List<String>> entry: receivedFilterFields.entrySet()) {
            if(!filterFields.containsKey(entry.getKey())) {
                throw new IllegalFilterKey(entry.getKey());
            }

            for(String s: entry.getValue()) {
                ret.add(new SolrQueryTerm(filterFields.get(entry.getKey()), s));
            }
        }

        return ret;
    }

    public static String generateSolrQuery(List<SolrQueryTerm> terms, Operator operator) {
        List<String> queryTokens = terms.stream()
                .map(term -> ClientUtils.escapeQueryChars(term.key) + ":" + ClientUtils.escapeQueryChars(term.value))
                .collect(Collectors.toList());

        return String.join(" " + operator.getOperator() + " ", queryTokens);
    }
}

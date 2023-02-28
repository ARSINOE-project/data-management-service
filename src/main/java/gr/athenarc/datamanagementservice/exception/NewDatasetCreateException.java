package gr.athenarc.datamanagementservice.exception;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;

public class NewDatasetCreateException extends RuntimeException {

    private Map<String, List<String>> errors;

    public NewDatasetCreateException(Map<String, List<String>> errors) {
        this.errors = errors;

    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}

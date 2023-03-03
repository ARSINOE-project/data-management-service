package gr.athenarc.datamanagementservice.exception;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;

public class NewDatasetCreateException extends RuntimeException {

    private Map<String, Object> errors;

    public NewDatasetCreateException(Map<String, Object> errors) {
        this.errors = errors;

    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}

package gr.athenarc.datamanagementservice.exception;

public class DatasetNotFoundException extends RuntimeException {

    private String datasetId;

    public DatasetNotFoundException(String datasetId) {
        super(String.format("Could not find dataset with id %s", datasetId));
        this.datasetId = datasetId;
    }

    public String getDatasetId() {
        return datasetId;
    }
}


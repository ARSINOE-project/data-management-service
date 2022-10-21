package gr.athenarc.datamanagementservice.exception;

public class ResourceNotFoundException extends RuntimeException {

    private String resourceId;

    public ResourceNotFoundException(String resourceId) {
        super(String.format("Could not find resource with id %s", resourceId));
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }
}

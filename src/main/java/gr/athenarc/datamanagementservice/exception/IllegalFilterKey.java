package gr.athenarc.datamanagementservice.exception;

public class IllegalFilterKey extends RuntimeException {

    private String key;

    public IllegalFilterKey(String key) {
        super(String.format("Illegal filter key %s", key));
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

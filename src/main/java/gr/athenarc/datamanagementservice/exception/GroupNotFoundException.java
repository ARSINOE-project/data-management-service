package gr.athenarc.datamanagementservice.exception;

public class GroupNotFoundException extends RuntimeException {

    private String groupId;

    public GroupNotFoundException(String groupId) {
        super(String.format("Could not find group with id %s", groupId));
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }
}

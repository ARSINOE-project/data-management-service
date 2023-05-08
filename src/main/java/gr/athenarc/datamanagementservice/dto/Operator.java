package gr.athenarc.datamanagementservice.dto;

public enum Operator {

    or("OR"),
    and("AND");

    private String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}

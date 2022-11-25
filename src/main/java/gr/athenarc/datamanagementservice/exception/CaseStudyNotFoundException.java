package gr.athenarc.datamanagementservice.exception;

public class CaseStudyNotFoundException extends RuntimeException {

    private String caseStudyId;

    public CaseStudyNotFoundException(String caseStudyId) {
        super(String.format("Could not find case study with id %s", caseStudyId));
        this.caseStudyId = caseStudyId;
    }

    public String getCaseStudyId() {
        return caseStudyId;
    }
}

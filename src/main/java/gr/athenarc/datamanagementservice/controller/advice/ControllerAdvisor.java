package gr.athenarc.datamanagementservice.controller.advice;

import gr.athenarc.datamanagementservice.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CaseStudyNotFoundException.class)
    public ResponseEntity<Void> handleCaseStudyNotFoundException(CaseStudyNotFoundException e, WebRequest request) {
        log.info("CaseStudyNotFound occurred, resource id = {}", e.getCaseStudyId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        log.info("ResourceNotFoundException occurred, resource id = {}", e.getResourceId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(DatasetNotFoundException.class)
    public ResponseEntity<Void> handleDatasetNotFoundException(DatasetNotFoundException e, WebRequest request) {
        log.info("DatasetNotFoundException occurred, dataset id = {}", e.getDatasetId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<Void> handleGroupNotFoundException(GroupNotFoundException e, WebRequest request) {
        log.info("GroupNotFoundException occurred, group id = {}", e.getGroupId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(NewDatasetCreateException.class)
    public ResponseEntity<Map<String, Object>> handleNewDatasetCreateException(NewDatasetCreateException e, WebRequest request) {
        log.info("NewDatasetCreateException occurred");
        return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
    }
}

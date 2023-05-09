package gr.athenarc.datamanagementservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PageResponse<R> {

    private R results;

    private Page pageMetadata;
}

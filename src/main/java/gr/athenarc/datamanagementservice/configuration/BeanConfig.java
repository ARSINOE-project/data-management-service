package gr.athenarc.datamanagementservice.configuration;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@Slf4j
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate(RequestInterceptor requestInterceptor) {
        RestTemplate restTemplate = new RestTemplate();

        if(log.isDebugEnabled()) {
            restTemplate.setInterceptors(Collections.singletonList(requestInterceptor));
        }

        return restTemplate;

    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}

package gr.athenarc.datamanagementservice.configuration;

import com.google.gson.Gson;
import gr.athenarc.datamanagementservice.dto.NewResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NewResourceConverter implements Converter<String, NewResource> {

    @Autowired
    private Gson gson;

    @Override
    public NewResource convert(String source) {
        return gson.fromJson(source, NewResource.class);
    }
}

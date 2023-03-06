package gr.athenarc.datamanagementservice.configuration;

import com.google.gson.Gson;
import gr.athenarc.datamanagementservice.dto.ResourceCreateUpdatePatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ResourceCreateUpdatePatchConverter implements Converter<String, ResourceCreateUpdatePatch> {

    @Autowired
    private Gson gson;

    @Override
    public ResourceCreateUpdatePatch convert(String source) {
        return gson.fromJson(source, ResourceCreateUpdatePatch.class);
    }
}

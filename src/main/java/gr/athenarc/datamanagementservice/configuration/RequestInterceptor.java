package gr.athenarc.datamanagementservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.*;

@Component
@Slf4j
public class RequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        response = logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
        log.debug("=========================== Request Begin ===========================");
        log.debug("URI          : " + request.getURI());
        log.debug("Method       : " + request.getMethod());
        log.debug("Headers      : " + request.getHeaders());
        log.debug("Body         : " + new String(body, "utf-8"));
        log.debug("============================ Request End ============================");
    }

    private ClientHttpResponse logResponse(ClientHttpResponse response) throws IOException {

        ClientHttpResponse newCopiedResponse = new BufferingClientHttpResponseWrapper(response);
        StringBuilder inputStringBuilder = new StringBuilder();
        if (isSuccessStatus(response.getRawStatusCode())) {
            inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newCopiedResponse.getBody(), "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }

        log.debug("=========================== Response Begin ===========================");
        log.debug("Status code   : {}", response.getStatusCode());
        log.debug("Status text   : {}", response.getStatusText());
        log.debug("Headers       : {}", response.getHeaders());
        log.debug("Response Body : {}", inputStringBuilder.toString());
        log.debug("============================ Response End ============================");

        return newCopiedResponse;
    }

    private static boolean isSuccessStatus(int statusCode) {
        return (statusCode / 100) == 2;
    }

    /**
     * Wrapper around ClientHttpResponse, buffers the body so it can be read repeatedly (for logging & consuming the result).
     */
    private static class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

        private final ClientHttpResponse response;
        private byte[] body;

        public BufferingClientHttpResponseWrapper(ClientHttpResponse response) {
            this.response = response;
        }

        @Override
        public InputStream getBody() throws IOException {
            if (body == null) {
                body = StreamUtils.copyToByteArray(response.getBody());
            }
            return new ByteArrayInputStream(body);
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return this.response.getStatusCode();
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return this.response.getRawStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return this.response.getStatusText();
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.response.getHeaders();
        }

        @Override
        public void close() {
            this.response.close();
        }
    }
}

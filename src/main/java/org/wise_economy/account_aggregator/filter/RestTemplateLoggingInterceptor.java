package org.wise_economy.account_aggregator.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

@Slf4j
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        log.debug(
                "\n===========================Request begin============================================="
                        + "\nURI         : {}"
                        + "\nMethod      : {}"
                        + "\nHeaders     : {}"
                        + "\nRequest body: {}"
                        + "\n==========================Request end================================================",
                request.getURI(), request.getMethod(), request.getHeaders(),
                new String(body, StandardCharsets.UTF_8.name())
        );
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        HttpStatus status = response.getStatusCode();
        log.debug(
                "\n===========================Response begin============================================="
                        + "\nStatus code  : {}"
                        + "\nStatus text  : {}"
                        + "\nHeaders      : {}",
                status, response.getStatusText(), response.getHeaders()
        );
        log.debug("\nResponse body: {}",
                StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));

        log.debug(
                "\n==========================Response end================================================");
    }
}
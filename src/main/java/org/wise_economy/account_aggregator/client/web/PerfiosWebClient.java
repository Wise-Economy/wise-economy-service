package org.wise_economy.account_aggregator.client.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationPayload;
import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationResponse;
import org.wise_economy.account_aggregator.exception.ExceptionHandler;
import org.wise_economy.account_aggregator.utils.ErrorHandler;

@Component
@Slf4j
public class PerfiosWebClient implements AccountAggregator {

    @Value("${perfios.web.base.uri}")
    private String perfiosBaseUri;

    @Value("${perfios.web.api.orgid}")
    private String perfiosOrgId;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public PerfiosWebClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public RegistrationResponse initiateRegistrationAndConsent(
        RegistrationPayload registrationPayload) {
        String initiateRegistrationUrl = "/process/initiateRegistrationAndConsent";
        try {
            String url = perfiosBaseUri.concat(initiateRegistrationUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RegistrationPayload> requestEntity = new HttpEntity<>(registrationPayload,
                headers);
            ResponseEntity<RegistrationResponse> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, RegistrationResponse.class);
            if (!(response.getStatusCode().is2xxSuccessful()) || ObjectUtils
                .isEmpty(response.getBody())) {
                throw new ExceptionHandler(ErrorHandler.INTERNAL_SERVER_ERROR,
                    "Perfios Registration Service",
                    "5xx");
            }
            return response.getBody();
        } catch (HttpClientErrorException hce) {
            throw new ExceptionHandler(ErrorHandler.RESOURCE_ACCESS_ERROR,
                "Perfios Registration Service",
                "4xx");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorHandler.RESOURCE_ACCESS_ERROR, "Swasth EUA Service",
                "5xx");
        }
    }
}

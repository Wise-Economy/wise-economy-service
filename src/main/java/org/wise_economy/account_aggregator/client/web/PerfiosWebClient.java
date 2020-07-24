package org.wise_economy.account_aggregator.client.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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
import org.wise_economy.account_aggregator.dto.perfios.datafetch.FetchTransactionsPayload;
import org.wise_economy.account_aggregator.dto.perfios.datafetch.InitiateDataFetch;
import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationPayload;
import org.wise_economy.account_aggregator.dto.perfios.registration.RegistrationResponse;
import org.wise_economy.account_aggregator.exception.ExceptionHandler;
import org.wise_economy.account_aggregator.utils.ErrorHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class PerfiosWebClient implements AccountAggregator {

    @Value("${perfios.web.base.uri}")
    private String perfiosBaseUri;

    @Value("${perfios.web.api.orgid}")
    private String perfiosOrgId;

    @Value("${perfios.web.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public RegistrationResponse initiateRegistrationAndConsent(
        RegistrationPayload registrationPayload) {
        String initiateRegistrationUrl = "/process/initiateRegistrationAndConsent";
        try {
            String url = perfiosBaseUri.concat(initiateRegistrationUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.add("org_id", perfiosOrgId);
            headers.add("api_key", apiKey);
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
            throw new ExceptionHandler(ErrorHandler.RESOURCE_ACCESS_ERROR,
                "Perfios Registration Service",
                "5xx");
        }
    }

    @Override
    public Boolean initiateDataFetch(InitiateDataFetch initiateDataFetch) {
        String initiateDataFetchUrl = "/process/initiateFetch";
        try {
            String url = perfiosBaseUri.concat(initiateDataFetchUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.add("org_id", perfiosOrgId);
            headers.add("api_key", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<InitiateDataFetch> requestEntity = new HttpEntity<>(initiateDataFetch,
                headers);
            ResponseEntity<Boolean> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, Boolean.class);
            if (!(response.getStatusCode().is2xxSuccessful())) {
                throw new ExceptionHandler(ErrorHandler.INTERNAL_SERVER_ERROR,
                    "Perfios Registration Service",
                    "5xx");
            }
            return true;
        } catch (HttpClientErrorException hce) {
            throw new ExceptionHandler(ErrorHandler.RESOURCE_ACCESS_ERROR,
                "Perfios Registration Service",
                "4xx");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorHandler.RESOURCE_ACCESS_ERROR,
                "Perfios Registration Servicee",
                "5xx");
        }
    }

    @Override
    public Boolean getTransactions(FetchTransactionsPayload txn) {
        String getTranscationsList = "/process/rawReport";
        try {
            String url = perfiosBaseUri.concat(getTranscationsList);
            HttpHeaders headers = new HttpHeaders();
            headers.add("org_id", perfiosOrgId);
            headers.add("api_key", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

            HttpEntity<FetchTransactionsPayload> requestEntity = new HttpEntity<>(txn,
                headers);
            ResponseEntity<byte[]> response = restTemplate
                .exchange(url, HttpMethod.POST, requestEntity, byte[].class);
            if (!(response.getStatusCode().is2xxSuccessful())) {
                throw new ExceptionHandler(ErrorHandler.INTERNAL_SERVER_ERROR,
                    "Perfios Registration Service",
                    "5xx");
            }
            if (response.getStatusCode().is2xxSuccessful()) {
                Files.write(Paths.get(txn.getTxnId() .concat(".zip")),
                    Objects.requireNonNull(response.getBody()));
            }
        } catch (HttpClientErrorException hce) {
            throw new ExceptionHandler(ErrorHandler.RESOURCE_ACCESS_ERROR,
                "Perfios Registration Service",
                "4xx");
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorHandler.RESOURCE_ACCESS_ERROR,
                "Perfios Registration Service",
                "5xx");
        }
        return true;
    }
}

package org.wise_economy.account_aggregator.configuration;


import static org.wise_economy.account_aggregator.utils.Constants.REST_CLIENT_TIMEOUT;
import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;
import org.wise_economy.account_aggregator.filter.RestTemplateLoggingInterceptor;

@Configuration
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
public class WebClientConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMinutes(REST_CLIENT_TIMEOUT))
                .setReadTimeout(Duration.ofMinutes(REST_CLIENT_TIMEOUT))
                .additionalInterceptors(new RestTemplateLoggingInterceptor())
                .requestFactory(
                        () -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .build();
    }

    @Bean
    public UriBuilderFactory getUriFactory() {
        return new DefaultUriBuilderFactory();
    }

}
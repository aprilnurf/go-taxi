package com.go_taxi.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class WebClientUtils {

    private static final Logger log = LoggerFactory.getLogger(WebClientUtils.class);

    public <T> Mono<ResponseEntity<T>> get(WebClient webClient, String uri, MultiValueMap queryParam,
                                           String token, Class<T> targetClass) {
        return webClient.get()
                .uri(builder -> queryParam != null ? builder.path(uri).queryParams(queryParam).build() : builder.path(uri).build())
                .headers(headers -> {
                    if (token != null) {
                        headers.setBearerAuth(token);
                    }
                })
                .exchange()
                .flatMap(clientResponse -> clientResponse.toEntity(targetClass)
                        .publishOn(Schedulers.boundedElastic())
                        .map(responseEntity -> {
                            log.info("Response: {} ", responseEntity);

                            return new ResponseEntity<>(
                                    responseEntity.getBody(),
                                    responseEntity.getHeaders(),
                                    responseEntity.getStatusCode());
                        }))
                .doOnError(e -> log.error("Get queryParam {}, Error {}", queryParam, e.getMessage(), e));
    }
}
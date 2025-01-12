package com.go_taxi.outbound.implementation;

import com.go_taxi.configuration.AvailApiProperties;
import com.go_taxi.exception.CustomException;
import com.go_taxi.helper.WebClientUtils;
import com.go_taxi.model.outbound.TaxiAvail;
import com.go_taxi.outbound.AvailOutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class AvailOutboundServiceImpl implements AvailOutboundService {

    private static final Logger log = LoggerFactory.getLogger(AvailOutboundServiceImpl.class);
    private final WebClientUtils webClientUtils;
    private final WebClient webClientAvail;
    private final AvailApiProperties properties;

    @Autowired
    public AvailOutboundServiceImpl(WebClientUtils webClientUtils, WebClient webClientAvail, AvailApiProperties properties) {
        this.webClientUtils = webClientUtils;
        this.webClientAvail = webClientAvail;
        this.properties = properties;
    }

    @Override
    public Mono<TaxiAvail> callAvail(String date) {
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("date_time", date);
        return webClientUtils.get(webClientAvail, properties.getPath(), null, null, TaxiAvail.class)
                .doOnError(e -> log.error("Error call Avail: ", e))
                .handle((response, sink) -> {
                    if (response.getStatusCode() != HttpStatus.OK) {
                        sink.error(new CustomException("Third party error"));
                        return;
                    }
                    log.info("Response data {} ", response.getBody());
                    sink.next(response.getBody());
                });
    }
}

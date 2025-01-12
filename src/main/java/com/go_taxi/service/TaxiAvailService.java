package com.go_taxi.service;

import com.go_taxi.model.dao.TaxiAvail;
import com.go_taxi.model.response.TaxiAvailResponse;
import reactor.core.publisher.Mono;

public interface TaxiAvailService {

    Mono<String> syncAvail(String dateTime);
    Mono<Iterable<TaxiAvail>> doSyncAvail(String dateTime);
    Mono<TaxiAvailResponse> searchNearby(double lon, double lat);
}

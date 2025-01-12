package com.go_taxi.outbound;

import com.go_taxi.model.outbound.TaxiAvail;
import reactor.core.publisher.Mono;

public interface AvailOutboundService {

    Mono<TaxiAvail> callAvail(String date);
}

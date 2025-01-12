package com.go_taxi.service.implementation;

import com.go_taxi.model.dao.TaxiAvail;
import com.go_taxi.model.response.TaxiAvailResponse;
import com.go_taxi.outbound.AvailOutboundService;
import com.go_taxi.repository.TaxiAvailRepository;
import com.go_taxi.service.TaxiAvailService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TaxiAvailServiceImpl implements TaxiAvailService {

    private static final Logger log = LoggerFactory.getLogger(TaxiAvailServiceImpl.class);
    private final TaxiAvailRepository taxiAvailRepository;
    private final AvailOutboundService availOutboundService;
    @Value("${max.distance:10km}")
    private String maxDistance;

    @Autowired
    public TaxiAvailServiceImpl(TaxiAvailRepository taxiAvailRepository, AvailOutboundService availOutboundService) {
        this.taxiAvailRepository = taxiAvailRepository;
        this.availOutboundService = availOutboundService;
    }

    @Override
    public Mono<String> syncAvail(String dateTime) {
        doSyncAvail(dateTime)
                .subscribeOn(Schedulers.parallel())
                .subscribe();
        return Mono.just("Sync avail start");
    }

    @Override
    public Mono<Iterable<TaxiAvail>> doSyncAvail(String dateTime) {
        dateTime = getDefaultDateTime(dateTime);
        return availOutboundService.callAvail(dateTime)
                .flatMapMany(taxiAvail -> Flux.fromIterable(taxiAvail.getFeatures()))
                .flatMap(avail -> Flux.fromIterable(avail.getGeometry().getCoordinates()))
                .map(coordinates -> {
                    GeoPoint geoPoint = new GeoPoint(coordinates.get(1), coordinates.get(0));
                    return TaxiAvail.builder()
                            .location(geoPoint)
                            .build();
                })
                .collectList()
                .publishOn(Schedulers.boundedElastic())
                .map(taxiAvails -> {
                    log.info("Saving avail data {} ", taxiAvails.size());
                    return taxiAvailRepository.saveAll(taxiAvails);
                });
    }

    private static String getDefaultDateTime(String dateTime) {
        if (StringUtils.isBlank(dateTime)) {
            LocalDate localDate = LocalDate.now();
            dateTime = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        return dateTime;
    }

    @Override
    public Mono<TaxiAvailResponse> searchNearby(double lon, double lat) {
        GeoPoint geoPoint = new GeoPoint(lat, lon);
        var avails = taxiAvailRepository.searchByLonLat(geoPoint, maxDistance);
        var coordinates = avails.stream().map(a -> a.getContent().getLocation()).toList();
        return Mono.just(TaxiAvailResponse.builder().coordinates(coordinates).build());
    }
}

package com.go_taxi.controller;

import com.go_taxi.exception.ErrorHandlerException;
import com.go_taxi.model.response.TaxiAvailResponse;
import com.go_taxi.service.TaxiAvailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/taxi")
public class TaxiController extends ErrorHandlerException {

    @Autowired
    private TaxiAvailService taxiAvailService;

    @GetMapping("/search")
    public Mono<TaxiAvailResponse> search(@RequestParam double lon, @RequestParam double lat) {
        return taxiAvailService.searchNearby(lon, lat);
    }

    @GetMapping("/sync")
    public Mono<String> sync(@RequestParam String dateTime) {
        return taxiAvailService.syncAvail(dateTime);
    }

}

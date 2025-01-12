package com.go_taxi.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Builder
@Getter
@Setter
public class TaxiAvailResponse {

    private List<GeoPoint> coordinates;
}

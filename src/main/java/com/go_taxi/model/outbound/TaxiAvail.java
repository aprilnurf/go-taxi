package com.go_taxi.model.outbound;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TaxiAvail {
    private List<Feature> features;
}

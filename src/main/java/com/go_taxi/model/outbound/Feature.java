package com.go_taxi.model.outbound;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Feature {

    private String type;
    private Geometry geometry;
    private Properties properties;
}

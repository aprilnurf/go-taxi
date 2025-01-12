package com.go_taxi.model.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Properties {

    @JsonProperty("taxi_count")
    private int taxiCount;
}

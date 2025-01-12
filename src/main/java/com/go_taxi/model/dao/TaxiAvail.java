package com.go_taxi.model.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Getter
@Setter
@Document(indexName = "avail")
@Builder
public class TaxiAvail {
    @Id
    private String id;
    @GeoPointField
    private GeoPoint location;

}

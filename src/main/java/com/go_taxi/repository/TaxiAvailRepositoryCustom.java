package com.go_taxi.repository;

import com.go_taxi.model.dao.TaxiAvail;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

public interface TaxiAvailRepositoryCustom {

    List<SearchHit<TaxiAvail>> searchByLonLat(GeoPoint geoPoint, String maxDistance);
}

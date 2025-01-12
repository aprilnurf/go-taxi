package com.go_taxi.repository.implementation;

import com.go_taxi.model.dao.TaxiAvail;
import com.go_taxi.repository.TaxiAvailRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaxiAvailRepositoryCustomImpl implements TaxiAvailRepositoryCustom {

    private final ElasticsearchOperations operations;

    @Autowired
    public TaxiAvailRepositoryCustomImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<SearchHit<TaxiAvail>> searchByLonLat(GeoPoint geoPoint, String maxDistance) {
        Query query = new CriteriaQuery(new Criteria("location").within(geoPoint, maxDistance));
        Sort sort = Sort.by(new GeoDistanceOrder("location", geoPoint).withUnit(maxDistance));
        query.addSort(sort);

        return operations.search(query, TaxiAvail.class).getSearchHits();
    }
}

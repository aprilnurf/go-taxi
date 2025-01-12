package com.go_taxi.repository;

import com.go_taxi.model.dao.TaxiAvail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiAvailRepository extends ElasticsearchRepository<TaxiAvail, String>, TaxiAvailRepositoryCustom {

}

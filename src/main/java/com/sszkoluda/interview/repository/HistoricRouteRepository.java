package com.sszkoluda.interview.repository;

import com.sszkoluda.interview.domain.HistoricRoute;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricRouteRepository extends MongoRepository<HistoricRoute, String> {
}

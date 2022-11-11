package com.sszkoluda.interview.repository;

import com.sszkoluda.interview.domain.HistoricRoute;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HistoricRouteRepository extends MongoRepository<HistoricRoute, String> {

    @Query("{$and:[{fromPort: {$in: [?0,?1]}},{toPort: {$in: [?0,?1]}}]}")
    List<HistoricRoute> findAllRoutesBetweenPorts(String firstPort, String secondPort);
}

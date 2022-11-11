package com.sszkoluda.interview.service.algorithm;

import com.sszkoluda.interview.domain.HistoricRoute;

import java.util.List;

public interface RouteFinder {

    HistoricRoute findMostRepresentativeRoute(List<HistoricRoute> routes);
}

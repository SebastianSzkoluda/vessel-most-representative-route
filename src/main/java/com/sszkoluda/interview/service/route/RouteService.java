package com.sszkoluda.interview.service.route;

import com.sszkoluda.interview.domain.HistoricRoute;
import com.sszkoluda.interview.dto.HistoricRouteDTO;
import com.sszkoluda.interview.mapper.HistoricRouteMapper;
import com.sszkoluda.interview.repository.HistoricRouteRepository;
import com.sszkoluda.interview.service.algorithm.RouteFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteFinder routeFinder;
    private final HistoricRouteRepository historicRouteRepository;
    private final HistoricRouteMapper historicRouteMapper;

    public HistoricRouteDTO findMostRepresentativeRoute() {
        List<HistoricRoute> allRoutes = historicRouteRepository.findAll();
        HistoricRoute mostRepresentativeRoute = routeFinder.findMostRepresentativeRoute(allRoutes);
        return historicRouteMapper.mapToHistoricRouteDTO(mostRepresentativeRoute);
    }

}

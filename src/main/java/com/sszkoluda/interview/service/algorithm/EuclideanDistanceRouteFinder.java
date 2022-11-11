package com.sszkoluda.interview.service.algorithm;

import com.sszkoluda.interview.domain.HistoricRoute;
import com.sszkoluda.interview.domain.Point;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// This class is for finding most representative route using Euclidean distance measure algorithm
@Service
public class EuclideanDistanceRouteFinder implements RouteFinder {

    private static final int EARTH_RADIUS = 6371;
    private static final String NO_ROUTES_ERROR_MSG = "To find most representative route there must be at least one route!";

    @Override
    public HistoricRoute findMostRepresentativeRoute(List<HistoricRoute> routes) {
        if (CollectionUtils.isEmpty(routes)) {
            throw new IllegalStateException(NO_ROUTES_ERROR_MSG);
        }
        if (routes.size() == 1) {
            return routes.get(0);
        }
        Map<String, HistoricRoute> historicRouteById = routes.stream()
                .collect(Collectors.toMap(HistoricRoute::getId, Function.identity()));
        Map<String, BigDecimal> similarityDistanceByRouteKey = routes.stream()
                .collect(Collectors.toMap(HistoricRoute::getId, v -> BigDecimal.ZERO));
        for (int i = 0; i < routes.size(); i++) {
            for (int j = i + 1; j < routes.size(); j++) {
                HistoricRoute historicRoute1 = routes.get(i);
                HistoricRoute historicRoute2 = routes.get(j);
                BigDecimal similarityDistance = euclideanDistance(historicRoute1.getPoints(), historicRoute2.getPoints());
                addSimilarityDistance(similarityDistanceByRouteKey, historicRoute1, similarityDistance);
                addSimilarityDistance(similarityDistanceByRouteKey, historicRoute2, similarityDistance);
            }
        }
        // finding route with minimum summary distance
        String mostRepresentativeRouteId = similarityDistanceByRouteKey
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalStateException::new);

        return historicRouteById.get(mostRepresentativeRouteId);
    }

    // returns euclidean distance between two routes which can be described with this mathematical formula:
    // EuclideanDistance(A, B) = Sqrt(Σ from i=1 to min(m,n) distance(ai,bi)^2)
    // where:
    // m and n are numbers of points in A and B routes
    // ai and bi are specific points in route
    private BigDecimal euclideanDistance(List<Point> route1, List<Point> route2) {
        return BigDecimal.valueOf(
                Math.abs(
                        IntStream.range(0, Math.min(route1.size(), route2.size()))
                                .mapToDouble(i -> Math.pow(distance(route1.get(i), route2.get(i)), 2))
                                .sum()
                )
        );
    }

    // method to calculate distance between two coordinates
    private static double distance(Point point1, Point point2) {
        // The math module contains a function named toRadians which converts from degrees to radians.
        double lon1 = Math.toRadians(point1.getLongitude());
        double lon2 = Math.toRadians(point2.getLongitude());
        double lat1 = Math.toRadians(point1.getLatitude());
        double lat2 = Math.toRadians(point2.getLatitude());

        // Haversine formula
        double deltaLon = lon2 - lon1;
        double deltaLat = lat2 - lat1;
        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // calculate the result in meters
        return c * EARTH_RADIUS * 1000;
    }

    private static void addSimilarityDistance(Map<String, BigDecimal> similarityDistanceByRouteKey,
                                                    HistoricRoute historicRoute,
                                                    BigDecimal similarityDistance) {
        similarityDistanceByRouteKey
                .computeIfPresent(historicRoute.getId(), (key, value) -> value.add(similarityDistance));
    }
}

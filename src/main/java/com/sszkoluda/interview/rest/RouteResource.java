package com.sszkoluda.interview.rest;

import com.sszkoluda.interview.dto.HistoricRouteDTO;
import com.sszkoluda.interview.service.route.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/routes")
@RequiredArgsConstructor
public class RouteResource {

    private final RouteService routeService;

    @GetMapping("/most-representative")
    public ResponseEntity<HistoricRouteDTO> getMostRepresentativeRoute() {
        return ResponseEntity.ok(routeService.findMostRepresentativeRoute());
    }
}

package com.sszkoluda.interview.config;

import com.sszkoluda.interview.domain.HistoricRoute;
import com.sszkoluda.interview.repository.HistoricRouteRepository;
import com.sszkoluda.interview.service.data.DataImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private static final String INPUT_DATA_PATH = "static/DEBRV_DEHAM_historical_routes.csv";

    private final DataImporter dataImporter;
    private final HistoricRouteRepository historicRouteRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_DATA_PATH);
        List<HistoricRoute> historicRoutes = dataImporter.importData(inputStream);
        historicRouteRepository.saveAll(historicRoutes);
    }
}

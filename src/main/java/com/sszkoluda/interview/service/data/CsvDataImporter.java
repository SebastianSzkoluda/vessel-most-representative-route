package com.sszkoluda.interview.service.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.sszkoluda.interview.domain.Point;
import com.sszkoluda.interview.domain.HistoricRoute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CsvDataImporter implements DataImporter {

    private static final String ROUTE_MISSING_VALUES_ERROR_MSG = "Values for fields: id, from_seq, to_seq, from_port, to_port, leg_duration, count, points are required and should be provided!";
    private static final String POINT_MISSING_VALUES_ERROR_MSG = "Values for fields: longitude, latitude and timestamp are required and should be provided!";

    @Override
    public List<HistoricRoute> importData(InputStream inputFile) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputFile))) {
            return reader.readAll().stream()
                    .skip(1) // skip header line
                    .filter(row -> row.length > 1) // skip empty rows from file
                    .map(this::toHistoricRoute)
                    .collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private HistoricRoute toHistoricRoute(String[] row) {
        log.info("Processing row with values: {}", Arrays.toString(row));
        validateRouteData();
        return HistoricRoute.builder()
                .shipId(row[0])
                .fromSeq(row[1])
                .toSeq(row[2])
                .fromPort(row[3])
                .toPort(row[4])
                .legDuration(Long.valueOf(row[5]))
                .count(Integer.valueOf(row[6]))
                .points(toPoints(row[7]))
                .build();
    }

    private List<Point> toPoints(String pointsArrayString) {
        String[] pointsArray = pointsArrayString
                .replaceAll("\\[", "")
                .replaceAll("]$", "")
                .split("], |] ");
        return Arrays.stream(pointsArray)
                .map(this::toPoint)
                .filter(this::onlyValidPoints)
                .collect(Collectors.toList());
    }

    private Point toPoint(String point) {
        String[] pointsValues = point.split(", ");
        validatePointData(pointsValues);
        return Point.builder()
                .longitude(valueOrNull(pointsValues[0], Double::parseDouble))
                .latitude(valueOrNull(pointsValues[1], Double::parseDouble))
                .timestamp(valueOrNull(pointsValues[2].replaceAll(",", ""), Long::valueOf)) // on timestamp I have found "," but there shouldn't be any, so I removed it
                .speed(valueOrNull(pointsValues[3], Double::parseDouble))
                .build();
    }

    private boolean onlyValidPoints(Point point) {
        return Objects.nonNull(point.getLongitude())
                && Objects.nonNull(point.getLatitude())
                && Objects.nonNull(point.getTimestamp());
    }

    private <T> T valueOrNull(String value, Function<String, T> mapper) {
        return Optional.ofNullable(value)
                .filter(NumberUtils::isCreatable)
                .map(mapper)
                .orElse(null);
    }

    private void validateRouteData(String... values) {
        validateData(values, ROUTE_MISSING_VALUES_ERROR_MSG);
    }

    private void validatePointData(String... values) {
        validateData(values, POINT_MISSING_VALUES_ERROR_MSG);
    }

    private void validateData(String[] values, String errorMessage) {
        boolean isAnyValueNotProvided = Arrays.stream(values).anyMatch(StringUtils::isBlank);
        if (isAnyValueNotProvided) {
            throw new IllegalStateException(errorMessage);
        }
    }
}

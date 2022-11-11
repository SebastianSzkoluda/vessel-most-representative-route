package com.sszkoluda.interview.domain;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document("route_history")
public class HistoricRoute {

    @Id
    private String id;
    // ship_id
    @NonNull
    private String shipId;
    @NonNull
    private String fromSeq;
    @NonNull
    private String toSeq;
    // route origin
    @NonNull
    private String fromPort;
    // route destination
    @NonNull
    private String toPort;
    //  trip duration in milliseconds
    @NonNull
    private Long legDuration;
    // total number of readings/points within the route
    @NonNull
    private Integer count;
    // an array of vessel observations from GPS where observation is [longitude, latitude, timestamp in epoch milliseconds, actual vessel speed in knots]
    @NonNull
    private List<Point> points;
}

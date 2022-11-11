package com.sszkoluda.interview.domain;

import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Point {

    @NonNull
    private Double longitude;
    @NonNull
    private Double latitude;
    // in epoch milliseconds
    @NonNull
    private Long timestamp;
    // actual vessel speed in knots
    private Double speed;
}

package com.sszkoluda.interview.dto;

import lombok.Data;

@Data
public class PointDTO {

    private Double longitude;
    private Double latitude;
    private Long timestamp;
    private Double speed;
}

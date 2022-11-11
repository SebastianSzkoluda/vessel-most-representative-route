package com.sszkoluda.interview.dto;

import lombok.Data;

import java.util.List;

@Data
public class HistoricRouteDTO {

    private String id;
    private String shipId;
    private String fromSeq;
    private String toSeq;
    private String fromPort;
    private String toPort;
    private Long legDuration;
    private Integer count;
    private List<PointDTO> points;
}

package com.sszkoluda.interview.mapper;

import com.sszkoluda.interview.domain.HistoricRoute;
import com.sszkoluda.interview.dto.HistoricRouteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PointMapper.class})
public interface HistoricRouteMapper {

    HistoricRouteDTO mapToHistoricRouteDTO(HistoricRoute historicRoute);
}

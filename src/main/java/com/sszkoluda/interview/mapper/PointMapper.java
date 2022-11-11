package com.sszkoluda.interview.mapper;

import com.sszkoluda.interview.domain.Point;
import com.sszkoluda.interview.dto.PointDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {

    PointDTO mapToPointDTO(Point point);

    List<PointDTO> mapToPointDTOList(List<Point> points);
}

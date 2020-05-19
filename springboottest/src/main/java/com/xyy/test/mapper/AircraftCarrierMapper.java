package com.xyy.test.mapper;

import com.xyy.test.entity.AircraftCarrier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AircraftCarrierMapper {
    List<AircraftCarrier> getAllData(@Param("order") String order);
}


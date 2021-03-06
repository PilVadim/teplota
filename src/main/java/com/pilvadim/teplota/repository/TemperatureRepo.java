package com.pilvadim.teplota.repository;

import com.pilvadim.teplota.model.Temperature;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface TemperatureRepo {

    @Select("select * from Temperatures Where moment >= #{start} AND moment <= #{end} ")
    List<Temperature> getTemperaturesForPeriod(LocalDateTime start, LocalDateTime end );

    @Insert("insert into temperatures(celsius,placeId,moment) values(#{celsius},#{placeId},#{moment})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id", useCache = false)
    void save( Temperature t );

}

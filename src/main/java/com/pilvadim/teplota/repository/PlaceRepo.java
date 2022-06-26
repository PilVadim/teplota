package com.pilvadim.teplota.repository;

import com.pilvadim.teplota.model.Place;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlaceRepo {

    @Select("select * from Places")
    List<Place> getAllPlaces();

    @Select("select * from Places where id = #{id} ")
    Place getPlace( Integer id );

    @Select("select * from Places where enabled = true")
    List<Place> getAllEnabledPlaces();

    @Select("select * from Places where id=ANY(#{ids}::int[])")
    List<Place> getAllPlacesByIds( Integer[] ids );

    @Insert("insert into places(name,latitude,longitude,period,enabled) " +
            "values(#{name},#{latitude},#{longitude},#{period},#{enabled})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void save( Place t );

    @Insert("update places set " +
                "name=#{name}," +
                "latitude=#{latitude}," +
                "longitude=#{longitude}," +
                "period=#{period}," +
                "enabled=#{enabled} " +
            "where id = #{id}")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void update( Place t );

}

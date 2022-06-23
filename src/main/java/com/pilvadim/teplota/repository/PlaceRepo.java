package com.pilvadim.teplota.repository;

import com.pilvadim.teplota.model.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlaceRepo {

    @Select("select * from Places")
    List<Place> getAllPlaces();

    @Select("select * from Places WHERE enabled = true")
    List<Place> getAllEnabledPlaces();

}

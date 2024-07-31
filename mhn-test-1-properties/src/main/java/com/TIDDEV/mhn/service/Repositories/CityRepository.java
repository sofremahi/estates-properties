package com.TIDDEV.mhn.service.Repositories;

import com.TIDDEV.mhn.service.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("select c from City c join Province p on " +
            "c.province.code = p.code where p.code = :provinceCode")
    List<City> citiesOfProvince(@Param("provinceCode") Long code);
}

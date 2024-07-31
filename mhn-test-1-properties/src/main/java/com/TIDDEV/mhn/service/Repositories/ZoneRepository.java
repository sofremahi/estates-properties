package com.TIDDEV.mhn.service.Repositories;

import com.TIDDEV.mhn.service.model.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone , Long> {
    @Query("select z from Zone z join City c " +
            "on z.city.code = c.code join Province p on c.province.code = p.code " +
            "where c.code = :cityCode")
     List<Zone> zonesOfCity(@Param("cityCode") Long code);
    @Query("select z from Zone z join City c " +
            "on z.city.code = c.code join Province p on c.province.code = p.code " +
            "where p.code = :provinceCode")
     List<Zone> zonesOfProvince(@Param("provinceCode") Long code);

}

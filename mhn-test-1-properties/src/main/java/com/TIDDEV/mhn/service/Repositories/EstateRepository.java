package com.TIDDEV.mhn.service.Repositories;

import com.TIDDEV.mhn.service.model.Estate;
import com.TIDDEV.mhn.service.modelDto.OrderPriceForZones;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EstateRepository extends JpaRepository<Estate, Long> {
    @Query("select e from Estate e join Province p on " +
            "e.province.code = p.code where p.code = :provinceCode")
    List<Estate> estatesOfProvince(@Param("provinceCode") Long code);

    @Query("select e from Estate e join City c " +
            "on e.city.code = c.code where c.code = :cityCode")
    List<Estate> estatesOfCity(@Param("cityCode") Long code);

    @Query("select e from Estate e join Zone z " +
            "on e.zone.code = z.code where z.code = :zoneCode")
    List<Estate> estatesOfZone(@Param("zoneCode") Long code);

    @Query("select e from Estate e join Zone z " +
            "on e.zone.code = z.code join City c " +
            "on z.city.code = c.code join Province p " +
            "on c.province.code = p.code where" +
            " (:provinceCode IS NULL OR p.code = :provinceCode) and" +
            " (:cityCode IS NULL OR c.code = :cityCode) and " +
            " (:zoneCode IS NULL OR z.code = :zoneCode)")
    Page<Estate> estatesPage(Pageable pageable, @Param("provinceCode") Long provinceCode,
                             @Param("cityCode") Long cityCode,
                             @Param("zoneCode") Long zoneCode);

    @Query("select e from Estate e where e.dateYear BETWEEN :start and :end")
    Page<Estate> estatePageByDate(Pageable pageable, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select e from Estate e where extract(year FROM e.dateYear) BETWEEN :start and :end" +
            " and e.zone.code = :zoneCode")
    Page<Estate> estatePageOfZoneByYear(Pageable pageable, @Param("zoneCode") Long zoneCode ,
                                        @Param("start") Integer start, @Param("end") Integer end);


    @Query("select e from Estate e where e.dateYear BETWEEN :start and :end")
    List<Estate> estateListByDate(@Param("start") LocalDate start, @Param("end") LocalDate end);
    @Query("SELECT new com.TIDDEV.mhn.service.modelDto.OrderPriceForZones(z.code, z.title, e.dateYear, AVG(e.pricePerSquareMeter)) " +
            "FROM Estate e " +
            "JOIN e.zone z " +
            "WHERE (:proCode IS NULL OR z.city.province.code = :proCode) " +
            "AND (:cityCode IS NULL OR z.city.code = :cityCode) " +
            "AND (:zoneCode IS NULL OR z.code = :zoneCode) " +
            "AND (:year IS NULL OR EXTRACT(YEAR FROM e.dateYear ) = :year) " +
            "GROUP BY z.code, z.title, e.dateYear " +
            "ORDER BY AVG(e.pricePerSquareMeter) DESC")
    List<OrderPriceForZones> findExpensiveZones(@Param("proCode") Long proCode,
                                                @Param("cityCode") Long cityCode,
                                                @Param("zoneCode") Long zoneCode,
                                                @Param("year") Integer year);

    @Query("SELECT new com.TIDDEV.mhn.service.modelDto.OrderPriceForZones(z.code, z.title, e.dateYear, AVG(e.pricePerSquareMeter)) " +
            "FROM Estate e " +
            "JOIN e.zone z " +
            "WHERE (:proCode IS NULL OR z.city.province.code = :proCode) " +
            "AND (:cityCode IS NULL OR z.city.code = :cityCode) " +
            "AND (:zoneCode IS NULL OR z.code = :zoneCode) " +
            "AND (:dateYear IS NULL OR e.dateYear = :dateYear) " +
            "GROUP BY z.code, z.title, e.dateYear " +
            "ORDER BY AVG(e.pricePerSquareMeter) ASC ")
    List<OrderPriceForZones> findCheapZones(@Param("proCode") Long proCode,
                                            @Param("cityCode") Long cityCode,
                                            @Param("zoneCode") Long zoneCode,
                                            @Param("dateYear") LocalDate dateYear);

}

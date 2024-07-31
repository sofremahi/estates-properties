package com.TIDDEV.mhn.service;


import com.TIDDEV.mhn.service.Repositories.CityRepository;
import com.TIDDEV.mhn.service.Repositories.EstateRepository;
import com.TIDDEV.mhn.service.Repositories.ProvinceRepository;
import com.TIDDEV.mhn.service.Repositories.ZoneRepository;
import com.TIDDEV.mhn.service.model.City;
import com.TIDDEV.mhn.service.model.Estate;
import com.TIDDEV.mhn.service.model.Province;
import com.TIDDEV.mhn.service.model.Zone;
import com.TIDDEV.mhn.service.modelDto.OrderPriceForZones;
import com.TIDDEV.mhn.web.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceApp {
    private final ProvinceRepository provinceRepository;
    private final EstateRepository estateRepository;
    private final CityRepository cityRepository;
    private final ZoneRepository zoneRepository;
//    private final MessageSource messageSource;

    public List<Province> provinces() {
        return provinceRepository.findAll();
    }

    public List<City> cityListByProvince(Long provinceCode) {
        provinceRepository.findById(provinceCode).orElseThrow(
                () -> new BusinessException("province.not.found.by.code", new Object[]{provinceCode})
        );
        return cityRepository.citiesOfProvince(provinceCode);
    }

    public List<Zone> zoneListByCity(Long cityCode) {
        cityRepository.findById(cityCode).orElseThrow(
                () -> new BusinessException("city.not.found.by.code", new Object[]{cityCode})
        );
        return zoneRepository.zonesOfCity(cityCode);
    }

    public List<Estate> estateListOfZone(Long zoneCode) {
        zoneRepository.findById(zoneCode).orElseThrow(
                () -> new BusinessException("zone.not.found.by.code", new Object[]{zoneCode})
        );
        return estateRepository.estatesOfZone(zoneCode);
    }


    public List<Estate> estatePage(int page, int size, Long provinceCode, Long cityCode, Long zoneCode) {
        Pageable pageable = PageRequest.of(page, size);
        if (provinceCode != null) {
            provinceRepository.findById(provinceCode).orElseThrow(
                    () -> new BusinessException("province.not.found.by.code", new Object[]{provinceCode})
            );
        }
        if (cityCode != null) {
            cityRepository.findById(cityCode).orElseThrow(
                    () -> new BusinessException("city.not.found.by.code", new Object[]{cityCode})
            );
        }
        if (zoneCode != null) {
            zoneRepository.findById(zoneCode).orElseThrow(
                    () -> new BusinessException("zone.not.found.by.code", new Object[]{zoneCode})
            );

        }
        return estateRepository.estatesPage(pageable, provinceCode, cityCode, zoneCode).getContent();
    }

    public List<Estate> estatePageOfZoneByYear(int page, int size, Long zoneCode, Integer start, Integer end) {
        Pageable pageable = PageRequest.of(page, size);
        if (zoneCode != null) {
            zoneRepository.findById(zoneCode).orElseThrow(
                    () -> new BusinessException("zone.not.found.by.code", new Object[]{zoneCode})
            );
        }
        return estateRepository.estatePageOfZoneByYear(pageable, zoneCode, start, end).getContent();
    }

    public byte[] chartAvgPricePerYear(Long provinceCode, Long cityCode, Long zoneCode, Integer year)
            throws Exception {
        // data database
//        List<OrderPriceForZones> data = estateRepository.findExpensiveZones(provinceCode, cityCode, zoneCode, year);
        //data memory
        List<OrderPriceForZones> data = mostExpensiveZonesMem(provinceCode, cityCode, zoneCode, year);

        // export to chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (OrderPriceForZones order : data) {
            dataset.addValue(order.getAveragePrice(),
                    "value", Integer.valueOf(order.getDateYear().getYear()));
        }
        JFreeChart chart = ChartFactory.createAreaChart(
                "Average estates price per year ",
                "years",
                "Average price",
                dataset,
                PlotOrientation.VERTICAL,
                false, false, false
        );
        BufferedImage image = chart.createBufferedImage(800, 600);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ChartUtils.writeBufferedImageAsPNG(baos, image);
        return baos.toByteArray();
    }

    public List<OrderPriceForZones> mostExpensiveZones(Long provinceCode, Long cityCode, Long zoneCode, Integer year) {
        if (provinceCode != null) {
            provinceRepository.findById(provinceCode).orElseThrow(
                    () -> new BusinessException("province.not.found.by.code", new Object[]{provinceCode})
            );
        }
        if (cityCode != null) {
            cityRepository.findById(cityCode).orElseThrow(
                    () -> new BusinessException("city.not.found.by.code", new Object[]{cityCode})
            );
        }
        if (zoneCode != null) {
            zoneRepository.findById(zoneCode).orElseThrow(
                    () -> new BusinessException("zone.not.found.by.code", new Object[]{zoneCode})
            );

        }
        return estateRepository.findExpensiveZones(provinceCode, cityCode, zoneCode, year);
    }

    public List<OrderPriceForZones> mostExpensiveZonesMem(Long provinceCode, Long cityCode, Long zoneCode, Integer year) {
        List<Estate> estates = estateRepository.findAll();
        return estates.stream()
                .filter(e -> (provinceCode == null || e.getZone().getCity().getProvince().getCode().equals(provinceCode)))
                .filter(e -> (cityCode == null || e.getZone().getCity().getCode().equals(cityCode)))
                .filter(e -> (zoneCode == null || e.getZone().getCode().equals(zoneCode)))
                .filter(e -> (year == null || e.getDateYear().getYear() == year))
                .collect(Collectors.groupingBy(e -> new ZoneYearKey(e.getZone().getCode(), e.getZone().getTitle(), e.getDateYear()),
                        Collectors.averagingDouble(e -> e.getPricePerSquareMeter().doubleValue())))
                .entrySet().stream()
                .map(entry -> new OrderPriceForZones(entry.getKey().getZoneCode(), entry.getKey().getZoneTitle(), entry.getKey().getYear(), entry.getValue()))
                .sorted(Comparator.comparingDouble(OrderPriceForZones::getAveragePrice).reversed())
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private static class ZoneYearKey {
        private Long zoneCode;
        private String zoneTitle;
        private LocalDate year;
    }

}

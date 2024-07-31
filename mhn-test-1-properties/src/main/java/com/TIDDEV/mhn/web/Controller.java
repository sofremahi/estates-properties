package com.TIDDEV.mhn.web;

import com.TIDDEV.mhn.common.Response;
import com.TIDDEV.mhn.service.ServiceApp;
import com.TIDDEV.mhn.service.model.City;
import com.TIDDEV.mhn.service.model.Estate;
import com.TIDDEV.mhn.service.model.Province;
import com.TIDDEV.mhn.service.model.Zone;
import com.TIDDEV.mhn.service.modelDto.OrderPriceForZones;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/rest/estate")
@RequiredArgsConstructor
public class Controller {

    private final MessageSource messageSource;
    private final ServiceApp service;

    @GetMapping("/find/province")
    public Response<List<Province>> provinceList() {
        return new Response<>(service.provinces());
    }

    @GetMapping("/find/city/{provinceCode}")
    public Response<List<City>> cityList(@PathVariable("provinceCode") Long code) {
        return new Response<>(service.cityListByProvince(code));
    }

    @GetMapping("/find/zone/{cityCode}")
    public Response<List<Zone>> zoneList(@PathVariable("cityCode") Long code) {
        return new Response<>(service.zoneListByCity(code));
    }

    @GetMapping("/find/estate/{zoneCode}")
    public Response<List<Estate>> estateList(@PathVariable("zoneCode") Long code) {
        return new Response<>(service.estateListOfZone(code));
    }

    @GetMapping("/find/estate/{page}/{size}")
    public Response<List<Estate>> estatePage(@PathVariable("page") int page,
                                             @PathVariable("size") int size,
                                             @RequestParam(required = false) Long provinceCode,
                                             @RequestParam(required = false) Long cityCode,
                                             @RequestParam(required = false) Long zoneCode) {
        return new Response<>(service.estatePage(page, size, provinceCode, cityCode, zoneCode));
    }

    @GetMapping("/find/estate/{page}/{size}/{zoneCode}/{startDate}/{endDate}")
    public Response<List<Estate>> estatePageByDate(@PathVariable("page") int page,
                                                   @PathVariable("size") int size,
                                                   @PathVariable("zoneCode") Long zoneCode,
                                                   @PathVariable("startDate")
                                                   Integer startDate,
                                                   @PathVariable("endDate")
                                                   Integer endDate) {
        return new Response<>(service.estatePageOfZoneByYear(page, size, zoneCode, startDate, endDate));
    }

    @GetMapping("/chart/avg/price/per/year")
    public ResponseEntity<byte[]> chartAvgPrice(@RequestParam(required = false) Long provinceCode,
                                                @RequestParam(required = false) Long cityCode,
                                                @RequestParam(required = false) Long zoneCode,
                                                @RequestParam(required = false) Integer year) throws Exception {

        byte[] image = service.chartAvgPricePerYear(provinceCode , cityCode , zoneCode , year);
        HttpHeaders headers = new HttpHeaders(); headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(image , headers ,HttpStatus.OK);
    }

    @GetMapping("/zones/expensive/to/cheap")
    public Response<List<OrderPriceForZones>> expensiveToCheapZonesList(@RequestParam(required = false) Long provinceCode,
                                                          @RequestParam(required = false) Long cityCode,
                                                          @RequestParam(required = false) Long zoneCode,
                                                          @RequestParam(required = false) Integer year){
        return new Response<>(service.mostExpensiveZones(provinceCode , cityCode , zoneCode , year)) ;
    }
}

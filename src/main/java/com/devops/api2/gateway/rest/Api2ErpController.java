package com.devops.api2.gateway.rest;

import com.devops.api2.gateway.service.RestRequestCenERPService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api2")
public class Api2ErpController {

    private final RestRequestCenERPService restRequestCenERPService;

    public Api2ErpController(RestRequestCenERPService restRequestCenERPService) {
        this.restRequestCenERPService = restRequestCenERPService;
    }

    @GetMapping("/openapi/baseInfo")
    public Mono<String> getBaseInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getBaseInfoData(queryParams);
    }

    @GetMapping("/openapi/dept")
    public Mono<String> getDeptData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getDeptData(queryParams);
    }

    @GetMapping("/openapi/company")
    public Mono<String> getCompanyData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getCompanyData(queryParams);
    }
}


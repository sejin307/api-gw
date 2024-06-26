package com.devops.api2.gateway.locator;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import com.devops.api2.gateway.filter.CustomFilter;
import com.devops.api2.gateway.locator.definition.BaseGatewayProperties;
import com.devops.api2.gateway.locator.provider.CustomCircuitBreakerConfigProvider;
import com.devops.api2.gateway.locator.provider.FilterListProvider;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * TODO:cloud gateway 의 설정을 동적으로 가져와야함.
 * 왠만한 gateway에서 제공하는 메소드 사용법의 이해도가 없다면 코드작성이 매우 어려움.
 * 위의 이유로 Rocator는 yml설정을 Java에서 동적생성 보다  Locator에서 직접세팅
 *
 * CENERP에서 제공하는 API를 정의
 */
@Configuration
public class CenERPRouteLocator {

    private final CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory;

    private final CustomFilter customFilter;

    private final BaseGatewayProperties baseGatewayProperties;

    private final CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider;

    private final FilterListProvider filterListProvider;

    @Value("${targetPath}")
    private String targetPath;

    public CenERPRouteLocator(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, BaseGatewayProperties baseGatewayProperties, CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider, FilterListProvider filterListProvider) {
        this.customCircuitBreakerFilterFactory = customCircuitBreakerFilterFactory;
        this.customFilter = customFilter;
        this.baseGatewayProperties = baseGatewayProperties;
        this.customCircuitBreakerConfigProvider = customCircuitBreakerConfigProvider;
        this.filterListProvider = filterListProvider;
    }

    @Bean
    public RouteLocator erpRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("baseinfo", r -> r.path("/cenerp/openapi/baseInfo")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("erpServiceBaseInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("dept", r -> r.path("/cenerp/openapi/dept")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("erpServiceDeptCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("company", r -> r.path("/cenerp/openapi/company")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("erpServiceCompanyCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("acntinfo", r -> r.path("/cenerp/openapi/acntinfo")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("erpServiceAcntinfoCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("acnts-map", r -> r.path("/cenerp/openapi/acnts-map")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceAcntsmapCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("ar-collects", r -> r.path("/cenerp/openapi/ar-collects")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceArcollectsCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("vendor-bonds", r -> r.path("/cenerp/openapi/vendor-bonds")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceVendorbondsCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("slipinfos", r -> r.path("/cenerp/openapi/slipinfos")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceSlipinfosCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("vendors", r -> r.path("/cenerp/openapi/vendors")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceVendorsCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("vendors-charges", r -> r.path("/cenerp/openapi/vendors-charges")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceVendorschargesCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("magaminfo-cenerp", r -> r.path("/cenerp/openapi/magaminfo-cenerp")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagaminfoCenerpCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("magam-vendor-bonds", r -> r.path("/cenerp/openapi/magam-vendor-bonds")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagamVendorBondsCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("orderDeptMove", r -> r.path("/cenerp/openapi/orderDeptMove")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceorderDeptMoveCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("dirct_persexp", r -> r.path("/cenerp/openapi/dirct_persexp")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceDirctPersExpCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("dept-acnt-info", r -> r.path("/cenerp/openapi/dept-acnt-info")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceDeptAcntInfoCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("magaminfo-final-cenerp", r -> r.path("/cenerp/openapi/magaminfo-final-cenerp")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagamInfoFinalCenerpCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("project-amt-info", r -> r.path("/cenerp/openapi/project-amt-info")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceProjectamtinfoCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("magaminfo-cenpcs", r -> r.path("/cenerp/openapi/magaminfo-cenpcs")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagaminfocenpcsCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("taxes", r -> r.path("/cenerp/openapi/taxes")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceTaxesCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("magam-pl-bonds", r -> r.path("/cenerp/openapi/magam-pl-bonds")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagamplbondsCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("hometax-status", r -> r.path("/cenerp/openapi/hometax-status")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceHometaxstatusCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("cost-project-info", r -> r.path("/cenerp/openapi/cost-project-info")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceCostprojectinfoCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("reverse-taxes", r -> r.path("/cenerp/openapi/reverse-taxes")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceReversetaxesCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("dept_persexp", r -> r.path("/cenerp/openapi/dept_persexp")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceDeptpersexpCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("exchrate_info", r -> r.path("/cenerp/openapi/exchrate_info")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceExchrateinfoCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("slipinfos-post", r -> r.path("/cenerp/openapi/slipinfos-post")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceSlipinfosPostCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("reverse-taxes-post", r -> r.path("/cenerp/openapi/reverse-taxes-post")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceReverseTaxesPostCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("contUsers-do", r -> r.path("/api/contUsers.do")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceContUsersDoCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("dept_secucen", r -> r.path("/cenerp/openapi/dept_secucen")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceDeptSecucenCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("baseinfo_secucen", r -> r.path("/cenerp/openapi/baseinfo_secucen")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceBaseinfoSecucenCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("order_mm", r -> r.path("/cenerp/openapi/order_mm")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceOrderMMCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("order_info", r -> r.path("/cenerp/openapi/order_info")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceOrderInfoCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("dept_itcgw", r -> r.path("/cenerp/openapi/dept_itcgw")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceDeptItcGWCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("user_itcgw", r -> r.path("/cenerp/openapi/user_itcgw")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceUserItcGWCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("title_itcgw", r -> r.path("/cenerp/openapi/title_itcgw")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceTitleItcGWCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("consolidated_tb", r -> r.path("/cenerp/openapi/consolidated_tb")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceConsolidatedTbCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("consolidated_bond_debt", r -> r.path("/cenerp/openapi/consolidated_bond_debt")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceConsolidatedBondDebtCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("consolidated_sales_purchase", r -> r.path("/cenerp/openapi/consolidated_sales_purchase")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceConsolidatedSalesPurchaseCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("consolidated_profit_cost", r -> r.path("/cenerp/openapi/consolidated_profit_cost")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceConsolidatedProfitCostCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .route("consolidated_rent_borrow", r -> r.path("/cenerp/openapi/consolidated_rent_borrow")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceConsolidatedRentBorrowCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(targetPath))
                .build();
    }

    private CustomCircuitBreakerGatewayFilterFactory.Config getFilterConfig(@Nullable String filterName){
        CustomCircuitBreakerGatewayFilterFactory.Config filterConfig = customCircuitBreakerConfigProvider.getConfig(filterName);
        return filterConfig;
    }
}

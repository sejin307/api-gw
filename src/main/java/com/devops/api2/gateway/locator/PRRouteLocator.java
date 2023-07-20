package com.devops.api2.gateway.locator;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import com.devops.api2.gateway.filter.CustomFilter;
import com.devops.api2.gateway.locator.definition.GatewayPropertiesPOJO;
import com.devops.api2.gateway.locator.provider.CustomCircuitBreakerConfigProvider;
import com.devops.api2.gateway.locator.provider.FilterListProvider;
import jakarta.annotation.Nullable;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

public class PRRouteLocator {


    private final CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory;

    private final CustomFilter customFilter;

    private final GatewayPropertiesPOJO gatewayPropertiesPOJO;
    private final CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider;

    private final FilterListProvider filterListProvider;

    public PRRouteLocator(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, GatewayPropertiesPOJO gatewayPropertiesPOJO, CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider, FilterListProvider filterListProvider) {
        this.customCircuitBreakerFilterFactory = customCircuitBreakerFilterFactory;
        this.customFilter = customFilter;
        this.gatewayPropertiesPOJO = gatewayPropertiesPOJO;
        this.customCircuitBreakerConfigProvider = customCircuitBreakerConfigProvider;
        this.filterListProvider = filterListProvider;
    }

    @Bean
    public RouteLocator prRouteLocator(RouteLocatorBuilder builder) {
        CustomCircuitBreakerGatewayFilterFactory.Config IFPMSERP01Config = customCircuitBreakerConfigProvider.getConfig("CENTerrServiceIF-PMSERP-01CircuitBreaker");

        return builder.routes()
                .route("IFPMSERP01", r -> r.path("/api/pms/inf/act-plan-activity-hnfs")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("CENTerrServiceIF-PMSERP-01CircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri("http://inf-centerrstg.cengroup.co.kr"))
                .build();
    }

    private CustomCircuitBreakerGatewayFilterFactory.Config getFilterConfig(@Nullable String filterName){
        CustomCircuitBreakerGatewayFilterFactory.Config filterConfig = customCircuitBreakerConfigProvider.getConfig(filterName);
        return filterConfig;
    }
}
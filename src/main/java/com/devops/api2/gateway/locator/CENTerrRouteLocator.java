package com.devops.api2.gateway.locator;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import com.devops.api2.gateway.filter.CustomFilter;
import com.devops.api2.gateway.locator.definition.GatewayPropertiesPOJO;
import com.devops.api2.gateway.locator.provider.CustomCircuitBreakerConfigProvider;
import com.devops.api2.gateway.locator.provider.FilterListProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * cloud gateway 의 설정을 동적으로 가져와야하는데 코드가 매우복잡하고,
 * 왠만한 gateway에서 제공하는 메소드 사용법의 이해도가 없다면 코드작성이 매우 어려움.
 * 위의 이유로 Rocator는 yml설정을 Java에서 동적생성 보다  Rocator에서 직접세팅
 *
 * CENTerr에서 제공하는 API를 정의
 */
@Configuration
public class CENTerrRouteLocator {

    private final CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory;

    private final CustomFilter customFilter;

    private final GatewayPropertiesPOJO gatewayPropertiesPOJO;
    private final CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider;

    private final FilterListProvider filterListProvider;

    public CENTerrRouteLocator(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, GatewayPropertiesPOJO gatewayPropertiesPOJO, CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider, FilterListProvider filterListProvider) {
        this.customCircuitBreakerFilterFactory = customCircuitBreakerFilterFactory;
        this.customFilter = customFilter;
        this.gatewayPropertiesPOJO = gatewayPropertiesPOJO;
        this.customCircuitBreakerConfigProvider = customCircuitBreakerConfigProvider;
        this.filterListProvider = filterListProvider;
    }

    @Bean
    public RouteLocator centerrRouteLocator(RouteLocatorBuilder builder) {
        CustomCircuitBreakerGatewayFilterFactory.Config IFPMSERP01Config = customCircuitBreakerConfigProvider.getConfig("CENTerrServiceIF-PMSERP-01CircuitBreaker");

        return builder.routes()
                .route("IFPMSERP01", r -> r.path("/api/pms/inf/act-plan-activity-hnfs")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter, IFPMSERP01Config).toArray(new GatewayFilter[0])))
                        .uri("http://inf-centerrstg.cengroup.co.kr"))
                .build();
    }
}

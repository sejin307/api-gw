package com.devops.api2.gateway.filter;

import com.devops.api2.gateway.model.GatewayLog;
import com.devops.api2.gateway.repository.GatewayLogRepository;
import com.devops.api2.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 라우팅 서비스에서 공통으로 활용하는 필터
 * TODO:Retry 설정 Cache에서 기존 요청정보를 가져와서 call하면 성능향상, 필요에따라 구현
 */
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    @Autowired
    private GatewayLogRepository gatewayLogRepository;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

//    public JWTFilter(TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }


    public CustomFilter(TokenProvider tokenProvider) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
    }

    /**
     * 여기서 실제 filter 로직 실행
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        //before-processing (서비스 시작시 세팅)
        return (exchange, chain) -> {
            //pre-processing ( Route start 할때 )
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        // post-processing
                        String jwt = resolveToken(exchange);
                        String hostName = "";
                        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
                            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                            hostName = authentication.getName();
                        }

                        String requestId = exchange.getRequest().getId();
                        String requestPath = exchange.getRequest().getPath().toString();
                        Integer responseStatus = exchange.getResponse().getStatusCode().value();
                        String remoteAddress = exchange.getRequest().getRemoteAddress().getAddress().toString();
                        //String currentTime = getCurrentTime();

                        saveLogToDb(requestId, requestPath, responseStatus, remoteAddress, hostName);
                    }));
        };
    }

//    public String getCurrentTime(){
//        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar cal = Calendar.getInstance();
//        String date2String = currentDate.format(cal.getTime());
//        return date2String;
//    }

    public static class Config {
        //Config 설정
    }

    private void saveLogToDb(String requestId, String requestPath, Integer responseStatus, String remoteAddress, String hostName) {
        GatewayLog gatewayLog = new GatewayLog();
        gatewayLog.setRequestId(requestId);
        gatewayLog.setRequestPath(requestPath);
        gatewayLog.setResponseStatus(responseStatus);
        gatewayLog.setRemoteAddress(remoteAddress);
        gatewayLog.setHostName(hostName);

        gatewayLogRepository.save(gatewayLog);
    }


    private String resolveToken(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}


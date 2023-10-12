package org.panda.support.gateway.config.filter;

import org.panda.bamboo.common.util.LogUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *  自定义全局过滤器
 *  服务授权，权限认证，日志记录等
 *
 * @author fangen
 **/
//@Component
public class AuthorizeGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LogUtil.info(getClass(), exchange.getRequest().getPath().value());
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> auths = headers.get("authorization");
        if(auths != null && auths.contains("admin")) {
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}

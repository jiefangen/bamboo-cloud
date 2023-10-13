package org.panda.support.gateway.config.filter;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.core.web.config.WebConstants;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *  自定义全局过滤器
 *  权限认证，服务授权，日志记录等
 *
 * @author fangen
 **/
@Component
public class AuthorizeGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger LOGGER = LogUtil.getLogger(AuthorizeGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.debug("Authorize global filter, uri: {}", exchange.getRequest().getPath().value());
        ServerHttpRequest request = exchange.getRequest();
        // 令牌规则校验
        String token = request.getHeaders().getFirst(WebConstants.HEADER_AUTH_JWT);
        if (StringUtils.isNotBlank(token)) {
            // CAS服务器验证令牌

            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        // 执行顺序，值越小越先执行
        return 0;
    }
}

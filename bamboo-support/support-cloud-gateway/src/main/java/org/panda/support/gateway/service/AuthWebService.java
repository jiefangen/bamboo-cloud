package org.panda.support.gateway.service;

import org.panda.support.gateway.service.feign.AuthServerClient;
import org.panda.tech.core.web.config.WebConstants;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Auth认证授权服务
 *
 * @author fangen
 **/
@Component
public class AuthWebService {

    private WebClient authWebClient;

    @Autowired
    private AuthServerClient authServerClient;

    @Autowired
    public AuthWebService(WebClient.Builder webClientBuilder) {
        this.authWebClient = webClientBuilder.baseUrl("lb://ms-auth/").build();
    }

    public Mono<RestfulResult> checkPermission(String token, String service) {
        return authWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/auth/home/index").queryParam("service", service).build())
                .header(WebConstants.HEADER_AUTH_JWT, token)
                .retrieve()
                .bodyToMono(RestfulResult.class);
    }

    public RestfulResult checkPermission(String service) {
        return authServerClient.validate(service);
    }

}

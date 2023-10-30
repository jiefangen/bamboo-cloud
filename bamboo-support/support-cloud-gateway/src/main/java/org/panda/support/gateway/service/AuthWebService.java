package org.panda.support.gateway.service;

import org.panda.tech.core.web.config.WebConstants;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    private WebClient.Builder webClient;

    @Autowired
    public AuthWebService(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    public Mono<RestfulResult> validate(String token, String service) {
        Mono<RestfulResult> result = webClient
                .baseUrl("http://bamboo-cloud-ms01.orb.local:21006/auth/access/validate")
                .build()
                .method(HttpMethod.GET)
                .header(WebConstants.HEADER_AUTH_JWT, token)
                .header("service", service)
                .retrieve()
                .bodyToMono(RestfulResult.class);
        return result;
    }
}

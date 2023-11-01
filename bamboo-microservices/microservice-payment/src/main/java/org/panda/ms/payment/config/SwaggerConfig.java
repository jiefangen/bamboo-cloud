package org.panda.ms.payment.config;

import lombok.Setter;
import org.panda.bamboo.Framework;
import org.panda.bamboo.common.constant.Profiles;
import org.panda.tech.core.config.app.AppConstants;
import org.panda.tech.core.web.config.WebConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;
import java.util.List;

@Setter
@ConfigurationProperties(prefix = "bamboo.swagger.config")
@EnableSwagger2WebMvc
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String SWAGGER_DESC = "支付网关微服务，致力于提供核心支付系统交易，助力上层业务平台。";

    private boolean enabled;
    private String version;
    private String basePackage;
    private String title;

    @Value(AppConstants.EL_SPRING_PROFILES_ACTIVE)
    private String env;

    @Bean
    public Docket defaultApi2() {
        if(Profiles.PRODUCTION.equals(env)) { // 生产环境关闭自动化文档
            this.enabled = false;
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(this.enabled)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContext())
                .securitySchemes(securitySchemes());
    }

    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey(WebConstants.HEADER_AUTH_JWT, WebConstants.HEADER_AUTH_JWT, "header"));
    }

    private List<SecurityContext> securityContext() {
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
        return Collections.singletonList(securityContext);
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(WebConstants.HEADER_AUTH_JWT, authorizationScopes));
    }

    private ApiInfo apiInfo() {
        this.title += "【" + env + "】";
        return new ApiInfoBuilder()
                .title(this.title)
                .description(SWAGGER_DESC)
                .version(this.version)
                .contact(new Contact(Framework.OWNER, "", Framework.EMAIL))
                .license("Apache 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}

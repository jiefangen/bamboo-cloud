package org.panda.support.cloud.example.config;

import lombok.Setter;
import org.panda.bamboo.Framework;
import org.panda.bamboo.common.constant.Profiles;
import org.panda.tech.core.config.app.AppConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@ConfigurationProperties(prefix = "bamboo.swagger.config")
@EnableSwagger2WebMvc
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String SWAGGER_DESC = "微服务实践样例，基于SpringCloud的能力实践。";

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
                .build();
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

    /**
     * 解决springboot升到2.6.x之后，knife4j报错
     * 适配spring-boot-starter-actuator依赖
     *
     * @param webEndpointsSupplier        the web endpoints supplier
     * @param servletEndpointsSupplier    the servlet endpoints supplier
     * @param controllerEndpointsSupplier the controller endpoints supplier
     * @param endpointMediaTypes          the endpoint media types
     * @param corsEndpointProperties      the cors properties
     * @param webEndpointProperties       the web endpoints properties
     * @param environment                 the environment
     * @return the web mvc endpoint handler mapping
     */
    @Bean
    public WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier,
                                                                     ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes,
                                                                     CorsEndpointProperties corsEndpointProperties, WebEndpointProperties webEndpointProperties,
                                                                     Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsEndpointProperties.toCorsConfiguration(), new EndpointLinksResolver(
                allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    /**
     * shouldRegisterLinksMapping
     *
     * @param webEndpointProperties
     * @param environment
     * @param basePath
     * @return
     */
    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}

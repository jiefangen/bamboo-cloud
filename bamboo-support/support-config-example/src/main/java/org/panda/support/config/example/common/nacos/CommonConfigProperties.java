package org.panda.support.config.example.common.nacos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "nacos.common.config")
@Configuration
@RefreshScope
public class CommonConfigProperties {

    private String name;

    private Boolean appLocalCache;
}

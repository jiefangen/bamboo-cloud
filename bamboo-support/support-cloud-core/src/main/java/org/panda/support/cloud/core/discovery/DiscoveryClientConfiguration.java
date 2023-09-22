package org.panda.support.cloud.core.discovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.panda.support.cloud.core.loadbalancer.DefaultServiceInstanceListFilter;
import org.panda.tech.core.version.VersionReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscoveryClientConfiguration {

    @Autowired
    private VersionReader versionReader;

    @Autowired
    public void setNacosDiscoveryProperties(NacosDiscoveryProperties nacosDiscoveryProperties) {
        nacosDiscoveryProperties.getMetadata().put(DefaultServiceInstanceListFilter.METADATA_VERSION,
                this.versionReader.getVersionText());
    }

}

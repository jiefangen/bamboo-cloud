package org.panda.support.cloud.core.loadbalancer;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancer;
import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration;
import org.panda.bamboo.core.beans.factory.WrappedObjectProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Import(NacosLoadBalancerClientConfiguration.BlockingSupportConfiguration.class)
public class LoadBalancerConfiguration extends NacosLoadBalancerClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ServiceInstanceListFilter serviceInstanceListFilter() {
        return new DefaultServiceInstanceListFilter();
    }

    @Override
    public ReactorLoadBalancer<ServiceInstance> nacosLoadBalancer(Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory, NacosDiscoveryProperties nacosDiscoveryProperties) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        ObjectProvider<ServiceInstanceListSupplier> supplierProvider = loadBalancerClientFactory.getLazyProvider(name,
                ServiceInstanceListSupplier.class);
        ServiceInstanceListFilter filter = serviceInstanceListFilter();
        supplierProvider = new WrappedObjectProvider<>(supplierProvider,
                supplier -> new FilterableServiceInstanceListSupplier(supplier, filter));
        return new NacosLoadBalancer(supplierProvider, name, nacosDiscoveryProperties);
    }

}

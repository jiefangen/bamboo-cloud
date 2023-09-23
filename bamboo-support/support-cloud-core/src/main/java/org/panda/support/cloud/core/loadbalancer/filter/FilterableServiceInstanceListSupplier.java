package org.panda.support.cloud.core.loadbalancer.filter;

import org.apache.commons.collections4.CollectionUtils;
import org.panda.support.cloud.core.loadbalancer.filter.ServiceInstanceListFilter;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.DelegatingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 可过滤的微服务实例清单供应者
 */
public class FilterableServiceInstanceListSupplier extends DelegatingServiceInstanceListSupplier {

    private ServiceInstanceListFilter filter;

    public FilterableServiceInstanceListSupplier(ServiceInstanceListSupplier delegate,
            ServiceInstanceListFilter filter) {
        super(delegate);
        this.filter = filter;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        Flux<List<ServiceInstance>> flux = this.delegate.get();
        if (this.filter != null) {
            flux = flux.map(serviceInstances -> {
                List<ServiceInstance> result = this.filter.filter(serviceInstances);
                // 如果过滤后的结果清单为空，则仍然使用原清单
                if (CollectionUtils.isEmpty(result)) {
                    result = serviceInstances;
                }
                return result;
            });
        }
        return flux;
    }

}

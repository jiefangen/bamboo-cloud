package org.panda.support.cloud.core.loadbalancer.filter;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.bamboo.common.util.lang.StringUtil;
import org.panda.tech.core.version.Version;
import org.panda.tech.core.web.util.NetUtil;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认微服务实例清单过滤器
 */
public class DefaultServiceInstanceListFilter implements ServiceInstanceListFilter {

    public static final String METADATA_VERSION = "bamboo.discovery.version";

    private static final List<String> LOCAL_INTRANET_IPS = NetUtil.getLocalIntranetIps();

    @Override
    public List<ServiceInstance> filter(List<ServiceInstance> serviceInstances) {
        // 优先使用本机的微服务实例，其次使用最低版本的微服务实例，因为最低版本往往意味着更稳定，是否使用更新版本由业务自定义决定
        List<ServiceInstance> result = serviceInstances.stream().filter(this::isPrior).collect(Collectors.toList());
        if (result.isEmpty()) {
            LogUtil.info(getClass(), "Service instance host: {}. Local intranet ips: {}",
                    StringUtil.join(serviceInstances, ", ", ServiceInstance::getHost),
                    StringUtil.join(LOCAL_INTRANET_IPS, ", ", String::toString));
            result = filterByVersion(serviceInstances);
        }
        return result;
    }

    protected boolean isPrior(ServiceInstance serviceInstance) {
        String host = serviceInstance.getHost();
        return NetUtil.isLocalHost(host) || LOCAL_INTRANET_IPS.contains(host);
    }

    protected List<ServiceInstance> filterByVersion(List<ServiceInstance> serviceInstances) {
        List<ServiceInstance> result = new ArrayList<>();
        Version lowestVersion = null;
        for (ServiceInstance serviceInstance : serviceInstances) {
            String versionValue = serviceInstance.getMetadata().get(METADATA_VERSION);
            if (StringUtils.isNotBlank(versionValue)) {
                Version version = new Version(versionValue);
                if (lowestVersion == null) { // 第一个有版本号的实例，直接添加实例到结果集，并缓存最低版本号
                    result.add(serviceInstance);
                    lowestVersion = version;
                } else {
                    int compared = lowestVersion.compareTo(version);
                    if (compared > 0) { // 缓存的最低版本高于当前实例版本，则清除已有结果集，加入当前实例，并更新最低版本号
                        result.clear();
                        result.add(serviceInstance);
                        lowestVersion = version;
                    } else if (compared == 0) { // 缓存的最低版本等于当前实例版本，则简单添加实例至结果集即可
                        result.add(serviceInstance);
                    }
                    // 缓存的最低版本低于当前实例版本，则忽略
                }
            }
        }
        return result;
    }

}

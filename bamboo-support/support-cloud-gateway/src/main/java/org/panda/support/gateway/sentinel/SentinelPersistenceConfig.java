package org.panda.support.gateway.sentinel;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.config.DataSourcePropertiesConfiguration;
import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 限流规则持久化
 *
 * @author fangen
 **/
@Configuration
@Order(20)
public class SentinelPersistenceConfig {

    @Autowired
    private SentinelProperties sentinelProperties;

    @Bean
    public SentinelPersistenceConfig initialize() {
        loadGatewayRule();
        return new SentinelPersistenceConfig();
    }

    private void loadGatewayRule() {
        Set<Map.Entry<String, DataSourcePropertiesConfiguration>> sentinelDataSources =
                sentinelProperties.getDatasource().entrySet();
        List<Map.Entry<String, DataSourcePropertiesConfiguration>> flowDataSources = sentinelDataSources.stream()
                .filter(map -> map.getValue().getNacos() != null && "gw-flow".equals(map.getKey()))
                .collect(Collectors.toList());
        List<Map.Entry<String, DataSourcePropertiesConfiguration>> apiGroupDataSources = sentinelDataSources.stream()
                .filter(map -> map.getValue().getNacos() != null && "gw-api-group".equals(map.getKey()))
                .collect(Collectors.toList());
        initGatewayRules(flowDataSources);
//        initCustomizedApis(apiGroupDataSources);
    }

    private void initCustomizedApis(List<Map.Entry<String, DataSourcePropertiesConfiguration>> apiGroupDataSources) {
        if (CollectionUtils.isNotEmpty(apiGroupDataSources)) {
            apiGroupDataSources.forEach(map -> {
                NacosDataSourceProperties nacos = map.getValue().getNacos();
                ReadableDataSource<String, Set<ApiDefinition>> apisDataSource = new NacosDataSource<>(
                        nacos.getServerAddr(), nacos.getGroupId(), nacos.getDataId(),
                        source -> JSON.parseObject(source, new TypeReference<Set<ApiDefinition>>() {
                        }));
                GatewayApiDefinitionManager.register2Property(apisDataSource.getProperty());
            });
        }
    }

    private void initGatewayRules(List<Map.Entry<String, DataSourcePropertiesConfiguration>> flowDataSources) {
        if (CollectionUtils.isNotEmpty(flowDataSources)) {
            flowDataSources.forEach(map -> {
                        NacosDataSourceProperties nacos = map.getValue().getNacos();
                        ReadableDataSource<String, Set<GatewayFlowRule>> flowRuleDataSource = new NacosDataSource<>(
                                nacos.getServerAddr(), nacos.getGroupId(), nacos.getDataId(),
                                source -> JSON.parseObject(source, new TypeReference<Set<GatewayFlowRule>>() {}));
                 GatewayRuleManager.register2Property(flowRuleDataSource.getProperty());
            });
        }
    }

}

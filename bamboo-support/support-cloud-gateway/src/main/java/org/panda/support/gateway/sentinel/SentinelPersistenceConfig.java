package org.panda.support.gateway.sentinel;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.config.DataSourcePropertiesConfiguration;
import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.panda.tech.core.web.config.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 限流规则持久化
 * 该规则配置可在配置文件中直接配置实现
 * 此处仅做配置字段的释义
 *
 * @author fangen
 **/
//@Configuration
public class SentinelPersistenceConfig {

    @Autowired
    private SentinelProperties sentinelProperties;

    @Bean
    public SentinelPersistenceConfig initialize() {
        loadGatewayRule();
        return new SentinelPersistenceConfig();
    }

    /**
     * 自定义网关限流API分组
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition anonymousApi = new ApiDefinition()
                 // 【必】apiName：api名称
                .setApiName("anonymous_api")
                 // 【必】predicateItems：匹配规则
                .setPredicateItems(new HashSet<>() {{
                    add(new ApiPathPredicateItem()
                            // 【必】pattern：匹配串：参数值的匹配模式，只有匹配该模式的请求属性值会纳入统计和流控；若为空则统计该请求属性的所有值
                            .setPattern("/*/home/**")
                            // 【必】matchStrategy：匹配策略：0-精确；1-前缀；2-正则
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                }});
        // 对应的json样例
        //        [
        //            {
        //                "apiName": "anonymous_api",
        //                    "predicateItems": [
        //                    {
        //                        "pattern": "/*/home/**",
        //                            "matchStrategy": 1
        //                    },
        //                    {
        //                        "pattern": "/*/auth/home/**",
        //                            "matchStrategy": 1
        //                    }
        //                ]
        //            }
        //        ]
        definitions.add(anonymousApi);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * 自定义网关限流规则
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule()
                 // 【必】resource：资源名称，可以是网关中的route名称或者用户自定义的API分组名称
                .setResource("anonymous_api")
                 // 【选】resourceMode：资源模式（默认为0），0-route模式；1-自定义api模式
                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID)
                 // 【选】grade：限流阈值类型（默认为1），0-根据线程并发数量来限流；1-根据QPS来进行流量控制
                .setGrade(1)
                 // 【必】count：限流阈值
                .setCount(3)
                 // 下面配置仅在限流阈值类型为QPS类型下生效
                 // 【选】intervalSec：间隔（默认是1秒），统计时间窗口，单位是秒
                .setIntervalSec(1)
                 // 【选】controlBehavior：流控方式（默认为0），0-快速失败；1-匀速排队
                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT)
                 // burst：仅在流控方式为快速失败模式下生效，应对突发请求时额外允许的请求数目
                .setBurst(10)
                // maxQueueingTimeoutMs：仅在流控方式为匀速排队模式下生效，匀速排队模式下的最长排队时间，单位是毫秒
                .setMaxQueueingTimeoutMs(600)
                 // paramItem：参数限流配置。若不提供，则代表不针对参数进行限流，该网关规则将会被转换成普通流控规则
                .setParamItem(new GatewayParamFlowItem()
                         // parseStrategy：从请求中提取参数的策略，0-来源IP；1-来源Host；2-任意Header；3-任意URL参数；4-任意Cookie
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
                         // fieldName：若提取策略选择Header模式或URL参数模式，则需要指定对应的header名称或URL参数名称
                        .setFieldName(WebConstants.HEADER_AUTH_JWT)
                         // pattern：匹配串：参数值的匹配模式，只有匹配该模式的请求属性值会纳入统计和流控；若为空则统计该请求属性的所有值
                        .setPattern("/*/home/**")
                         // matchStrategy：匹配策略：0-精确匹配；3-子串匹配；2-正则匹配
                        .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_EXACT)
                )
        );
        GatewayRuleManager.loadRules(rules);
    }

    private void loadGatewayRule() {
        Set<Map.Entry<String, DataSourcePropertiesConfiguration>> sentinelDataSources =
                sentinelProperties.getDatasource().entrySet();
        List<Map.Entry<String, DataSourcePropertiesConfiguration>> flowDataSources = sentinelDataSources.stream()
                .filter(map -> map.getValue().getNacos() != null && "gw-flow".equals(map.getKey()))
                .collect(Collectors.toList());
//        initGatewayRules(flowDataSources);
        // 自定义网关限流规则
        initCustomizedApis();
        initGatewayRules();
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

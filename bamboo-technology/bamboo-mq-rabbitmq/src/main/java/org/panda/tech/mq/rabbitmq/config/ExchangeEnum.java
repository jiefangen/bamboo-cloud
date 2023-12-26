package org.panda.tech.mq.rabbitmq.config;

import org.panda.bamboo.common.annotation.Caption;
import org.panda.bamboo.common.annotation.EnumValue;

/**
 * 交换机类型枚举
 */
public enum ExchangeEnum {

    @Caption("直连模式")
    @EnumValue("direct")
    DIRECT,

    @Caption("主题模式")
    @EnumValue("topic")
    TOPIC,

    @Caption("头模式")
    @EnumValue("headers")
    HEADERS,

    @Caption("广播模式")
    @EnumValue("fanout")
    FANOUT

}

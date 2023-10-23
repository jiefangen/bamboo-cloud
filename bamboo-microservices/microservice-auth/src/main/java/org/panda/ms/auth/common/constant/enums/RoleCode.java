package org.panda.ms.auth.common.constant.enums;

import org.panda.bamboo.common.annotation.Caption;

/**
 * 角色编码
 */
public enum RoleCode {

    @Caption("顶级管理员")
    ADMIN,

    @Caption("系统管理员")
    SYSTEM,

    @Caption("监控管理员")
    ACTUATOR,

    @Caption("特性用户")
    USER,

    @Caption("普通用户")
    GENERAL,

    @Caption("访客")
    CUSTOMER;

}

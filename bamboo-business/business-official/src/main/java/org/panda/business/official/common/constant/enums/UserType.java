package org.panda.business.official.common.constant.enums;

import org.panda.bamboo.common.annotation.Caption;

/**
 * 系统全局用户类型
 */
public enum UserType {

    @Caption("管理员")
    MANAGER,

    @Caption("系统用户")
    SYSTEM,

    @Caption("普通用户")
    GENERAL,

    @Caption("访客")
    CUSTOMER;

}

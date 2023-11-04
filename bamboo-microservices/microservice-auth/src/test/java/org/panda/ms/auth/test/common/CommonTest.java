package org.panda.ms.auth.test.common;

import org.junit.jupiter.api.Test;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.tech.core.util.CommonUtil;

/**
 * 常用工具类测试
 **/
public class CommonTest {

    @Test
    void getDefaultPermission() {
        String uri = "/payment/prepare/{gateway}/{gatewayName}/{gatewayName}/{gatewayName}/{gatewayName}/{gatewayName}/{gatewayName}";
        String defaultPermission = CommonUtil.getDefaultPermission(uri);
        if (uri.contains(Strings.LEFT_BRACE) && uri.contains(Strings.RIGHT_BRACE) ) {
            defaultPermission += "_*";
        }
        System.out.println(defaultPermission);
    }

}

package org.panda.ms.auth.common.util;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;

/**
 * 全局通用性工具类
 *
 * @author fangen
 **/
public class CommonUtil {

    public static String getPermissionCode(String url) {
        // 确保头尾都有/
        url = StringUtils.wrapIfMissing(url, Strings.SLASH);
        // 移除可能包含的路径变量
        if (url.endsWith("/{id}/")) { // 以路径变量id结尾的，默认视为detail
            url = url.replaceAll("/\\{id\\}/", "/detail/");
        }
        url = url.replaceAll("/\\{[^}]*\\}/", Strings.SLASH);
        // 去掉头尾的/
        url = StringUtils.strip(url, Strings.SLASH);
        // 替换中间的/为_
        return url.replaceAll(Strings.SLASH, Strings.UNDERLINE);
    }

}

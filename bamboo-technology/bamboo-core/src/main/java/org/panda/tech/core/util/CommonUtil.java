package org.panda.tech.core.util;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;

/**
 * 全局通用性工具类
 **/
public class CommonUtil {

    /**
     * 获取默认权限code
     *
     * @param uri 服务uri
     * @return 权限code
     */
    public static String getDefaultPermission(String uri) {
        // 确保头尾都有/
        uri = StringUtils.wrapIfMissing(uri, Strings.SLASH);
        // 移除可能包含的路径变量
        if (uri.endsWith("/{id}/")) { // 以路径变量id结尾的，默认视为detail
            uri = uri.replaceAll("/\\{id\\}/", "/detail/");
        }
        // 递归移除路径变量
        uri = removePathVariable(uri);
        // 去掉头尾的/
        uri = StringUtils.strip(uri, Strings.SLASH);
        // 替换中间的/为_
        return uri.replaceAll(Strings.SLASH, Strings.UNDERLINE);
    }

    /**
     * 移除路径变量
     */
    public static String removePathVariable(String uri) {
        if (uri.contains(Strings.LEFT_BRACE) && uri.contains(Strings.RIGHT_BRACE)) {
            uri = uri.replaceAll("/\\{[^}]*\\}/", Strings.SLASH);
        } else {
            return uri;
        }
        return removePathVariable(uri);
    }

}

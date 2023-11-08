package org.panda.support.gateway.common.model;

import lombok.Data;
import org.panda.bamboo.common.model.DomainModel;

/**
 * 网关访问审计模型
 *
 * @author fangen
 **/
@Data
public class AccessRecord implements DomainModel {
    /**
     * 请求路径
     */
    private String path;
    /**
     * 请求schema: http/https
     */
    private String scheme;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 路由服务地址
     */
    private String targetUri;
    /**
     * 请求远程地址
     */
    private String remoteAddress;
    /**
     * 请求头
     */
    private String headers;
}

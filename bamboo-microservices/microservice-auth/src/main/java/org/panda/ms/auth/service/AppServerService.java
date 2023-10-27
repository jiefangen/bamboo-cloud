package org.panda.ms.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.panda.ms.auth.model.entity.AppServer;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <p>
 * 应用服务 服务类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-10-25
 */
public interface AppServerService extends IService<AppServer> {

    /**
     * 服务权限验证
     *
     * @param service 服务资源
     * @param grantedAuthorities 访问者权限集
     * @return 验证结果
     */
    boolean permissionVerification(String service, Collection<? extends GrantedAuthority> grantedAuthorities);

}

package org.panda.ms.auth.service;

import org.panda.ms.auth.model.entity.AuthRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 应用认证角色 服务类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-10-25
 */
public interface AuthRoleService extends IService<AuthRole> {

    Set<String> getPermissions(Set<String> roleCodes);

}

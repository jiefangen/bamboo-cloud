package org.panda.service.auth.service;

import org.panda.service.auth.model.entity.AuthRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.panda.support.cloud.core.security.model.AppServiceModel;

import java.util.List;

/**
 * <p>
 * 角色权限关系 服务类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-11-04
 */
public interface AuthRolePermissionService extends IService<AuthRolePermission> {

    void initPermissions(List<AppServiceModel.Permission> permissions, Integer appServerId, String appName);

}

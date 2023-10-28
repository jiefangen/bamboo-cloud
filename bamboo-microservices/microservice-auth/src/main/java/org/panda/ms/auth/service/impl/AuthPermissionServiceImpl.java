package org.panda.ms.auth.service.impl;

import org.panda.ms.auth.model.entity.AuthPermission;
import org.panda.ms.auth.repository.AuthPermissionMapper;
import org.panda.ms.auth.service.AuthPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 应用资源权限 服务实现类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-10-25
 */
@Service
public class AuthPermissionServiceImpl extends ServiceImpl<AuthPermissionMapper, AuthPermission> implements AuthPermissionService {

    @Override
    public Set<String> getPermissions(Set<String> roleCodes) {
        return this.baseMapper.selectPermissionsByRoleCodes(roleCodes);
    }

}

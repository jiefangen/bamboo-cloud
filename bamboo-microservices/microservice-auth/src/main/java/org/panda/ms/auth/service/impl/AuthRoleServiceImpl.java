package org.panda.ms.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.panda.ms.auth.model.entity.AuthRole;
import org.panda.ms.auth.repository.AuthRoleMapper;
import org.panda.ms.auth.service.AuthRoleService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 应用认证角色 服务实现类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-10-25
 */
@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole> implements AuthRoleService {

    @Override
    public Set<String> getPermissions(Set<String> roleCodes) {
        return this.baseMapper.selectPermissionsByRoleCodes(roleCodes);
    }
}

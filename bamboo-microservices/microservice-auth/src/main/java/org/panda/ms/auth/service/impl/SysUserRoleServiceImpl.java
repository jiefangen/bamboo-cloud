package org.panda.ms.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.panda.ms.auth.model.dto.SysUserDto;
import org.panda.ms.auth.model.entity.SysRole;
import org.panda.ms.auth.model.entity.SysUser;
import org.panda.ms.auth.model.entity.SysUserRole;
import org.panda.ms.auth.repository.SysUserRoleMapper;
import org.panda.ms.auth.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户角色关系 服务实现类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-06-07
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public SysUserDto getUserAndRoles(String username) {
        SysUser userParam = new SysUser();
        userParam.setUsername(username);
        SysUserDto sysUserDto = this.baseMapper.findUserAndRoles(userParam);
        if (sysUserDto == null) {
            return null;
        }
        List<SysRole> roles = sysUserDto.getRoles();
        if(CollectionUtils.isNotEmpty(roles)) {
            Set<String> roleCodes = roles.stream().map(role -> role.getRoleCode()).collect(Collectors.toSet());
            sysUserDto.setRoleCodes(roleCodes);
        }
        return sysUserDto;
    }

}

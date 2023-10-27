package org.panda.ms.auth.repository;

import org.apache.ibatis.annotations.Param;
import org.panda.ms.auth.model.entity.AuthRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 应用认证角色 Mapper 接口
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-10-25
 */
public interface AuthRoleMapper extends BaseMapper<AuthRole> {

    Set<String> selectPermissionsByRoleCodes(@Param("roleCodes") Set<String> roleCodes);

}

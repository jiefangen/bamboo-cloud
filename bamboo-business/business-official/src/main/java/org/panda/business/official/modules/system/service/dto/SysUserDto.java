package org.panda.business.official.modules.system.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.panda.bamboo.common.model.entity.Unity;
import org.panda.business.official.modules.system.service.entity.SysRole;
import org.panda.business.official.modules.system.service.entity.SysUser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class SysUserDto implements Unity<String>, Serializable {

    private static final long serialVersionUID = -4934171750565309043L;

    private Integer userId;
    private SysUser user;
    private List<SysRole> roles;
    private Set<String> roleCodes = new HashSet<>();

    @Override
    public String getId() {
        return this.user.getUsername();
    }

}

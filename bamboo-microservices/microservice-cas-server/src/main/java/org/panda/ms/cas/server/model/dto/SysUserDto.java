package org.panda.ms.cas.server.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.panda.ms.cas.server.model.entity.SysRole;
import org.panda.ms.cas.server.model.entity.SysUser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class SysUserDto implements Serializable {

    private static final long serialVersionUID = -4934171750565309043L;

    private Integer userId;
    private SysUser user;
    private List<SysRole> roles;
    private Set<String> roleCodes = new HashSet<>();

}

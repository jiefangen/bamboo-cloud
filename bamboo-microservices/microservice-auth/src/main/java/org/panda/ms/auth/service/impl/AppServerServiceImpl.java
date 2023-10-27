package org.panda.ms.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.panda.ms.auth.model.entity.AppServer;
import org.panda.ms.auth.repository.AppServerMapper;
import org.panda.ms.auth.service.AppServerService;
import org.panda.ms.auth.service.AuthRoleService;
import org.panda.tech.core.web.context.SpringWebContext;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.security.cas.CasConstants;
import org.panda.tech.security.user.UserGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Set;

/**
 * <p>
 * 应用服务 服务实现类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-10-25
 */
@Service
public class AppServerServiceImpl extends ServiceImpl<AppServerMapper, AppServer> implements AppServerService {

    @Autowired
    private AuthRoleService authRoleService;

    @Override
    public boolean permissionVerification(String service, Collection<? extends GrantedAuthority> grantedAuthorities) {
        if (StringUtils.isBlank(service)) {
            // 尝试从请求头header中获取
            HttpServletRequest request = SpringWebContext.getRequest();
            if (request != null) {
                service = WebHttpUtil.getHeader(request, CasConstants.PARAMETER_SERVICE);
            }
        }
        if (StringUtils.isNotBlank(service)) {
            for (GrantedAuthority grantedAuthority : grantedAuthorities) {
                if (grantedAuthority instanceof UserGrantedAuthority) {
                    UserGrantedAuthority userGrantedAuthority = (UserGrantedAuthority) grantedAuthority;
                    Set<String> roleCodes = userGrantedAuthority.getPermissions();
                    if (CollectionUtils.isNotEmpty(roleCodes)) {
                        Set<String> permissions = authRoleService.getPermissions(roleCodes);
                        if (CollectionUtils.isNotEmpty(permissions)) {
                            return permissions.contains(service);
                        }
                    }
                }
            }
        }
        return false;
    }
}

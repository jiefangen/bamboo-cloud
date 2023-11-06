package org.panda.ms.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.Commons;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.lang.StringUtil;
import org.panda.ms.auth.model.entity.AppServer;
import org.panda.ms.auth.model.entity.AuthPermission;
import org.panda.ms.auth.model.entity.AuthRole;
import org.panda.ms.auth.model.entity.AuthRolePermission;
import org.panda.ms.auth.repository.AppServerMapper;
import org.panda.ms.auth.service.AppServerService;
import org.panda.ms.auth.service.AuthPermissionService;
import org.panda.ms.auth.service.AuthRolePermissionService;
import org.panda.ms.auth.service.AuthRoleService;
import org.panda.support.cloud.core.security.authority.AppConfigAuthority;
import org.panda.support.cloud.core.security.model.AppServiceModel;
import org.panda.support.cloud.core.security.model.enums.AuthRoleCode;
import org.panda.tech.core.util.CommonUtil;
import org.panda.tech.core.web.context.SpringWebContext;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.security.cas.CasConstants;
import org.panda.tech.security.user.UserGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private AuthPermissionService authPermissionService;
    @Autowired
    private AuthRoleService authRoleService;
    @Autowired
    private AuthRolePermissionService authRolePermissionService;

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
                        Set<String> permissions = authPermissionService.getPermissions(roleCodes);
                        if (CollectionUtils.isNotEmpty(permissions)) {
                            // 服务授权鉴定
                            return isGranted(permissions, service);
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isGranted(Set<String> permissions, String service) {
        String apiCode = CommonUtil.getDefaultPermission(service).toUpperCase();
        return StringUtil.antPathMatchOneOf(apiCode, permissions);
    }

    @Override
    public String initServicePermission(AppServiceModel appServiceModel) {
        String appName = appServiceModel.getAppName();
        String appCode = appName.toUpperCase();
        LambdaQueryWrapper<AppServer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppServer::getAppCode, appCode);
        AppServer appServer = this.getOne(queryWrapper);
        if (appServer == null) {
            // 应用服务注册激活
            AppServer appServerParam = new AppServer();
            appServerParam.setAppName(appName);
            appServerParam.setAppCode(appCode);
            appServerParam.setStatus(1);
            appServerParam.setCaption(appServiceModel.getCaption());
            appServerParam.setBusiness(appServiceModel.getBusiness());
            appServerParam.setScope(appServiceModel.getScope());
            boolean retBool = this.save(appServerParam);
            if (retBool) { // 保存成功
                appServer = this.getOne(queryWrapper);
            }
        }
        if (appServer != null) { // 还未获取到应用服务则本次初始化失败
            return appName + " service registration and save failed";
        }

        this.savePermissions(appServiceModel.getPermissions(), appServer.getId(), appName);
        return Commons.RESULT_SUCCESS;
    }

    private void savePermissions(List<AppServiceModel.Permission> permissions, Integer appServerId, String appName) {
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.forEach(permission -> {
                String resources = permission.getApi();
                String permissionName = CommonUtil.getDefaultPermission(resources);
                if (resources.contains(Strings.LEFT_BRACE) && resources.contains(Strings.RIGHT_BRACE)) {
                    permissionName += Strings.UNDERLINE + Strings.ASTERISK;
                }
                String permissionCode = permissionName.toUpperCase();
                AuthPermission permissionParam = new AuthPermission();
                permissionParam.setPermissionName(permissionName);
                permissionParam.setPermissionCode(permissionCode);
                permissionParam.setResourcesId(appServerId);
                permissionParam.setResourcesType(permission.getResourcesType());
                permissionParam.setSource(appName);
                permissionParam.setResources(resources);
                authPermissionService.saveOrUpdate(permissionParam);

                LambdaQueryWrapper<AuthPermission> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(AuthPermission::getPermissionCode, permissionCode);
                queryWrapper.eq(AuthPermission::getSource, appName);
                AuthPermission authPermission = authPermissionService.getOne(queryWrapper);
                if (authPermission != null) {
                    this.saveAuthPerRelationship(authPermission.getId(), permission.getAppConfigAuthorities());
                }
            });
        }
    }

    private void saveAuthPerRelationship(Integer permissionId, Collection<AppConfigAuthority> configAuthorities) {
        if (CollectionUtils.isNotEmpty(configAuthorities)) {
            configAuthorities.forEach(configAuthority -> {
                String rolePermission = configAuthority.getPermission();
                List<String> rolePermissions = new ArrayList<>();
                if (StringUtils.isNotEmpty(rolePermission)) {
                    if (rolePermission.contains(Strings.COMMA)) {
                        rolePermissions.addAll(Arrays.asList(rolePermission.split(Strings.COMMA)));
                    } else {
                        rolePermissions.add(rolePermission);
                    }
                }
                // 加入管理员角色权限，默认管理员拥有全部资源权限
                rolePermissions.addAll(AuthRoleCode.getManagerRoles());
                for (String roleCode : rolePermissions) {
                    AuthRolePermission authRolePermission = new AuthRolePermission();
                    authRolePermission.setPermissionId(permissionId);
                    LambdaQueryWrapper<AuthRole> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(AuthRole::getRoleCode, roleCode);
                    AuthRole authRole = authRoleService.getOne(queryWrapper, false);
                    if (authRole != null) {
                        authRolePermission.setRoleId(authRole.getId());
                        authRolePermissionService.saveOrUpdate(authRolePermission);
                    }
                }
            });
        }
    }

}

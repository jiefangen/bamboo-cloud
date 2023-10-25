package org.panda.ms.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.panda.ms.auth.model.dto.AuthAccountDto;
import org.panda.ms.auth.model.entity.AuthAccount;
import org.panda.ms.auth.model.entity.AuthRole;
import org.panda.ms.auth.repository.AuthAccountMapper;
import org.panda.ms.auth.service.AuthAccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 应用认证账户 服务实现类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-10-25
 */
@Service
public class AuthAccountServiceImpl extends ServiceImpl<AuthAccountMapper, AuthAccount> implements AuthAccountService {

    @Override
    public AuthAccountDto getAccountAndRoles(String account) {
        AuthAccount accountParam = new AuthAccount();
        accountParam.setAccount(account);
        AuthAccountDto authAccountDto = this.baseMapper.findAccountAndRoles(accountParam);
        if (authAccountDto == null) {
            return null;
        }
        List<AuthRole> roles = authAccountDto.getRoles();
        if(CollectionUtils.isNotEmpty(roles)) {
            Set<String> roleCodes = roles.stream().map(role -> role.getRoleCode()).collect(Collectors.toSet());
            authAccountDto.setRoleCodes(roleCodes);
        }
        return authAccountDto;
    }
}

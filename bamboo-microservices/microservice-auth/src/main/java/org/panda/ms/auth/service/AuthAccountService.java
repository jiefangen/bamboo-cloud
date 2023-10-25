package org.panda.ms.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.panda.ms.auth.model.dto.AuthAccountDto;
import org.panda.ms.auth.model.entity.AuthAccount;

/**
 * <p>
 * 应用认证账户 服务类
 * </p>
 *
 * @author bamboo-code-generator
 * @since 2023-10-25
 */
public interface AuthAccountService extends IService<AuthAccount> {

    AuthAccountDto getAccountAndRoles(String account);

}

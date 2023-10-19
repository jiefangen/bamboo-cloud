package org.panda.support.cas.server.ticket;

import org.panda.tech.security.user.UserSpecificDetails;

/**
 * CAS用户特性细节转换器，用于CAS服务端向客户端传输用户特性细节前，对用户特性细节进行转换处理
 */
public interface CasUserDetailsConverter {

    UserSpecificDetails<?> convert(String app, UserSpecificDetails<?> userDetails);

}

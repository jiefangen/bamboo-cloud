package org.panda.support.sso.controller;

import io.swagger.annotations.Api;
import org.panda.tech.cas.server.controller.CasServerLoginControllerSupport;
import org.springframework.stereotype.Controller;

/**
 * CAS服务登录控制器
 *
 * @author fangen
 **/
@Api(tags = "CAS服务端登录控制器")
@Controller
public class CasServerLoginController extends CasServerLoginControllerSupport {

}

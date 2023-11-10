package org.panda.support.cloud.example.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.swagger.annotations.Api;
import org.panda.tech.core.web.controller.HomeControllerSupport;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "匿名服务问候语")
@Controller
public class HomeController extends HomeControllerSupport {

    @SentinelResource("anonymous")
    public RestfulResult<String> home() {
        return super.home();
    }

    @SentinelResource("anonymous")
    public ModelAndView index(HttpServletRequest request) {
        return super.index(request);
    }

}

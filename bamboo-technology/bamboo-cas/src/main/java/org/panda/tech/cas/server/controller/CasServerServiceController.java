package org.panda.tech.cas.server.controller;

import org.jasig.cas.client.validation.Assertion;
import org.panda.tech.cas.server.service.CasServiceManager;
import org.panda.tech.cas.server.ticket.CasTicketManager;
import org.panda.tech.security.config.annotation.ConfigAnonymous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Cas服务端服务控制器
 */
@Controller
public class CasServerServiceController {

    @Autowired
    private CasServiceManager serviceManager;
    @Autowired
    private CasTicketManager ticketManager;

    @GetMapping("/serviceValidate")
    @ConfigAnonymous
    @ResponseBody
    public Assertion serviceValidate(@RequestParam("service") String service, @RequestParam("ticket") String ticket) {
        service = URLDecoder.decode(service, StandardCharsets.UTF_8);
        String app = this.serviceManager.getAppName(service);
        return this.ticketManager.validateAppTicket(app, ticket);
    }

}

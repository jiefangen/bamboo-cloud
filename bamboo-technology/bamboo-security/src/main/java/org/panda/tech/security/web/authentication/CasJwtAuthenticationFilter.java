package org.panda.tech.security.web.authentication;

import org.panda.tech.core.web.config.WebConstants;
import org.panda.tech.core.webmvc.jwt.JwtParser;
import org.panda.tech.security.authentication.UserSpecificDetailsAuthenticationToken;
import org.panda.tech.security.user.UserSpecificDetails;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * CAS JWT鉴定过滤器
 */
public class CasJwtAuthenticationFilter extends GenericFilterBean {

    private JwtParser jwtParser;

    public CasJwtAuthenticationFilter(ApplicationContext context) {
        this.jwtParser = context.getBean(JwtParser.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (this.jwtParser.isAvailable()) {
            HttpServletRequest request = (HttpServletRequest) req;
            String type = request.getHeader(WebConstants.HEADER_AUTH_TYPE);
            String jwt = request.getHeader(WebConstants.HEADER_AUTH_JWT);
            UserSpecificDetails<?> details = this.jwtParser.parse(type, jwt, UserSpecificDetails.class);
            if (details != null) {
                Authentication authResult = new UserSpecificDetailsAuthenticationToken(details);
                securityContext.setAuthentication(authResult);
            }
        }

        chain.doFilter(req, res);
        // 恢复原授权
        securityContext.setAuthentication(authentication);
    }

}

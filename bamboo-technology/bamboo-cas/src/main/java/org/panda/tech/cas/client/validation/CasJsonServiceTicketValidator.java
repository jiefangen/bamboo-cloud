package org.panda.tech.cas.client.validation;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.validation.AbstractUrlBasedTicketValidator;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.model.tuple.Binate;
import org.panda.bamboo.common.util.ExceptionUtil;
import org.panda.bamboo.common.util.jackson.JsonUtil;
import org.panda.bamboo.common.util.lang.MathUtil;
import org.panda.tech.cas.core.validation.SimpleAssertion;
import org.panda.tech.core.util.HttpClientUtil;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;

/**
 * 基于JSON数据格式的CAS服务票据校验器
 */
public class CasJsonServiceTicketValidator extends AbstractUrlBasedTicketValidator {

    public CasJsonServiceTicketValidator(String casServerUrlPrefix) {
        super(casServerUrlPrefix);
    }

    @Override
    protected String getUrlSuffix() {
        return "serviceValidate";
    }

    @Override
    protected String retrieveResponseFromServer(URL validationUrl, String ticket) {
        try {
            Binate<Integer, String> result = HttpClientUtil.requestByGet(validationUrl.toString(), null, null);
            if (result != null) {
                return result.getLeft() + Strings.COLON + result.getRight();
            }
        } catch (Exception e) {
            throw ExceptionUtil.toRuntimeException(e);
        }
        return null;
    }

    @Override
    protected Assertion parseResponseFromServer(String response) throws TicketValidationException {
        Assertion assertion = null;
        if (StringUtils.isNotBlank(response)) {
            int index = response.indexOf(Strings.COLON);
            int statusCode = MathUtil.parseInt(response.substring(0, index));
            String content = response.substring(index + 1);
            if (statusCode == HttpServletResponse.SC_FORBIDDEN) {
                throw new TicketValidationException(content);
            } else {
                assertion = JsonUtil.json2Bean(content, SimpleAssertion.class);
            }
        }
        if (assertion == null) {
            throw new TicketValidationException("The service ticket is invalid");
        }
        if (!assertion.isValid()) {
            throw new TicketValidationException("The service ticket is expired");
        }
        return assertion;
    }

}

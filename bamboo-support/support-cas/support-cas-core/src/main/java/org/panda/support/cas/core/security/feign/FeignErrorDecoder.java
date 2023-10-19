package org.panda.support.cas.core.security.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Feign错误解码器
 */
@Component
public class FeignErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            int status = response.status();
            if (status == HttpStatus.FORBIDDEN.value() || status == HttpStatus.BAD_REQUEST.value()) {
                String json = getResponseBody(response);
//                return this.exceptionParser.parse(json);
            } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new RuntimeException(getResponseBody(response));
            }
        } catch (Exception e) {
            LogUtil.error(getClass(), e);
        }
        return super.decode(methodKey, response);
    }

    private String getResponseBody(Response response) throws IOException {
        return IOUtils.toString(response.body().asReader(StandardCharsets.UTF_8));
    }

}

package org.panda.support.cas.core.interceptor;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * CAS错误解码器
 */
//@Component
public class CasErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            int status = response.status();
            if (status == HttpStatus.FORBIDDEN.value() || status == HttpStatus.BAD_REQUEST.value()) {
                String json = getResponseBody(response);
                return new Exception(json); // 可自定义扩展处理
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

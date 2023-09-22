package org.panda.support.cloud.core.common;

import org.panda.tech.core.version.MavenVersionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 微服务下默认maven版本读取器
 *
 * @author fangen
 **/
@Component
@PropertySource("classpath:maven.properties")
public class DefaultMavenVersionReader extends MavenVersionReader {

    @Override
    protected String readFullVersion(ApplicationContext context) {
        return super.readFullVersion(context);
    }

}

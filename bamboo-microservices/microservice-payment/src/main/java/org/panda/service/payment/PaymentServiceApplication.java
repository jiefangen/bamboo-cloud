package org.panda.service.payment;

import org.panda.support.cloud.core.CloudCoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 支付微服务启动项
 *
 * @author fangen
 */
@EnableFeignClients
@EnableDiscoveryClient
@Import({CloudCoreModule.class})
@EnableTransactionManagement
@SpringBootApplication
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}

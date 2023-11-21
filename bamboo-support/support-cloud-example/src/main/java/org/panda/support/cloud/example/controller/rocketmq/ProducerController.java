package org.panda.support.cloud.example.controller.rocketmq;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息队列-生产器
 *
 * @author fangen
 **/
@Api(tags = "消息队列-生产器")
@RestController
@RequestMapping(value = "/produce")
public class ProducerController {
}

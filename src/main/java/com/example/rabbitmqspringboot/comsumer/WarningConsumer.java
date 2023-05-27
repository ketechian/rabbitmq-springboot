package com.example.rabbitmqspringboot.comsumer;

import com.example.rabbitmqspringboot.config.ConfirmConfig;
import com.rabbitmq.client.ConfirmCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author: HZY
 * @CreateTime: 2022/3/21 18:54
 */

@Slf4j
@Component
public class WarningConsumer {
    //接受报警消息
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("警报发现不可路由消息: {}", msg);
    }
}

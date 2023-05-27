package com.example.rabbitmqspringboot.comsumer;

import com.example.rabbitmqspringboot.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description
 * @Author: HZY
 * @CreateTime: 2022/3/21 11:41
 */

@Slf4j
@Component
public class DelayQueueConsumer {
    //监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message) {
        String msg = new String(message.getBody());
        log.info("当前发送时间: {}, 收到延时队列消息: {}", new Date().toString(), msg);

    }

}

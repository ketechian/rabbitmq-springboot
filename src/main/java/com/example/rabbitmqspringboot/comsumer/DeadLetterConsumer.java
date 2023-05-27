package com.example.rabbitmqspringboot.comsumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description
 * @Author: HZY
 * @CreateTime: 2022/3/19 23:18
 */

@Slf4j
@Component
public class DeadLetterConsumer {
    //接受消息
    @RabbitListener(queues =  "QD")
    @RabbitHandler
    public void receiveD(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody());
        log.info("当前时间: {}, 收到死信队列消息: {}", new Date().toString(), msg);
    }
}

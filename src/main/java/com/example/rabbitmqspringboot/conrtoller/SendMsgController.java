package com.example.rabbitmqspringboot.conrtoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.example.rabbitmqspringboot.config.DelayedQueueConfig.DELAYED_EXCHANGE_NAME;
import static com.example.rabbitmqspringboot.config.DelayedQueueConfig.DELAYED_ROUTING_KEY;

/**
 * @Description
 * @Author: HZY
 * @CreateTime: 2022/3/19 23:03
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前发送时间: {}, 发送消息给ttl队列: {}", new Date().toString(), message);

        rabbitTemplate.convertAndSend("X", "XA", "来自ttl为10s的队列: " + message);
        rabbitTemplate.convertAndSend("X", "XB", "来自ttl为40s的队列: " + message);
    }

    //消费者设置ttl
    @GetMapping("/sendExpiratMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,
                        @PathVariable String ttlTime) {
        log.info("当前发送时间: {}, 发送时长{}ms的ttl消息给ttl队列: {}", new Date().toString(), ttlTime, message);

        rabbitTemplate.convertAndSend("X", "XC", message, msg -> {
            //发送消息时的延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    //发送消息 基于插件
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message,
                        @PathVariable Integer delayTime) {
        log.info("当前时间: {},发送时长{}毫秒的信息给延时队列: {}", new Date().toString(), delayTime, message);

        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message, msg -> {
            //发送消息时的延迟时长
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }
}

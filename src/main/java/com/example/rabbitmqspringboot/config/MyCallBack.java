package com.example.rabbitmqspringboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description
 * @Author: HZY
 * @CreateTime: 2022/3/21 16:28
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //注入到RabbitTemplate
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     *
     * @param correlationData 保存回调信息的ID及相关信息
     * @param ack 交换机是否成功收到消息
     * @param cause 成功则为null,失败则为相关信息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "" ;

        if(ack) {
            log.info("交换机已经成功收到ID为{}的消息", id);
        }else {
            log.info("交换机未功收到ID为{}的消息,原因为: {}", id, cause);
        }
    }

    //消息发送失败时,回退消息
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息: {}, 被交换机{}退回,退回原因: {}, 路由Key: {}", new String(returnedMessage.getMessage().getBody()),
                returnedMessage.getExchange(), returnedMessage.getReplyText(), returnedMessage.getRoutingKey());
    }
}

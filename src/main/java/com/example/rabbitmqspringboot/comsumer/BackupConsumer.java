package com.example.rabbitmqspringboot.comsumer;

import com.example.rabbitmqspringboot.config.ConfirmConfig;
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
public class BackupConsumer {
    //接受报警消息
    @RabbitListener(queues = ConfirmConfig.BACKUP_QUEUE_NAME)
    public void receiveBackupMsg(Message message) {
        String msg = new String(message.getBody());
        System.out.println(msg);
        log.info("接受到备份队列消息: {}", msg);
    }
}

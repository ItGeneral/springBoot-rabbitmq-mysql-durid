package com.kindergarten.bootmain.base;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created on 2016/11/10.
 * @Author SongJiuHua.
 * @description
 */
@Service
public class ProducerService implements RabbitTemplate.ConfirmCallback{

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sender(){
        amqpTemplate.convertAndSend("helloExchange", "hello.routingKey.test", "测试rabbitmq生产者");
    }

    /**
     * 回调
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause);
        }
    }
}

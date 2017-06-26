package com.kindergarten.bootmain.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * @Date Created on 2016/11/10.
 * @Author SongJiuHua.
 * @description
 */
public class ConsumerListener implements ChannelAwareMessageListener{


    /**
     * 消费者接收到消息后，可以在里面实现自己的业务逻辑
     * @param message
     * @param channel
     * @throws Exception
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        System.out.println("receive msg : " + new String(body));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
    }
}

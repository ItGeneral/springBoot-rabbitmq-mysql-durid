package com.kindergarten.bootmain.config;

import com.kindergarten.bootmain.listener.ConsumerListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created on 2016/11/10.
 * @Author SongJiuHua.
 * @description
 */
@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
public class RabbitMQConfig {

    @Value("${springBoot.rabbitmq.host}")
    private String host;

    @Value("${springBoot.rabbitmq.port}")
    private Integer port;

    @Value("${springBoot.rabbitmq.username}")
    private String userName;

    @Value("${springBoot.rabbitmq.password}")
    private String password;

    @Value("${springBoot.rabbitmq.virtualHost}")
    private String virtualHost;

    @Value("${springBoot.rabbitmq.queue}")
    private String queue;

    @Value("${springBoot.rabbitmq.exchange}")
    private String exchange;

    @Value("${springBoot.rabbitmq.routingKey}")
    private String routingKey;

    /**
     * 创建连接
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory ();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true);//消息确认回调
        return connectionFactory;
    }

    /**
     * 创建队列
     * true : 服务重启后，queue仍然还在
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(queue ,true);
    }

    /**
     * 创建exchange, 可以创建TopicExchange(*、#模糊匹配routing key，routing key必须包含".")，DirectExchange，FanoutExchange(无routing key概念)
     * @return
     */
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    /**
     * queue绑定exchange
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    /**
     * 注册listener
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ConsumerListener());
        return container;
    }

}

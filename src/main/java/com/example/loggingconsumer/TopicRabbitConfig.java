package com.example.loggingconsumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
    final static String TOPIC_MESSAGE = "topic.message";
    final static String TOPIC_MESSAGES = "topic.message";
    final static String TOPIC_MESSAGE4Test = "topic.message4test";
    final static String TOPIC_MESSAGE4Test2 = "topic.message4test2";

    @Bean
    public Queue messageQueue(){
        return new Queue(TOPIC_MESSAGE);
    }

    @Bean
    public Queue messageQueues(){
        return new Queue(TOPIC_MESSAGES);
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange("exchange");
    }

    // --- 这里自动注入的Queue是根据参数名为beanName来查找注入的，之前因为注册了多个Queue的bean。如果此处修改参数名为其他找不到的名称，会注入不进去
    // --- 如果这里不通过参数名一致来注入，可参考下面的注释示例
    @Bean
    Binding bindTopicMessageExchange(Queue messageQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(messageQueue).to(topicExchange).with("topic.message");
    }

    @Bean
    Binding bindTopicMessagesExchange(Queue messageQueues, TopicExchange topicExchange){
        return BindingBuilder.bind(messageQueues).to(topicExchange).with("topic.#");
    }


    // ------------- 示例1，修改注册bean时的BeanName，默认的beanName为方法名，自动注入时保持注入的对象的参数名和beanName一致即可
    @Bean(name = "testQueue")
    public Queue messageQueue4Test(){
        return new Queue(TOPIC_MESSAGE4Test);
    }

    @Bean
    Binding bindTopicMessageExchange4Test(Queue testQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(testQueue).to(topicExchange).with("topic.message");
    }
    // ------------- 示例1结束

    // ------------- 示例2，注册Bean时保持原有的不变，但是在自动注入时要使用注解@Qualifier("messageQueue4Test2")指定要注入的bean的名称，即原有方法的参数名
    @Bean
    public Queue messageQueue4Test2(){
        return new Queue(TOPIC_MESSAGE4Test2);
    }

    @Bean
    Binding bindTopicMessageExchange4Test2(@Qualifier("messageQueue4Test2") Queue anyName, TopicExchange topicExchange){
        return BindingBuilder.bind(anyName).to(topicExchange).with("topic.message");
    }
    // ------------- 示例2结束

}

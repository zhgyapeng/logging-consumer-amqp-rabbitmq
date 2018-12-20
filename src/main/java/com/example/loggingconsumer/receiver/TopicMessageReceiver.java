package com.example.loggingconsumer.receiver;

import com.example.loggingconsumer.info.Person;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "topic.message")
@Component
public class TopicMessageReceiver {

    @RabbitHandler
    public void received(Person person) {
        System.out.println("topic message received: " + person);
    }
}

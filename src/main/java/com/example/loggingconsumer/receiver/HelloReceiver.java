package com.example.loggingconsumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"hello"})
public class HelloReceiver {

    @RabbitHandler
    public void receive(String content) {
        System.out.println("receiver1: " + content);
    }
}

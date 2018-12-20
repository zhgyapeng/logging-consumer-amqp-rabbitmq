package com.example.loggingconsumer;

import com.example.loggingconsumer.info.Person;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping
@RestController
public class HelloController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/test")
    public String test() {
        LocalDateTime now = LocalDateTime.now();
        amqpTemplate.convertAndSend("hello","send test. current: " + now);
        return "success: " + now;
    }

    @GetMapping("/testTopic")
    public String testTopic() {
        LocalDateTime now = LocalDateTime.now();
        amqpTemplate.convertAndSend("exchange","topic.message",new Person("zhang",26));
        amqpTemplate.convertAndSend("exchange","topic.anythings",new Person("zhang",27));
        return "topic success: " + now;
    }


}

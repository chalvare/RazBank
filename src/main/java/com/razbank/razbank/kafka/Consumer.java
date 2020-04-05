package com.razbank.razbank.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Consumer {


    public CountDownLatch latch = new CountDownLatch(3);

    @KafkaListener(topics = "${message.topic.name}", groupId = "${kafka.consumer.group-id}", containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
        System.out.println("Received Messasge in group 'foo': " + message);
        System.out.println(message.toUpperCase());
        latch.countDown();
    }

}

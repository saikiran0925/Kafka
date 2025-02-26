package com.example.Kafka.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Consumer
{
    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "test-topic", groupId = "my-group")
    public void listen(String message) {
        messages.add(message);
        System.out.println("Received: " + message);
    }

    public List<String> getMessages() {
        return messages;
    }

}


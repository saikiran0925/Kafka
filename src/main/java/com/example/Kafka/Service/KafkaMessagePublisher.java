package com.example.Kafka.Service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher
{
    @Autowired
    private KafkaTemplate<String,Object> template;

    public List<String> messages=new ArrayList<>();


    public void sendMessage(String message)
    {
        CompletableFuture<SendResult<String, Object>> send = template.send("test-topic", message);
        send.whenComplete((res,exc)->{
            if(exc==null)
            {
                System.out.println("Message is sent "+message+" With offset value ="+res.getRecordMetadata().offset());
            }
            else {
                System.out.println(exc.getMessage());
            }
        }) ;
    }

    @KafkaListener(topics = "test-topic", groupId = "test-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record) {
        messages.add(record.value());
        System.out.println("Received message: " + record.value());
    }


    public List<String> getMessages()
    {
        System.out.println(messages);
        return messages;
    }

}

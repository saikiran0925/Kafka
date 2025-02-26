package com.example.Kafka.Controller;

import com.example.Kafka.Service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/producer")
public class KafkaController
{
    @Autowired
    private KafkaMessagePublisher publisher;

    @PostMapping
    public ResponseEntity<?> getMessage(@RequestParam String message)
    {
        try
        {
            publisher.sendMessage(message);

            return ResponseEntity.ok("Message published successfully");
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/messages")
    public List<String> getMessages()
    {
        return publisher.getMessages();
    }
 }

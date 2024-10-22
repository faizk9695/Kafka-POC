package com.faizan.controller;

import com.faizan.service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.faizan.dto.Customer;
@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;


//    @GetMapping("/publish/{message}")
//    public ResponseEntity<?> publishMessage(@PathVariable String message) {
//
//        for (int i = 0; i < 100000000; i++) {
//            publisher.sendMessageToTopic(message);
//        }
//        ;
//        return ResponseEntity.ok("message published successfully....");
//    }


    @PostMapping("/publish/")
    public ResponseEntity<?> sendEvents(@RequestBody Customer customer) {
        publisher.sendEventToTopic(customer);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}


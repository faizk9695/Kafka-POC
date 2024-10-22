package com.faizan.consumer;

import com.faizan.dto.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class KafkaConsumer {
    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @RetryableTopic(attempts = "4")
    @KafkaListener(topics = "Event-User", groupId = "worker-1", containerFactory = "kafkalistenerContainerFactory")
    public void consumer(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            log.info("Received: {} from {} offset {}", new ObjectMapper().writeValueAsString(customer), topic, offset);

            //Validate restricted IP before process the records
            List<String> restrictedIpList = Stream.of("32.241.244.236", "15.55.49.164", "81.1.95.253", "126.130.43.183").toList();
            if (restrictedIpList.contains(customer.getIpAddress())) {
                throw new RuntimeException("Invalid Ip Address Received!");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @DltHandler
    public void listenDLT (Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header(KafkaHeaders.OFFSET) long offset){
        log.info("DLT Received :  {} , from{} , offset{}", customer.getName(),topic,offset);
    }


//    @KafkaListener(topics = "Event-User", groupId = "worker-1",containerFactory = "kafkalistenerContainerFactory")
//
//    public void consumer2(Customer customer) {
//
//        logger.info("Consumer2 is consuming the message {}", customer);
//
//
//    }
//    @org.springframework.kafka.annotation.KafkaListener(topics = "payment-6", groupId = "worker-4")
//    public void consumer3(String message){
//
//        logger.info("Consumer3 is consuming the message {}", message);
//
//
//    }
//    @org.springframework.kafka.annotation.KafkaListener(topics = "payment-6", groupId = "worker-4")
//    public void consumer4(String message){
//
//        logger.info("Consumer4 is consuming the message {}", message);


}


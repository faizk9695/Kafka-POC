package com.faizan.service;

import com.faizan.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String,Object > template;

//    public void sendMessageToTopic(String message){try {
//        CompletableFuture<SendResult<String, Object>> future = template.send("payment-6",2,null, message);
//
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                System.out.println("sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            } else {
//                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
//            }
//
//        });
//    } catch (Exception e) {
//        System.out.println("ERROR " + e.getMessage());
//    }
//    }


    public void sendEventToTopic(Customer customer){
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("Event-User", customer);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("sent message=[" + customer.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" + customer.toString() + "] due to : " + ex.getMessage());
                }

            });
        } catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
        }
    }


}

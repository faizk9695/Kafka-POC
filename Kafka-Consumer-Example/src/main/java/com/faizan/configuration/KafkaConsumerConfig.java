package com.faizan.configuration;

import com.faizan.dto.Customer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {


//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        // Adjust to your DTO class
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.faizan.dto");
//        return props;
//    }

    @Bean
    public ConsumerFactory<String, Customer> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "worker-1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.faizan.dto.Customer");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.faizan.dto");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Customer.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,Customer>> kafkalistenerContainerFactory(){

       ConcurrentKafkaListenerContainerFactory<String,Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
       factory.setConsumerFactory(consumerFactory());
       return factory;
}


//    @Bean
//    public Map<String,Object> consumerConfig() {
//    Map<String, Object> props = new HashMap<>();
//    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            "localhost:9092");
//    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//            StringDeserializer.class);
//    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//            JsonDeserializer.class);
//    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//    return props;
//
//}
//
//@Bean
//public ConsumerFactory<String, Customer> consumerFactory() {
//    Map<String, Object> props = consumerConfig();
//    JsonDeserializer<Customer> jsonDeserializer = new JsonDeserializer<>(Customer.class);
//    jsonDeserializer.setTypeMapper((Jackson2JavaTypeMapper) new ObjectMapper());
//    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
//}



//    @Bean
//    public ConsumerFactory<String, Customer> consumerFactory() {
//        JsonDeserializer<Customer> deserializer = new JsonDeserializer<>(Customer.class);
//        deserializer.addTrustedPackages("com.example.consumer.Kafka_Consumer_Example.dto"); // Update to your actual package
//
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.consumer.Kafka_Consumer_Example.dto");
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.example.consumer.Kafka_Consumer_Example.dto.Customer");
//
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
//    }
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,Customer>> kafkalistenerContainerFactory(){
//
//       ConcurrentKafkaListenerContainerFactory<String,Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
//       factory.setConsumerFactory(consumerFactory());
//       return factory;
//}


//    @Bean
//    public Map<String, Object> consumerConfig() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Allow all packages
//        return props;
//    }
//
//    @Bean
//    public ConsumerFactory<String, Customer> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
//                new JsonDeserializer<>(Customer.class));
//    }
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Customer>> kafkalistenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }

}

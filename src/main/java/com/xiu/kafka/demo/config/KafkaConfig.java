package com.xiu.kafka.demo.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther 创建者: Tc李
 * @Date 创建时间: 2018/07/05 18:54
 * @Description 类描述:
 */

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin admin() {
        KafkaAdmin kafkaAdmin = new KafkaAdmin(producerConfigs());
        kafkaAdmin.setAutoCreate(true);
        return kafkaAdmin;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        DefaultKafkaProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(producerConfigs());

        return producerFactory;
    }

    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        DefaultKafkaConsumerFactory<String, Object> consumerFactory = new DefaultKafkaConsumerFactory(producerConfigs());
        return consumerFactory;
    }

    @Bean
    public KafkaListenerContainerFactory kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        containerFactory.setAutoStartup(true);
        containerFactory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
        return containerFactory;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.20:9091,192.168.1.20:9092,192.168.1.20:9093");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,20000);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,20000);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,15000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,10000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES,"com.xiu.kafka.demo.model");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG,"121212");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "11111");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        return props;
    }


    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){

        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate(producerFactory(),true);

        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return kafkaTemplate;
    }
}

package com.xiu.kafka.demo.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
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
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.20:9091,192.168.0.20:9092,192.168.0.20:9093");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,6000);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,6000);
        return props;
    }


    @Bean
    public NewTopic topic1() {
        return new NewTopic("foo", 10, (short) 2);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){

        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate(producerFactory(),true);

        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return kafkaTemplate;
    }
}

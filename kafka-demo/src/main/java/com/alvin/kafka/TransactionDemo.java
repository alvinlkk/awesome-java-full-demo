package com.alvin.kafka;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/6/5  20:49
 */
public class TransactionDemo {

    public static void main(String[] args) {
        Properties prodcuerProps = new Properties();
        // kafka地址
        prodcuerProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        // key序列化
        prodcuerProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value序列化
        prodcuerProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        // 设置事务id
        prodcuerProps.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "prod-1");
        KafkaProducer<String, String> producer = new KafkaProducer(prodcuerProps);

        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put("group.id", "my-group-id");
        // 设置consumer手动提交
        consumerProps.put("enable.auto.commit", "false");
        // 设置隔离级别，读取事务已提交的消息
        consumerProps.put("isolation.level", "read_committed");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);


    }

}

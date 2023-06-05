/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 类的描述
 *
 * @author alvin
 * @version 7.x
 * @since 2023/5/21
 **/
public class MyProducer {

    public static void main(String[] args) {
        // 1. 创建 kafka 生产者的配置对象
        Properties properties = new Properties();
        // 2. 给 kafka 配置对象添加配置信息：bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // key,value 序列化（必须）：key.serializer，value.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        // 3. 创建 kafka 生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        // 4. 调用 send 方法,发送消息
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new
                    ProducerRecord<>("first", Integer.toString(i), "hello " + i));
            // 发送消息到0号分区
            kafkaProducer.send(new
                    ProducerRecord<>("first", 0, Integer.toString(i), "hello " + i));
            // 同步发送
//            kafkaProducer.send(new
//                    ProducerRecord<>("first", Integer.toString(i), "hello " + i)).get();
        }

        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new
                    ProducerRecord<>("first", Integer.toString(i), "hello " + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        // 没有异常,输出信息到控制台
                        System.out.println(" 主题： " +
                                metadata.topic() + "->" + "分区：" + metadata.partition());
                    } else {
                        // 出现异常打印
                        exception.printStackTrace();
                    }
                }
            });
        }
        // 5. 关闭资源
        kafkaProducer.close();
    }

}

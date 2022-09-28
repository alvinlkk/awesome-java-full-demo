package cn.com.alvin.zk.sasl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (cxw@bsfit.com.cn)
 * @date: 2022/9/28  13:22
 * @version: 1.0.0
 */

@Slf4j
public class KafkaTest {

    private static final String KFK_SERVER = "10.100.2.101:9092,10.100.2.132:9092,10.100.2.111:9092";

    private static final String ZK_ADDR = "10.100.1.13:2187";

    private static final Integer ZK_SESSION_TIMEOUT = 30000;

    private ZooKeeper zooKeeper = null;

    @Before
    public void init() throws IOException, InterruptedException {
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        System.setProperty("java.security.krb5.conf", "D:\\Developer\\CodeRepo\\alvinlkk\\awesome-java-full-demo\\zookeeper-demo\\zookeeper-sasl-demo\\src\\main\\resources\\krb5.conf");
        System.setProperty("java.security.auth.login.config", "D:\\Developer\\CodeRepo\\alvinlkk\\awesome-java-full-demo\\zookeeper-demo\\zookeeper-sasl-demo\\src\\main\\resources\\jaas.conf");
        System.setProperty("Dsun.security.krb5.debug", "true");
        log.info("********************** start zk ..................");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //ZKClientConfig conf = new ZKClientConfig();
        //conf.setProperty(ZKClientConfig.LOGIN_CONTEXT_NAME_KEY, "Client");
        zooKeeper = new ZooKeeper(ZK_ADDR, ZK_SESSION_TIMEOUT, event -> {
            log.info("%%%%%%%%%%%%%%%%%%%%%触发了事件：[{}]", event);
            countDownLatch.countDown();
        });
        countDownLatch.await();
    }

    @Test
    public void testConnect() throws InterruptedException, KeeperException {

        // 配置.
        Map<String, Object> config = new HashMap<>();

        // 连接地址
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KFK_SERVER);

        // ACK
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        // 相应超时.
        config.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 5000);

        // 缓冲区大小. (发送给服务器的)
        config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024 * 1024 * 10);
        // 每次最多发10K
        config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1024 * 10);
        // 不重试,有些非幂等性可以.
        config.put(ProducerConfig.RETRIES_CONFIG, 0);

        // 序列化
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        config.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        config.put("sasl.kerberos.service.name", "kafka");
        config.put("sasl.mechanism", "GSSAPI");

        // snappy 压缩..
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        KafkaProducer<String, String> producer = new KafkaProducer<>(config);

        IntStream.range(0, 10).forEach(value -> {
            // 发送
            producer.send(new ProducerRecord<>("testAuth", "cur-time", String.format("id: %d, time : %d.", value, System.currentTimeMillis())), (metadata, exception) -> {

            });
            log.info("send kafka success");
        });

        Stat stat = new Stat();
        byte[] data = zooKeeper.getData("/node1", false, stat);
        log.info("获取到的数据是：" + new String(data));
        log.info("当前节点的版本：" + stat.getVersion());

    }

}

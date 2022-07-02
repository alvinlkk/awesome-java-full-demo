package com.alvin.zk;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZkInit {
    public static void main(String[] args) {
        try {
            log.info("开始连接zk ............");
            ZooKeeper zooKeeper = new ZooKeeper("10.100.1.14:2181", 30000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    log.info("event: [{}]", event);
                }
            });
            log.info("zk 连接成功 ............");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

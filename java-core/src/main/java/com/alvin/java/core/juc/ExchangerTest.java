package com.alvin.java.core.juc;

import java.util.concurrent.Exchanger;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/12/1  19:08
 * @version: 1.0.0
 */
@Slf4j(topic = "c.ExchangerTest")
public class ExchangerTest {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread boy = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("你开始准备礼物~~~~~~~~~~~~");
                try {
                    // 模拟准备礼物时间
                    Thread.sleep(5000);

                    String gift = "IPhone 14";
                    log.info("你送了礼物: {}",  gift);
                    String recGift = exchanger.exchange(gift);
                    log.info("你收到了礼物: {}",  recGift);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread girl = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("女朋友开始准备礼物~~~~~~~~~~~~");
                try {
                    // 模拟准备礼物时间
                    Thread.sleep(6000);

                    String gift = "一个吻";
                    log.info("女朋友送了礼物: {}",  gift);
                    String recGift = exchanger.exchange(gift);
                    log.info("女朋友收到了礼物: {}",  recGift);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        boy.start();
        girl.start();

        boy.join();
        girl.join();

    }
}

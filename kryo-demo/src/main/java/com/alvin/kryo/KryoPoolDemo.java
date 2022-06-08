package com.alvin.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.Pool;

public class KryoPoolDemo {

    public static void main(String[] args) {
        // Pool constructor arguments: thread safe, soft references, maximum capacity
        Pool<Kryo> kryoPool = new Pool<Kryo>(true, false, 8) {
            @Override
            protected Kryo create() {
                Kryo kryo = new Kryo();
                // Kryo 配置
                return kryo;
            }
        };

        // 获取池中的Kryo对象
        Kryo kryo = kryoPool.obtain();
        // 将kryo对象归还到池中
        kryoPool.free(kryo);

    }
}

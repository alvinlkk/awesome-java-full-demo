/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.anno;

import java.util.Arrays;

/**
 * 枚举测试
 *
 * @author alvin
 * @date 2022/9/3
 * @since 1.0
 **/
public class EnumTest {

    public static void main(String[] args) {
        // 根据字符串获取枚举
        StatusEnum enable = Enum.valueOf(StatusEnum.class, "ENABLE");
        System.out.println(enable);
        //枚举比较直接用==
        System.out.println(enable == StatusEnum.ENABLE);

        // values方法获取所有的枚举
        StatusEnum[] values = StatusEnum.values();
        for (StatusEnum statusEnum : values) {
            // 打印枚举的位置
            System.out.println(statusEnum.ordinal());
        }
    }

}

enum StatusEnum {
    ENABLE("1", "启用"), DISABLE("0", "禁用");

    private String code;

    private String name;

    StatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

enum WeekEnum {
    MONDAY, TUESDAY
}

enum OperEnum {
    ADD(1, 2) {
        @Override
        public Integer operate() {
            return this.getA() + this.getB();
        }
    }, MULTIPY(1, 2) {
        @Override
        public Integer operate() {
            return this.getA() * this.getB();
        }
    };

    private Integer a;

    private Integer b;

    OperEnum(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    public abstract Integer operate();

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }
}

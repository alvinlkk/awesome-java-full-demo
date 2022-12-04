/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.error.spi;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/24
 * @since 1.0
 **/
public class Huawei implements Phone {
    @Override
    public String getSystemInfo() {
        return "Hong Meng";
    }
}
/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.fanxin;

import java.time.LocalDate;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/9/4
 * @since 1.0
 **/
public class DateInterval extends Pair<LocalDate> {

    public DateInterval(LocalDate first, LocalDate second) {
        super(first, second);
    }

    @Override
    public void setSecond(LocalDate second) {
        if(second.compareTo(getFirst()) >= 0) {
            super.setSecond(second);
        }
    }
}

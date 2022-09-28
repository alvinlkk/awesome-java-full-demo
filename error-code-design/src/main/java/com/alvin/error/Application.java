package com.alvin.error;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alvin.error.api.ErrorCode;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (cxw@bsfit.com.cn)
 * @date: 2022/9/1  20:46
 * @version: 1.0.0
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.alvin.error.api.ErrorCode");
        SpringApplication.run(Application.class, args);
    }

}

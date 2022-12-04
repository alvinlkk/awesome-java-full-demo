package com.alvin.error;

import java.util.Arrays;
import java.util.ServiceLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alvin.error.api.ErrorCode;
import com.alvin.error.manager.ErrorCodeManager;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/9/1  20:46
 * @version: 1.0.0
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        ErrorCodeManager.init();
    }

}

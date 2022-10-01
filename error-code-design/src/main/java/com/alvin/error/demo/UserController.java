package com.alvin.error.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alvin.error.response.Result;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/9/1  20:29
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get")
    public Result<String> getUser() {
        throw new UserException(UserErrorCodes.USER_NOT_EXIST);
    }
}

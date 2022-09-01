package com.alvin.error.demo;

import com.alvin.error.api.ErrorCode;
import com.alvin.error.manager.ErrorManager;

import lombok.Getter;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (cxw@bsfit.com.cn)
 * @date: 2022/9/1  20:30
 * @version: 1.0.0
 */
@Getter
public enum UserErrorCodes implements ErrorCode {

    USER_NOT_EXIST(0, "用户名不存在"), //错误码: 10100
    PASSWORD_ERROR(1, "密码错误");    //错误码: 10101

    private final int nodeNum;
    private final String msg;

    UserErrorCodes(int nodeNum, String msg) {
        this.nodeNum = nodeNum;
        this.msg = msg;
        ErrorManager.register(UserProjectCodes.USER, this);
    }
}

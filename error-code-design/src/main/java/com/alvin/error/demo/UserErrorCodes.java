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
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(0, "用户名不存在"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR(1, "密码错误");

    private final int nodeNum;
    private final String msg;

    UserErrorCodes(int nodeNum, String msg) {
        this.nodeNum = nodeNum;
        this.msg = msg;
        // 注册错误码，也就是绑定这个错误码属于哪个模块的
        ErrorManager.register(UserProjectCodes.USER, this);
    }

    static {
        System.out.println("************************");
    }
}

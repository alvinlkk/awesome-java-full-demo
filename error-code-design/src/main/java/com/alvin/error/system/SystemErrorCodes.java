package com.alvin.error.system;

import com.alvin.error.api.ErrorCode;
import com.alvin.error.manager.ErrorCodeManager;

import lombok.Getter;

/**
 * 基础错误码定义
 *
 * @author lishafeng02
 * @date 2018/8/3
 */
@Getter
public enum SystemErrorCodes implements ErrorCode {

    SUCCESS(0, "ok"),
    SYSTEM_ERROR(1, "system error");

    private final int nodeNum;
    private final String msg;

    SystemErrorCodes(int nodeNum, String msg) {
        this.nodeNum = nodeNum;
        this.msg = msg;
        ErrorCodeManager.register(SystemProjectModule.INSTANCE, this);
    }

}

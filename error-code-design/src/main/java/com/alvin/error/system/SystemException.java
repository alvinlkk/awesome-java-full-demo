package com.alvin.error.system;

import com.alvin.error.api.ErrorCode;
import com.alvin.error.api.ProjectModule;
import com.alvin.error.exception.BaseException;
import com.alvin.error.manager.ErrorInfo;

/**
 * @author sofn
 * @version 1.0 Created at: 2022-03-09 16:41
 */
public class SystemException extends BaseException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SystemException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public ProjectModule projectModule() {
        return SystemProjectModule.INSTANCE;
    }
}

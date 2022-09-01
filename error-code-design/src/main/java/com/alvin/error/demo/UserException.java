package com.alvin.error.demo;

import com.alvin.error.api.ErrorCode;
import com.alvin.error.api.ProjectModule;
import com.alvin.error.exception.BaseRuntimeException;
import com.alvin.error.manager.ErrorInfo;

/**
 * @author sofn
 * @version 1.0 Created at: 2022-03-09 18:18
 */
public class UserException extends BaseRuntimeException {

    protected UserException(String message) {
        super(message);
    }

    protected UserException(String message, Throwable cause) {
        super(message, cause);
    }

    protected UserException(Throwable cause) {
        super(cause);
    }

    protected UserException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    protected UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected UserException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public ProjectModule projectModule() {
        return UserProjectCodes.USER;
    }
}

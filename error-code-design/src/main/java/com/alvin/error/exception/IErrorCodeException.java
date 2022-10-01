package com.alvin.error.exception;

import com.alvin.error.api.ProjectModule;
import com.alvin.error.manager.ErrorInfo;

/**
 * @author lishaofeng02
 * @version 1.0 Created at: 2022-03-24 15:35
 */
public interface IErrorCodeException {
    /**
     * 错误信息，获取异常的错误信息
     */
    ErrorInfo getErrorInfo();

    /**
     * 模块信息，获取异常属于哪个模块的
     */
    ProjectModule projectModule();
}

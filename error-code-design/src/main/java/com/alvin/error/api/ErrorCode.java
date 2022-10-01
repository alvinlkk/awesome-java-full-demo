package com.alvin.error.api;


import com.alvin.error.manager.ErrorCodeManager;

/**
 * @author sofn
 * @version 1.0 Created at: 2022-03-09 16:21
 */
public interface ErrorCode {

    /**
     * 最细粒度 Code
     */
    int getNodeNum();

    /**
     * 异常信息 英文
     */
    String getMsg();

    /**
     * 拼接project、module、node后的完整的错误码
     */
    default int getCode() {
        return ErrorCodeManager.genCode(this);
    }

    default ProjectModule projectModule(){
        return ErrorCodeManager.projectModule(this);
    }
}

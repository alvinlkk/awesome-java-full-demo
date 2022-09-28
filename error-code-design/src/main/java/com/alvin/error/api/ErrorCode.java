package com.alvin.error.api;


import com.alvin.error.manager.ErrorManager;

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
        return ErrorManager.genCode(this);
    }

    /**
     * 错误码所属的模块
     * @return 模块枚举
     */
    default ProjectModule projectModule(){
        return ErrorManager.projectModule(this);
    }
}

package com.alvin.error.demo;

import com.alvin.error.api.ProjectModule;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sofn
 * @version 1.0 Created at: 2022-03-09 16:27
 */
@Getter
@AllArgsConstructor
public enum UserProjectCodes implements ProjectModule {
    /**
     * 登录模块
     */
    LOGIN(1, 1, "用户中心", "登录模块"),
    /**
     * 用户管理模块
     */
    USER(1, 2, "用户中心", "用户模块");

    private int projectCode;
    private int moduleCode;
    private String projectName;
    private String moduleName;

}

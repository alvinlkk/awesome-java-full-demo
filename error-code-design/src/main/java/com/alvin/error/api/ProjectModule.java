package com.alvin.error.api;

import com.alvin.error.system.SystemProjectModule;
import com.google.common.base.Preconditions;

/**
 * 项目和模块的编码
 *
 * @author sofn
 * @version 1.0 Created at: 2022-03-09 16:21
 */
public interface ProjectModule {

    /**
     * 项目编码
     */
    int getProjectCode();

    /**
     * 模块编码
     */
    int getModuleCode();

    /**
     * 项目名称
     */
    String getProjectName();

    /**
     * 模块名称
     */
    String getModuleName();

    static void check(ProjectModule required, ProjectModule input) {
        Preconditions.checkNotNull(required);
        if (input != SystemProjectModule.INSTANCE) {
            Preconditions.checkState(required == input, "module not match, need: " + required.getProjectName() + "-" + required.getModuleName() + "(" + required.getProjectCode() + "-" + required.getModuleCode() + ")" + " but input: " + input.getProjectName() + "-" + input.getModuleName() + "(" + input.getProjectCode() + "-" + input.getModuleCode() + ")");
        }
    }
}

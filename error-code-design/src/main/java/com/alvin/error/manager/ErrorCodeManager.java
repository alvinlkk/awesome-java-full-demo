package com.alvin.error.manager;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.core.io.support.SpringFactoriesLoader;

import com.alvin.error.api.ErrorCode;
import com.alvin.error.api.ProjectModule;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sofn
 * @version 1.0 Created at: 2022-03-10 10:34
 */
@Slf4j
public class ErrorCodeManager {
    private static final BiMap<Integer, ErrorCode> GLOBAL_ERROR_CODE_MAP = HashBiMap.create();
    private static final Map<ErrorCode, ProjectModule> ERROR_PROJECT_MODULE_MAP = new ConcurrentHashMap<>();

    private static final Comparator<ProjectModule> PROJECT_MODULE_COMPARATOR = Comparator.comparingInt(ProjectModule::getProjectCode)
            .thenComparingInt(ProjectModule::getModuleCode);
    private static final Comparator<ErrorCode> ERROR_CODE_COMPARATOR = Comparator.comparingInt(ErrorCode::getNodeNum);

    public static void register(ProjectModule projectModule, ErrorCode errorCode) {
        Preconditions.checkNotNull(projectModule);
        Preconditions.checkArgument(projectModule.getProjectCode() >= 0);
        Preconditions.checkArgument(projectModule.getModuleCode() >= 0);
        Preconditions.checkArgument(errorCode.getNodeNum() >= 0);
        int code = genCode(projectModule, errorCode);
        // 如果存在重复，抛出异常
        Preconditions.checkArgument(!GLOBAL_ERROR_CODE_MAP.containsKey(code), "错误码重复:" + code);
        GLOBAL_ERROR_CODE_MAP.put(code, errorCode);
        ERROR_PROJECT_MODULE_MAP.put(errorCode, projectModule);
    }

    public static List<TreeNode> getAllErrorCodes() {
        return ERROR_PROJECT_MODULE_MAP.entrySet().stream()
                .sorted((it1, it2) -> ERROR_CODE_COMPARATOR.compare(it1.getKey(), it2.getKey()))
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                .entrySet()
                .stream()
                .sorted((it1, it2) -> PROJECT_MODULE_COMPARATOR.compare(it1.getKey(), it2.getKey()))
                .collect(Collectors.groupingBy(
                                e -> new TreeNode(e.getKey().getProjectCode(), e.getKey().getProjectName()),
                                Collectors.groupingBy(
                                        it -> new TreeNode(it.getKey().getModuleCode(), it.getKey().getModuleName()),
                                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                                )
                        )
                )
                .entrySet()
                .stream()
                .map(e -> {
                    TreeNode top = e.getKey();
                    List<TreeNode> middleNode = e.getValue()
                            .entrySet()
                            .stream()
                            .map(e1 -> {
                                TreeNode key = e1.getKey();
                                List<TreeNode> leftNode = e1.getValue().stream()
                                        .flatMap(Collection::stream)
                                        .map(errorCode -> new TreeNode(errorCode.getCode(), errorCode.getMsg()))
                                        .collect(Collectors.toList());
                                key.setNodes(leftNode);
                                return key;
                            })
                            .collect(Collectors.toList());
                    top.setNodes(middleNode);
                    return top;
                })
                .collect(Collectors.toList());
    }

    private static int genCode(ProjectModule projectModule, ErrorCode errorCode) {
        return projectModule.getProjectCode() * 10000 + projectModule.getModuleCode() * 100 + errorCode.getNodeNum();
    }

    public static int genCode(ErrorCode errorCode) {
        return GLOBAL_ERROR_CODE_MAP.inverse().get(errorCode);
    }

    public static ProjectModule projectModule(ErrorCode errorCode) {
        return ERROR_PROJECT_MODULE_MAP.get(errorCode);
    }

    public static void init() {
        List<String> results = SpringFactoriesLoader.loadFactoryNames(ErrorCode.class, ErrorCodeManager.class.getClassLoader());
        for (String result : results) {
          log.info("********** {}", result);
            try {
                Class.forName(result);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

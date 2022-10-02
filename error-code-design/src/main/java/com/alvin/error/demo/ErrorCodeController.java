package com.alvin.error.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alvin.error.manager.ErrorCodeManager;
import com.alvin.error.manager.TreeNode;
import com.alvin.error.response.Result;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/9/2  12:43
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/errorcode")
public class ErrorCodeController {

    @GetMapping("/all")
    public Result<List<TreeNode>> listAllErrorCodes() {
        List<TreeNode> allErrorCodes = ErrorCodeManager.getAllErrorCodes();
        return Result.success(allErrorCodes);
    }
}

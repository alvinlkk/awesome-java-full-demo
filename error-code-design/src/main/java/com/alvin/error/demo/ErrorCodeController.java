package com.alvin.error.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alvin.error.manager.ErrorManager;
import com.alvin.error.manager.TreeNode;
import com.alvin.error.response.Result;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (cxw@bsfit.com.cn)
 * @date: 2022/9/2  12:43
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/errorcode")
public class ErrorCodeController {

    @GetMapping("/all")
    public Result<List<TreeNode>> listAllErrorCodes() {
        List<TreeNode> allErrorCodes = ErrorManager.getAllErrorCodes();
        return Result.success(allErrorCodes);
    }
}

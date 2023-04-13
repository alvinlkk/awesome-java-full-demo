package com.alvin.hutool;

import java.util.Arrays;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/4/13  15:27
 */
public class BeanCopyTest {

    public static void main(String[] args) {

        User user1 = new User();
        // 3em3dgqsgmn0
        user1.setId("3e");
        user1.setName("cxw");
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user1, userDTO);
        System.out.println(userDTO);
    }
}

package com.alvin.hutool.bean;

import lombok.Data;
import lombok.ToString;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/4/13  15:27
 */
@Data
@ToString
public class DiagramDTO {

    // 前端生产的字符串id
    private String id;

    private String code;

    private String name;
}

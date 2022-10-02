/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.java.core.serialize;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类的描述
 *
 * @author alvin
 * @date 2022/10/1
 * @since 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends SerialCloneable implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;

    private Integer age;
}

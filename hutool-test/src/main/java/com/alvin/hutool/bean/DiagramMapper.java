/**
 * Copyright © 2010 浙江邦盛科技有限公司 版权所有
 */
package com.alvin.hutool.bean;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 类的描述
 *
 * @author alvin
 * @version 7.x
 * @since 2023/4/16
 **/
@Mapper
public interface DiagramMapper {
    DiagramMapper INSTANCE = Mappers.getMapper(DiagramMapper.class);

    DiagramDTO toDTO(Diagram diagram);

    Diagram toEntity(DiagramDTO diagram);

}

package com.alvin.hutool.bean;


import org.modelmapper.ModelMapper;

import cn.hutool.core.bean.BeanUtil;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/4/13  15:27
 */
public class BeanCopyTest1 {

    public static void main(String[] args) {
        // 前端传输的对象
        DiagramDTO diagramDTO = new DiagramDTO();
        diagramDTO.setId("3em3dgqsgmn0");
        diagramDTO.setCode("d1");
        diagramDTO.setName("图表");

        Diagram diagram = new Diagram();
        // 关键点，数据拷贝
        diagram.setCode(diagramDTO.getCode());
        diagram.setName(diagramDTO.getName());
        System.out.println("数据实体对象：" + diagram);
        //保存到数据库中 TODO
        //diagramMapper.save(diagram);
    }

    private static void testModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        DiagramDTO diagramDTO = new DiagramDTO();
        diagramDTO.setId("3em3dgqsgmn0");
        diagramDTO.setCode("d1");
        diagramDTO.setName("图表");
        Diagram diagram = modelMapper.map(diagramDTO, Diagram.class);
    }

    private static void testMapStruct() {
        DiagramDTO diagramDTO = new DiagramDTO();
        diagramDTO.setId("3em3dgqsgmn0");
        diagramDTO.setCode("d1");
        diagramDTO.setName("图表");
        Diagram diagram = DiagramMapper.INSTANCE.toEntity(diagramDTO);
    }


}

package com.alvin.hutool.bean;


import cn.hutool.core.bean.BeanUtil;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/4/13  15:27
 */
public class BeanCopyTest {

    public static void main(String[] args) {
        // 前端传输的对象
        DiagramDTO diagramDTO = new DiagramDTO();
        diagramDTO.setId("3em3dgqsgmn0");
        diagramDTO.setCode("d1");
        diagramDTO.setName("图表");

        Diagram diagram = new Diagram();
        // 关键点，数据拷贝
        BeanUtil.copyProperties(diagramDTO, diagram);
        System.out.println("数据实体对象：" + diagram);
        //设置id为空，自增
        diagram.setId(null);
        //保存到数据库中 TODO
        //diagramMapper.save(diagram);
    }
}

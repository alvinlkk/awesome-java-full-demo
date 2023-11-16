package com.alvin.calcite;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/11/16  20:38
 */
public class MemoryColumn<T> {
    private String name;
    private Class<T> type;

    public MemoryColumn(String name, Class<T> type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
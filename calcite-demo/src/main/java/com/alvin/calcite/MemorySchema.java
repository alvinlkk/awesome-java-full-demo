package com.alvin.calcite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/11/16  20:37
 */
public class MemorySchema extends AbstractSchema {

    /**
     * 返回 内存表名和表的映射关系
     */
    private Map<String, Table> tableMap;

    private final List<MemoryColumn> meta;

    private final List<List<Object>> source;

    public MemorySchema(List<MemoryColumn> meta, List<List<Object>> source) {
        this.meta = meta;
        this.source = source;
    }

    @Override
    public Map<String, Table> getTableMap() {
        if (tableMap == null || tableMap.isEmpty()) {
            tableMap = new HashMap<>();
            tableMap.put("memory", new MemoryTable(meta, source));
        }
        return tableMap;
    }
}

package com.alvin.calcite;

import java.util.ArrayList;
import java.util.List;

import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeUtil;
import org.apache.calcite.util.Pair;

/**
 * <p>描 述：</p>
 * 内存表
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/11/16  20:39
 */
public class MemoryTable extends AbstractTable implements ScannableTable {

    private final List<MemoryColumn> meta;

    private final List<List<Object>> source;

    public MemoryTable(List<MemoryColumn> meta, List<List<Object>> source) {
        this.meta = meta;
        this.source = source;
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
        JavaTypeFactory typeFactory = (JavaTypeFactory) relDataTypeFactory;
        //字段名
        List<String> names = new ArrayList<>();
        //类型
        List<RelDataType> types = new ArrayList<>();
        for (MemoryColumn col : meta) {
            names.add(col.getName());
            RelDataType relDataType = typeFactory.createJavaType(col.getType());
            relDataType = SqlTypeUtil.addCharsetAndCollation(relDataType, typeFactory);
            types.add(relDataType);
        }
        return typeFactory.createStructType(Pair.zip(names, types));
    }

    @Override
    public Enumerable<Object[]> scan(DataContext dataContext) {
        return new AbstractEnumerable<Object[]>() {
            @Override
            public Enumerator<Object[]> enumerator() {
                return new MemoryEnumerator(source);
            }
        };
    }
}

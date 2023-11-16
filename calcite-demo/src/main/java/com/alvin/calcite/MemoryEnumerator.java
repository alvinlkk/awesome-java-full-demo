package com.alvin.calcite;

import java.util.List;

import org.apache.calcite.linq4j.Enumerator;

/**
 * <p>描 述：</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/11/16  20:41
 */
public class MemoryEnumerator implements Enumerator<Object[]> {

    private final List<List<Object>> source;

    private int i = -1;

    private final int length;

    public MemoryEnumerator(List<List<Object>> source){
        this.source = source;
        length = source.size();
    }

    @Override
    public Object[] current() {
        List<Object> list = source.get(i);
        return list.toArray();
    }

    @Override
    public boolean moveNext() {
        if(i < length - 1){
            i++;
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        i = 0;
    }

    @Override
    public void close() {

    }
}

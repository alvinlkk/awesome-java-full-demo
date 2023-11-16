package com.alvin.calcite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.calcite.config.CalciteConnectionProperty;
import org.apache.calcite.config.NullCollation;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;

/**
 * <p>描 述：内存查询的demo</p>
 *
 * @author cxw (332059317@qq.com)
 * @version 1.0.0
 * @since 2023/11/16  20:36
 */
public class MemoryQueryDemo {

    private Properties info;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    public void getData(List<MemoryColumn> meta, List<List<Object>> source) throws SQLException {
        // 构造Schema
        Schema memory = new MemorySchema(meta, source);
        // 设置连接参数
        info = new Properties();
        info.setProperty(CalciteConnectionProperty.DEFAULT_NULL_COLLATION.camelName(), NullCollation.LAST.name());
        info.setProperty(CalciteConnectionProperty.CASE_SENSITIVE.camelName(), "false");
        // 建立连接
        connection = DriverManager.getConnection("jdbc:calcite:", info);
        // 执行查询
        statement = connection.createStatement();
        // 取得Calcite连接
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        // 取得RootSchema RootSchema是所有Schema的父Schema
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        // 添加schema
        rootSchema.add("memory", memory);
        // 编写SQL
        String sql = "select * from memory.memory where COALESCE (id, 0) <> 2 order by id asc";
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + ":" + resultSet.getString(2) + ":"
                    + resultSet.getString(3));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
        List<MemoryColumn> meta = new ArrayList<>();
        List<List<Object>> source = new ArrayList<>();
        MemoryColumn id = new MemoryColumn("id", Long.class);
        MemoryColumn name = new MemoryColumn("name", String.class);
        MemoryColumn age = new MemoryColumn("age", Integer.class);
        meta.add(id);
        meta.add(name);
        meta.add(age);

        List<Object> line1 = new ArrayList<Object>(){
            {
                add(null);
                add("a");
                add(1);
            }
        };
        List<Object> line2 = new ArrayList<Object>(){
            {
                add(2L);
                add("b");
                add(2);
            }
        };
        List<Object> line3 = new ArrayList<Object>(){
            {
                add(3L);
                add("c");
                add(3);
            }
        };
        List<Object> line4 = new ArrayList<Object>(){
            {
                add(null);
                add("c");
                add(4);
            }
        };
        source.add(line1);
        source.add(line2);
        source.add(line4);
        source.add(line3);
        new MemoryQueryDemo().getData(meta, source);
    }

}

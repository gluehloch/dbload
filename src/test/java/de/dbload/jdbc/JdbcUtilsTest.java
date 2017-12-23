/*
 * Copyright 2014 Andre Winkler
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.dbload.jdbc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Test;

import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;
import de.dbload.utils.TransactionalTest;

/**
 * A test for {@link JdbcUtils}.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcUtilsTest extends TransactionalTest {

    @Test
    public void testJdbcUtilsMetadataFinder() throws Exception {
        ResultSetMetaData metaData = JdbcUtils.findMetaData(conn, "person");

        assertThat(metaData, notNullValue());
        assertThat(metaData.getColumnCount(), equalTo(7));
        assertThat(metaData.getColumnName(1), equalTo("id"));
        assertThat(metaData.getColumnLabel(1), equalTo("id"));
        assertThat(metaData.getColumnName(2), equalTo("name"));
        assertThat(metaData.getColumnLabel(2), equalTo("name"));
        assertThat(metaData.getColumnName(3), equalTo("vorname"));
        assertThat(metaData.getColumnLabel(3), equalTo("vorname"));
        assertThat(metaData.getColumnName(4), equalTo("age"));
        assertThat(metaData.getColumnLabel(4), equalTo("age"));
        assertThat(metaData.getColumnName(5), equalTo("sex"));
        assertThat(metaData.getColumnLabel(5), equalTo("sex"));
        assertThat(metaData.getColumnName(6), equalTo("birthday"));
        assertThat(metaData.getColumnLabel(6), equalTo("birthday"));
        assertThat(metaData.getColumnName(7), equalTo("human"));
        assertThat(metaData.getColumnLabel(7), equalTo("human"));

        assertThat(metaData.getColumnTypeName(1), equalTo("BIGINT"));
        assertThat(metaData.getColumnTypeName(2), equalTo("VARCHAR"));
        assertThat(metaData.getColumnTypeName(3), equalTo("VARCHAR"));
        assertThat(metaData.getColumnTypeName(4), equalTo("INT"));
        assertThat(metaData.getColumnTypeName(5), equalTo("VARCHAR"));
        assertThat(metaData.getColumnTypeName(6), equalTo("DATETIME"));
        assertThat(metaData.getColumnTypeName(7), equalTo("BIT"));

        assertThat(metaData.getColumnType(1), equalTo(java.sql.Types.BIGINT));
        assertThat(metaData.getColumnType(2), equalTo(java.sql.Types.VARCHAR));
        assertThat(metaData.getColumnType(3), equalTo(java.sql.Types.VARCHAR));
        assertThat(metaData.getColumnType(4), equalTo(java.sql.Types.INTEGER));
        assertThat(metaData.getColumnType(5), equalTo(java.sql.Types.VARCHAR));
        assertThat(metaData.getColumnType(6),
                equalTo(java.sql.Types.TIMESTAMP));
        assertThat(metaData.getColumnType(7), equalTo(java.sql.Types.BIT));
    }

    @Test
    public void testJdbcUtils() throws SQLException {
        ResultSetMetaData metaData = JdbcUtils.findMetaData(conn, "person");

        TableMetaData data = JdbcUtils.toTableMetaData(metaData);
        ColumnsMetaData columns = data.getColumns();

        assertThat(columns.size(), equalTo(7));
        assertThat(columns.get(0).getColumnType().getJavaSqlType(),
                equalTo(java.sql.Types.BIGINT));
        assertThat(columns.get(1).getColumnType().getJavaSqlType(),
                equalTo(java.sql.Types.VARCHAR));
        assertThat(columns.get(2).getColumnType().getJavaSqlType(),
                equalTo(java.sql.Types.VARCHAR));
        assertThat(columns.get(3).getColumnType().getJavaSqlType(),
                equalTo(java.sql.Types.INTEGER));
        assertThat(columns.get(4).getColumnType().getJavaSqlType(),
                equalTo(java.sql.Types.VARCHAR));
        assertThat(columns.get(5).getColumnType().getJavaSqlType(),
                equalTo(java.sql.Types.TIMESTAMP));
        assertThat(columns.get(6).getColumnType().getJavaSqlType(),
                equalTo(java.sql.Types.BIT));
    }

}

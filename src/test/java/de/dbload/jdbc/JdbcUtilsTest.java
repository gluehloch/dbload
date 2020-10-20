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

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;
import de.dbload.utils.TransactionalTest;
import org.junit.jupiter.api.Test;

/**
 * A test for {@link JdbcUtils}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcUtilsTest extends TransactionalTest {

    @Test
    public void testJdbcUtilsMetadataFinder() throws Exception {
        ResultSetMetaData metaData = JdbcUtils.findMetaData(conn, "person");

        assertThat(metaData).isNotNull();
        assertThat(metaData.getColumnCount()).isEqualTo(7);
        assertThat(metaData.getColumnName(1)).isEqualTo("id");
        assertThat(metaData.getColumnLabel(1)).isEqualTo("id");
        assertThat(metaData.getColumnName(2)).isEqualTo("name");
        assertThat(metaData.getColumnLabel(2)).isEqualTo("name");
        assertThat(metaData.getColumnName(3)).isEqualTo("vorname");
        assertThat(metaData.getColumnLabel(3)).isEqualTo("vorname");
        assertThat(metaData.getColumnName(4)).isEqualTo("age");
        assertThat(metaData.getColumnLabel(4)).isEqualTo("age");
        assertThat(metaData.getColumnName(5)).isEqualTo("sex");
        assertThat(metaData.getColumnLabel(5)).isEqualTo("sex");
        assertThat(metaData.getColumnName(6)).isEqualTo("birthday");
        assertThat(metaData.getColumnLabel(6)).isEqualTo("birthday");
        assertThat(metaData.getColumnName(7)).isEqualTo("human");
        assertThat(metaData.getColumnLabel(7)).isEqualTo("human");

        assertThat(metaData.getColumnTypeName(1)).isEqualTo("BIGINT");
        assertThat(metaData.getColumnTypeName(2)).isEqualTo("VARCHAR");
        assertThat(metaData.getColumnTypeName(3)).isEqualTo("VARCHAR");
        assertThat(metaData.getColumnTypeName(4)).isEqualTo("INTEGER");
        assertThat(metaData.getColumnTypeName(5)).isEqualTo("VARCHAR");
        assertThat(metaData.getColumnTypeName(6)).isEqualTo("DATETIME");
        assertThat(metaData.getColumnTypeName(7)).isEqualTo("BIT");

        assertThat(metaData.getColumnType(1)).isEqualTo(java.sql.Types.BIGINT);
        assertThat(metaData.getColumnType(2)).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(3)).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(4)).isEqualTo(java.sql.Types.INTEGER);
        assertThat(metaData.getColumnType(5)).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(6))
                .isEqualTo(java.sql.Types.TIMESTAMP);
        assertThat(metaData.getColumnType(7)).isEqualTo(java.sql.Types.BIT);
    }

    @Test
    public void testJdbcUtils() throws SQLException {
        ResultSetMetaData metaData = JdbcUtils.findMetaData(conn, "person");

        TableMetaData data = JdbcUtils.toTableMetaData(metaData);
        ColumnsMetaData columns = data.getColumns();

        assertThat(columns.size()).isEqualTo(7);
        assertThat(columns.get(0).getColumnType().getJavaSqlType())
                .isEqualTo(java.sql.Types.BIGINT);
        assertThat(columns.get(1).getColumnType().getJavaSqlType())
                .isEqualTo(java.sql.Types.VARCHAR);
        assertThat(columns.get(2).getColumnType().getJavaSqlType())
                .isEqualTo(java.sql.Types.VARCHAR);
        assertThat(columns.get(3).getColumnType().getJavaSqlType())
                .isEqualTo(java.sql.Types.INTEGER);
        assertThat(columns.get(4).getColumnType().getJavaSqlType())
                .isEqualTo(java.sql.Types.VARCHAR);
        assertThat(columns.get(5).getColumnType().getJavaSqlType())
                .isEqualTo(java.sql.Types.TIMESTAMP);
        assertThat(columns.get(6).getColumnType().getJavaSqlType())
                .isEqualTo(java.sql.Types.BIT);
    }

}

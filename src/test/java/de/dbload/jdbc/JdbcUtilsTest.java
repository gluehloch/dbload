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

import org.junit.jupiter.api.Test;

import de.dbload.meta.ColumnKey;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;
import de.dbload.utils.TransactionalTest;

/**
 * A test for {@link JdbcUtils}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class JdbcUtilsTest extends TransactionalTest {

    @Test
    void testJdbcUtilsMetadataFinder() {
        ColumnsMetaData columnsMetaData = new ColumnsMetaData();
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("id"), ColumnMetaData.Type.LONG));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("firstname"), ColumnMetaData.Type.VARCHAR));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("lastname"), ColumnMetaData.Type.VARCHAR));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("age"), ColumnMetaData.Type.INTEGER));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("sex"), ColumnMetaData.Type.VARCHAR));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("birthday"), ColumnMetaData.Type.DATE));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("human"), ColumnMetaData.Type.BIT));
        TableMetaData csvMetaData = new TableMetaData("person", columnsMetaData);

        TableMetaData metaData = JdbcUtils.findMetaData(conn, csvMetaData);

        assertThat(metaData).isNotNull();
        assertThat(metaData.getColumnCount()).isEqualTo(7);
        assertThat(metaData.getColumnName(1)).isEqualToIgnoringCase("id");
        assertThat(metaData.getColumnName(1)).isEqualToIgnoringCase("id");
        assertThat(metaData.getColumnName(2)).isEqualToIgnoringCase("firstname");
        assertThat(metaData.getColumnName(2)).isEqualToIgnoringCase("firstname");
        assertThat(metaData.getColumnName(3)).isEqualToIgnoringCase("lastname");
        assertThat(metaData.getColumnName(3)).isEqualToIgnoringCase("lastname");
        assertThat(metaData.getColumnName(4)).isEqualToIgnoringCase("age");
        assertThat(metaData.getColumnName(4)).isEqualToIgnoringCase("age");
        assertThat(metaData.getColumnName(5)).isEqualToIgnoringCase("sex");
        assertThat(metaData.getColumnName(5)).isEqualToIgnoringCase("sex");
        assertThat(metaData.getColumnName(6)).isEqualToIgnoringCase("birthday");
        assertThat(metaData.getColumnName(6)).isEqualToIgnoringCase("birthday");
        assertThat(metaData.getColumnName(7)).isEqualToIgnoringCase("human");
        assertThat(metaData.getColumnName(7)).isEqualToIgnoringCase("human");

        assertThat(metaData.getColumnTypeName(1)).isEqualTo("LONG");
        assertThat(metaData.getColumnTypeName(2)).isEqualTo("VARCHAR");
        assertThat(metaData.getColumnTypeName(3)).isEqualTo("VARCHAR");
        assertThat(metaData.getColumnTypeName(4)).isEqualTo("INTEGER");
        assertThat(metaData.getColumnTypeName(5)).isEqualTo("VARCHAR");
        assertThat(metaData.getColumnTypeName(6)).isEqualTo("DATE_TIME"); // H2
        // assertThat(metaData.getColumnTypeName(6)).isEqualTo("DATETIME"); // MariaDB
        assertThat(metaData.getColumnTypeName(7)).isEqualTo("BOOLEAN"); // H2
        // assertThat(metaData.getColumnTypeName(7)).isEqualTo("BIT"); // MariaDB

        assertThat(metaData.getColumnType(1).getJavaSqlType()).isEqualTo(java.sql.Types.BIGINT);
        assertThat(metaData.getColumnType(2).getJavaSqlType()).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(3).getJavaSqlType()).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(4).getJavaSqlType()).isEqualTo(java.sql.Types.INTEGER);
        assertThat(metaData.getColumnType(5).getJavaSqlType()).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(6).getJavaSqlType()).isEqualTo(java.sql.Types.TIMESTAMP);
        assertThat(metaData.getColumnTypeName(7)).isEqualTo("BOOLEAN"); // H2
        // assertThat(metaData.getColumnType(7)).isEqualTo(java.sql.Types.BIT); // MariaDB
    }

    @Test
    void testJdbcUtils() {
        ColumnsMetaData columnsMetaData = new ColumnsMetaData();
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("id"), ColumnMetaData.Type.LONG));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("firstname"), ColumnMetaData.Type.VARCHAR));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("lastname"), ColumnMetaData.Type.VARCHAR));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("age"), ColumnMetaData.Type.INTEGER));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("sex"), ColumnMetaData.Type.VARCHAR));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("birthday"), ColumnMetaData.Type.DATE));
        columnsMetaData.addColumn(new ColumnMetaData(ColumnKey.of("human"), ColumnMetaData.Type.BIT));
        TableMetaData csvMetaData = new TableMetaData("person", columnsMetaData);

        TableMetaData metaData = JdbcUtils.findMetaData(conn, csvMetaData);

        assertThat(metaData.getColumnCount()).isEqualTo(7);
        assertThat(metaData.getColumnType(1).getJavaSqlType()).isEqualTo(java.sql.Types.BIGINT);
        assertThat(metaData.getColumnType(2).getJavaSqlType()).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(3).getJavaSqlType()).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(4).getJavaSqlType()).isEqualTo(java.sql.Types.INTEGER);
        assertThat(metaData.getColumnType(5).getJavaSqlType()).isEqualTo(java.sql.Types.VARCHAR);
        assertThat(metaData.getColumnType(6).getJavaSqlType()).isEqualTo(java.sql.Types.TIMESTAMP);
        assertThat(metaData.getColumnType(7).getJavaSqlType()).isEqualTo(java.sql.Types.BOOLEAN); // H2
        // assertThat(columns.get(6).getColumnType().getJavaSqlType()).isEqualTo(java.sql.Types.BIT); // MariaDB
    }

}

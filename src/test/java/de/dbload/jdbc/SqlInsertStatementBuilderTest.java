/*
 * Copyright 2014 Andre Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dbload.jdbc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import de.dbload.jdbc.mysql.MySqlInsertStatementBuilder;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;

/**
 * Test for building an INSERT statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlInsertStatementBuilderTest {

    @Test
    public void testSqlInsertStatementBuilder() {
        MySqlInsertStatementBuilder builder = new MySqlInsertStatementBuilder();
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData("col1", Type.DEFAULT));
        columns.addColumn(new ColumnMetaData("col2", Type.DEFAULT));
        columns.addColumn(new ColumnMetaData("col3", Type.DEFAULT));
        TableMetaData tableMetaData = new TableMetaData("tablename", columns);
        
        SqlStatement sqlStatement = builder.create(tableMetaData);
        
        assertThat(sqlStatement.getSql(), equalTo("INSERT INTO tablename(col1, col2, col3) VALUES(?, ?, ?)"));
    }

    @Test
    public void testExecuteSqlStatement() throws SQLException {
        Connection conn = null;
        Statement stmt = conn.createStatement();
        
        stmt.execute(sql, columnNames);
    }

}

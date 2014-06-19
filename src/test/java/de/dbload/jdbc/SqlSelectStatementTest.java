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
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;

/**
 * A test class for {@link SqlSelectStatement}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlSelectStatementTest {

    @Test
    public void tesSqlSelectStatement() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.column("id", Type.NUMBER_INTEGER);
        columns.column("name", Type.STRING);
        columns.column("vorname", Type.STRING);
        columns.column("age", Type.NUMBER_INTEGER);
        columns.column("sex", Type.NUMBER_INTEGER);
        columns.column("birthday", Type.DATE);
        TableMetaData tableMetaData = new TableMetaData("person", columns);

        SqlSelectStatement sqlSelectStatement = new SqlSelectStatement(
                tableMetaData);
        assertThat(
                sqlSelectStatement.createSql(),
                equalTo("SELECT id, name, vorname, age, sex, birthday "
                        + "FROM person "
                        + "WHERE id = ? AND name = ? AND vorname = ? AND age = ? AND sex = ? AND birthday = ?"));
    }

    @Test
    public void tesSqlSelectStatementWithSingleColumnTable() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.column("id", Type.NUMBER_INTEGER);
        TableMetaData tableMetaData = new TableMetaData("person", columns);

        SqlSelectStatement sqlSelectStatement = new SqlSelectStatement(
                tableMetaData);
        assertThat(sqlSelectStatement.createSql(),
                equalTo("SELECT id FROM person WHERE id = ?"));
    }

}

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

import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;
import org.junit.jupiter.api.Test;

/**
 * A test class for {@link SqlSelectStatementBuilder}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlSelectStatementBuilderTest {

    @Test
    public void tesSqlSelectStatement() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.column("id", Type.LONG);
        columns.column("name", Type.VARCHAR);
        columns.column("vorname", Type.VARCHAR);
        columns.column("age", Type.INTEGER);
        columns.column("sex", Type.INTEGER);
        columns.column("birthday", Type.DATE);
        TableMetaData tableMetaData = new TableMetaData("person", columns);

        SqlSelectStatementBuilder sqlSelectStatement = new SqlSelectStatementBuilder(
                tableMetaData);
        assertThat(sqlSelectStatement.createSql())
                .isEqualTo("SELECT id, name, vorname, age, sex, birthday "
                        + "FROM person "
                        + "WHERE id = ? AND name = ? AND vorname = ? AND age = ? AND sex = ? AND birthday = ?");
    }

    @Test
    public void tesSqlSelectStatementWithSingleColumnTable() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.column("id", Type.INTEGER);
        TableMetaData tableMetaData = new TableMetaData("person", columns);

        SqlSelectStatementBuilder sqlSelectStatement = new SqlSelectStatementBuilder(
                tableMetaData);
        assertThat(sqlSelectStatement.createSql())
                .isEqualTo("SELECT id FROM person WHERE id = ?");
    }

}

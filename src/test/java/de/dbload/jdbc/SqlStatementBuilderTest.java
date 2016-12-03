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

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;

/**
 * Test class for {@link SqlStatementBuilderUtils}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlStatementBuilderTest {

    @Test
    public void testSqlStatementBuilderAndCondition() {
        ColumnsMetaData columns = createThreeColumnTable();
        String andCondition = SqlStatementBuilderUtils
                .createAndCondition(columns);
        assertThat(andCondition, equalTo("col1 = ? AND col2 = ? AND col3 = ?"));
    }

    @Test
    public void testSqlStatementBuilderCreateColumn() {
        ColumnsMetaData columns = createThreeColumnTable();
        String desc = SqlStatementBuilderUtils.createColumnDescription(columns);
        assertThat(desc, equalTo("col1, col2, col3"));
    }

    @Test
    public void testSqlStatementBuilderCreateColumnWithSingleColumn() {
        ColumnsMetaData columns = createSingleColumnTable();
        String desc = SqlStatementBuilderUtils.createColumnDescription(columns);
        assertThat(desc, equalTo("col1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSqlStatementBuilderCreateColumnWithNoColumns() {
        ColumnsMetaData columns = new ColumnsMetaData();
        String desc = SqlStatementBuilderUtils.createColumnDescription(columns);
        assertThat(desc, equalTo(""));
    }

    @Test
    public void testSqlStatementBuilderCreateValueColumn() {
        ColumnsMetaData columns = createTwoColumnTable();
        String desc = SqlStatementBuilderUtils
                .createQuestionMarkPerColumn(columns);
        assertThat(desc, equalTo("?, ?"));
    }

    @Test
    public void testSqlStatementBuilderCreateValueColumnWithSingleColumn() {
        ColumnsMetaData columns = createSingleColumnTable();
        String desc = SqlStatementBuilderUtils
                .createQuestionMarkPerColumn(columns);
        assertThat(desc, equalTo("?"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSqlStatementBuilderCreateColumnValueWithNoColumns() {
        ColumnsMetaData columns = new ColumnsMetaData();
        String desc = SqlStatementBuilderUtils
                .createQuestionMarkPerColumn(columns);
        assertThat(desc, equalTo(""));
    }

    private ColumnsMetaData createSingleColumnTable() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData("col1", Type.VARCHAR));
        return columns;
    }

    private ColumnsMetaData createTwoColumnTable() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData("col1", Type.VARCHAR));
        columns.addColumn(new ColumnMetaData("col2", Type.VARCHAR));
        return columns;
    }

    private ColumnsMetaData createThreeColumnTable() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData("col1", Type.VARCHAR));
        columns.addColumn(new ColumnMetaData("col2", Type.VARCHAR));
        columns.addColumn(new ColumnMetaData("col3", Type.DATE));
        return columns;
    }

}

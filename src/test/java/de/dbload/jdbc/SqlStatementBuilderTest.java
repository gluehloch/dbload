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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;

/**
 * Test class for {@link SqlStatementBuilder}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlStatementBuilderTest {

    @Test
    public void testSqlStatementBuilderCreateColumn() {
        SqlStatementBuilder builder = new SqlStatementBuilder();
        List<ColumnMetaData> columns = new ArrayList<>();
        columns.add(new ColumnMetaData("col1", Type.DEFAULT));
        columns.add(new ColumnMetaData("col2", Type.DEFAULT));
        columns.add(new ColumnMetaData("col3", Type.DATE));
        String desc = builder.createColumnDescription(columns);
        assertThat(desc, equalTo("col1, col2, col3"));
    }

    @Test
    public void testSqlStatementBuilderCreateColumnWithSingleColumn() {
        SqlStatementBuilder builder = new SqlStatementBuilder();
        List<ColumnMetaData> columns = new ArrayList<>();
        columns.add(new ColumnMetaData("col1", Type.DEFAULT));
        String desc = builder.createColumnDescription(columns);
        assertThat(desc, equalTo("col1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSqlStatementBuilderCreateColumnWithNoColumns() {
        SqlStatementBuilder builder = new SqlStatementBuilder();
        List<ColumnMetaData> columns = new ArrayList<>();
        String desc = builder.createColumnDescription(columns);
        assertThat(desc, equalTo(""));
    }

}

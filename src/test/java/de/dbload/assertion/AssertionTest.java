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

package de.dbload.assertion;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dbload.DataRow;
import de.dbload.DbloadContext;
import de.dbload.meta.TableMetaData;
import de.dbload.utils.TestConnectionFactory;
import de.dbload.utils.TestMetaDataFactory;

/**
 * Test for class {@link Assertion}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class AssertionTest {

    private DbloadContext context;

    private TableMetaData tableMetaData;

    @Before
    public void before() throws SQLException {
        context = new DbloadContext(TestConnectionFactory.connectToTestDatabase());
        Statement stmt = context.getConnection().createStatement();
        stmt.execute("INSERT INTO person VALUES (1, 'winkler', 'lars', 40, 0, '1972-01-01 01:00:00')");

        tableMetaData = TestMetaDataFactory.createPersonMetaData();
    }

    @After
    public void after() throws SQLException {
        context.getConnection().rollback();
        context.getConnection().close();
    }

    @Test
    public void testAssertion() throws SQLException {
        List<DataRow> dataRows = new ArrayList<>();
        DataRow dataRow = new DataRow();
        dataRow.put("id", "1");
        dataRow.put("vorname", "lars");
        dataRow.put("name",  "winkler");
        dataRow.put("age", "40");
        dataRow.put("sex", "0");
        dataRow.put("birthday", "1972-01-01 01:01:00");
        dataRows.add(dataRow);

        assertThat(Assertion.assertExists(context, tableMetaData, dataRows), is(true));
        assertThat(dataRow, DataRowMatchers.hasAllEntries(dataRow));
    }

}

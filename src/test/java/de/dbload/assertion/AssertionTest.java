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

import de.dbload.DbloadContext;
import de.dbload.impl.DefaultDbloadContext;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;
import de.dbload.utils.TestMetaDataFactory;
import de.dbload.utils.TransactionalTest;

/**
 * Test for class {@link Assertion}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class AssertionTest extends TransactionalTest {

    private DbloadContext context;

    private TableMetaData tableMetaData;

    @Before
    public void before() throws SQLException {
        context = new DefaultDbloadContext(conn);

        try (Statement stmt = context.getConnection().createStatement()) {
            stmt.execute(
                    "INSERT INTO person VALUES (1, 'winkler', 'lars', 40, 0, '1972-01-01 01:00:00', b'1')");
            stmt.execute(
                    "INSERT INTO person VALUES (2, 'winkler', 'andre', 43, 0, '1971-03-24 01:00:00', b'0')");
        }

        tableMetaData = TestMetaDataFactory.createPersonMetaData();
    }

    @After
    public void after() throws SQLException {
    }

    @Test
    public void testAssertion() throws SQLException {
        List<DataRow> dataRows = new ArrayList<>();
        DataRow dataRow1 = new DataRow();
        dataRow1.put("id", "1");
        dataRow1.put("vorname", "lars");
        dataRow1.put("name", "winkler");
        dataRow1.put("age", "40");
        dataRow1.put("sex", "0");
        dataRow1.put("birthday", "1972-01-01 01:00:00");
        dataRow1.put("human", "1");
        dataRows.add(dataRow1);

        DataRow dataRow2 = new DataRow();
        dataRow2.put("id", "2");
        dataRow2.put("vorname", "andre");
        dataRow2.put("name", "winkler");
        dataRow2.put("age", "43");
        dataRow2.put("sex", "0");
        dataRow2.put("birthday", "1971-03-24 01:00:00");
        dataRow2.put("human", "0");
        dataRows.add(dataRow2);

        assertThat(Assertion.assertExists(context, tableMetaData, dataRow1),
                is(true));
        assertThat(Assertion.assertExists(context, tableMetaData, dataRow2),
                is(true));

        assertThat(Assertion.assertExists(context, tableMetaData, dataRows),
                is(true));
        //
        // TODO DataRow Matcher funktioniert nur eingeschraenkt: Date == String
        // Vergleich funktioniert nur wenn eine Type-Konversation statt finden
        // wuerde.
        //
        // assertThat(dataRow, DataRowMatchers.hasAllEntries(dataRow));

        dataRow1.put("birthday", "1971-01-01 01:01:00");
        assertThat(Assertion.assertExists(context, tableMetaData, dataRows),
                is(false));

        // TODO Siehe oben. DataRow assertion bringt uns hier nicht weiter.
        // assertThat(dataRow, DataRowMatchers.hasAllEntries(dataRow));
    }

}

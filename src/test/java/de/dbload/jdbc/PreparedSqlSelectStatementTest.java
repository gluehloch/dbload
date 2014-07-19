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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.dbload.DbloadContext;
import de.dbload.impl.DefaultDbloadContext;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;
import de.dbload.utils.TestConnectionFactory;
import de.dbload.utils.TestMetaDataFactory;

/**
 * Test for class {@link PreparedSqlSelectStatement}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class PreparedSqlSelectStatementTest {

    private DbloadContext dbloadContext;
    private TableMetaData tableMetaData;

    @Before
    public void before() {
        Connection connection = TestConnectionFactory.connectToTestDatabase();
        dbloadContext = new DefaultDbloadContext(connection);
        tableMetaData = TestMetaDataFactory.createPersonMetaData();
    }

    @After
    public void after() throws SQLException {
        dbloadContext.getConnection().rollback();
        dbloadContext.getConnection().close();
    }

    @Test
    public void testPreparedSqlSelectStatement() throws SQLException {
        try (Statement stmt = dbloadContext.getConnection().createStatement()) {
            stmt.execute("INSERT INTO person(id, name, vorname, age, sex, birthday) "
                    + "VALUES(1, 'winkler', 'andre', 43, 0, '1971-03-24 01:00:00')");
            stmt.execute("INSERT INTO person(id, name, vorname, age, sex, birthday) "
                    + "VALUES(2, 'winkler', 'lars', 40, 0, '1971-03-24 01:00:00')");
        }

        try (PreparedSqlSelectStatement sql = new PreparedSqlSelectStatement(
                dbloadContext, tableMetaData)) {

            DataRow dataRowB = new DataRow();
            dataRowB.put("id",  "1");
            dataRowB.put("name", "winkler");
            dataRowB.put("vorname", "andre");
            dataRowB.put("age", "43");
            dataRowB.put("sex", "0");
            dataRowB.put("birthday", "1971-03-24 01:00:00");
            sql.execute(dataRowB);

            assertThat(sql.getResultSet().next(), is(true));
            assertThat(sql.getResultSet().getString("name"), equalTo("winkler"));
            assertThat(sql.getResultSet().getString("vorname"), equalTo("andre"));

            DataRow dataRowA = new DataRow();
            dataRowA.put("id",  "2");
            dataRowA.put("name", "winkler");
            dataRowA.put("vorname", "lars");
            dataRowA.put("age", "40");
            dataRowA.put("sex", "0");
            dataRowA.put("birthday", "1971-03-24 01:00:00");
            sql.execute(dataRowA);

            assertThat(sql.getResultSet().next(), is(true));
            assertThat(sql.getResultSet().getString("name"), equalTo("winkler"));
            assertThat(sql.getResultSet().getString("vorname"), equalTo("lars"));
        }
    }

}

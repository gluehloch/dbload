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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import de.dbload.jdbc.connector.JdbcMySqlConnector;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;

/**
 * Test for building an INSERT statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlInsertStatementBuilderTest {

    private TableMetaData tableMetaData;

    @Before
    public void setup() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.column("id", Type.NUMBER_INTEGER);
        columns.column("name", Type.STRING);
        columns.column("vorname", Type.STRING);
        columns.column("age", Type.NUMBER_INTEGER);
        columns.column("sex", Type.NUMBER_INTEGER);
        columns.column("birthday", Type.DATE);
        tableMetaData = new TableMetaData("person", columns);
    }

    @Test
    public void testSqlInsertStatementBuilder() {
        SqlInsertStatementBuilder sqlStatement = new SqlInsertStatementBuilder(tableMetaData);

        assertThat(
                sqlStatement.createSql(),
                equalTo("INSERT INTO person(id, name, vorname, age, sex, birthday) VALUES(?, ?, ?, ?, ?, ?)"));
    }

    @Test
    public void testExecuteSqlStatement() throws SQLException {
        Connection conn = JdbcMySqlConnector.createMySqlConnection("dbload",
                "dbload", "localhost", "dbload");

        SqlInsertStatementBuilder sqlStatement = new SqlInsertStatementBuilder(tableMetaData);

        DateTime jodaDateTime = DateTimeUtils.toJodaDateTime("2014-03-24 06:05:00");
        Date date = jodaDateTime.toDate();

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(date.getTime());
        java.sql.Time sqlTime = new java.sql.Time(date.getTime());

        PreparedStatement stmt = conn.prepareStatement(sqlStatement.createSql());
        stmt.setInt(1, 1);
        stmt.setString(2, "winkler");
        stmt.setString(3, "andre");
        stmt.setInt(4, 43);
        stmt.setInt(5, 0);
        stmt.setTimestamp(6, sqlTimestamp);
        stmt.execute();

        stmt.setInt(1, 2);
        stmt.setString(2, "winkler");
        stmt.setString(3, "lars");
        stmt.setInt(4, 43);
        stmt.setInt(5, 0);
        stmt.setTime(6, sqlTime);
        stmt.execute();

        /* I get the following result from the MySql command line:
        +----+---------+---------+------+------+---------------------+
        | id | name    | vorname | age  | sex  | birthday            |
        +----+---------+---------+------+------+---------------------+
        |  1 | winkler | andre   |   43 |      | 2014-03-24 06:05:00 |
        |  2 | winkler | andre   |   43 |      | 2006-05-00 00:00:00 |
        +----+---------+---------+------+------+---------------------+
        The type java.sql.Time delivers an unexpected result: Hours and
        minutes are moved to year and month? No! Time ist only time!!!
         */

        try (Statement selectStmt = conn.createStatement()) {
            ResultSet resultSet = selectStmt.executeQuery("select * from person order by id");
            assertThat(resultSet.next(), is(true));
            assertThat(resultSet.getString("name"), equalTo("winkler"));
            assertThat(resultSet.getString("vorname"), equalTo("andre"));
            assertThat(resultSet.next(), is(true));
            assertThat(resultSet.getString("name"), equalTo("winkler"));
            assertThat(resultSet.getString("vorname"), equalTo("lars"));
            assertThat(resultSet.next(), is(false));
        }

        conn.rollback();
        conn.close();
    }

}
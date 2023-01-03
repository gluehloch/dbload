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

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnKey;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;
import de.dbload.utils.TransactionalTest;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for building an INSERT statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class SqlInsertStatementBuilderTest extends TransactionalTest {

    private TableMetaData tableMetaData;

    @BeforeEach
    public void setup() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.column(ColumnKey.of("id"), Type.LONG);
        columns.column(ColumnKey.of("lastname"), Type.VARCHAR);
        columns.column(ColumnKey.of("firstname"), Type.VARCHAR);
        columns.column(ColumnKey.of("age"), Type.INTEGER);
        columns.column(ColumnKey.of("sex"), Type.INTEGER);
        columns.column(ColumnKey.of("birthday"), Type.DATE);
        tableMetaData = new TableMetaData("person", columns);
    }

    @Test
    void testSqlInsertStatementBuilder() {
        SqlInsertStatementBuilder sqlStatement = new SqlInsertStatementBuilder(
                tableMetaData);

        assertThat(sqlStatement.createSql()).isEqualTo(
                "INSERT INTO person(id, lastname, firstname, age, sex, birthday) VALUES(?, ?, ?, ?, ?, ?)");
    }

    @Test
    void testExecuteSqlStatement() throws SQLException {
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

        /*
         * I get the following result from the MySql command line:
         * +----+---------+---------+------+------+---------------------+ | id |
         * lastname | firstname| age | sex | birthday |
         * +----+---------+---------+------+------+---------------------+ | 1 |
         * winkler | andre | 43 | | 2014-03-24 06:05:00 | | 2 | winkler | andre
         * | 43 | | 2006-05-00 00:00:00 |
         * +----+---------+---------+------+------+---------------------+ The
         * type java.sql.Time delivers an unexpected result: Hours and minutes
         * are moved to year and month? No! Time ist only time!!!
         */

        try (Statement selectStmt = conn.createStatement()) {
            ResultSet resultSet = selectStmt.executeQuery("select * from person order by id");
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getString("lastname")).isEqualTo("winkler");
            assertThat(resultSet.getString("firstname")).isEqualTo("andre");
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getString("lastname")).isEqualTo("winkler");
            assertThat(resultSet.getString("firstname")).isEqualTo("lars");
            assertThat(resultSet.next()).isFalse();
        }
    }

}

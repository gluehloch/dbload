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

package de.dbload;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import de.dbload.jdbc.connector.JdbcMySqlConnector;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;

/**
 * A test case for {@link DbloadInsert}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadInsertTest {

    @Test
    public void dbloadInsert() throws SQLException {
        Connection connection = JdbcMySqlConnector.createMySqlConnection(
                "dbload", "dbload", "localhost", "dbload");
        DbloadContext context = new DbloadContext(connection);
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData("id", Type.NUMBER));
        columns.addColumn(new ColumnMetaData("name", Type.STRING));
        columns.addColumn(new ColumnMetaData("vorname", Type.STRING));
        columns.addColumn(new ColumnMetaData("age", Type.NUMBER));
        columns.addColumn(new ColumnMetaData("sex", Type.NUMBER));
        columns.addColumn(new ColumnMetaData("birthday", Type.DATE));
        TableMetaData tableMetaData = new TableMetaData("person", columns);

        try (DbloadInsert dbloadInsert = new DbloadInsert(context,
                tableMetaData, Locale.GERMANY)) {
            DataRow data = new DataRow();
            data.put("id", "0");
            data.put("name", "Winkler");
            data.put("vorname", "Andre");
            data.put("age", "43");
            data.put("sex", "0");
            data.put("birthday", "1971-03-24 06:41:11");
            dbloadInsert.insert(data);
        }

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from person");
        boolean hasNext = resultSet.next();
        String name = resultSet.getString(2);
        String vorname = resultSet.getString(3);
        Date date = resultSet.getTimestamp(6);

        assertThat(name, equalTo("Winkler"));
        assertThat(vorname, equalTo("Andre"));
        assertThat(date,
                equalTo(DateTimeUtils.toJodaDateTime("1971-03-24 06:41:11")
                        .toDate()));
    }

}

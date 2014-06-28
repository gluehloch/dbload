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

package de.dbload;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import de.dbload.assertion.Assertion;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;
import de.dbload.utils.TestConnectionFactory;
import de.dbload.utils.TestMetaDataFactory;

/**
 * A test case for {@link DbloadInsert}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadInsertTest {

    @Test
    public void dbloadInsert() throws SQLException {
        Connection connection = TestConnectionFactory.connectToTestDatabase();
        DbloadContext context = new DbloadContext(connection);
        TableMetaData tableMetaData = TestMetaDataFactory.createPersonMetaData();

        DataRow dataRow1 = new DataRow();
        dataRow1.put("id", "1");
        dataRow1.put("name", "Winkler");
        dataRow1.put("vorname", "Andre");
        dataRow1.put("age", "43");
        dataRow1.put("sex", "0");
        dataRow1.put("birthday", "1971-03-24 06:41:11");

        DataRow dataRow2 = new DataRow();
        dataRow2.put("id", "2");
        dataRow2.put("name", "Winkler");
        dataRow2.put("vorname", "Lars");
        dataRow2.put("age", "40");
        dataRow2.put("sex", "0");
        dataRow2.put("birthday", "1974-06-02 10:00:00");

        try (DbloadInsert dbloadInsert = new DbloadInsert(context,
                tableMetaData, Locale.GERMANY)) {
            dbloadInsert.insert(dataRow1);
            dbloadInsert.insert(dataRow2);
        }

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from person");
        resultSet.next();
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String vorname = resultSet.getString(3);
        Date date = resultSet.getTimestamp(6);

        assertThat(id, equalTo(1L));
        assertThat(name, equalTo("Winkler"));
        assertThat(vorname, equalTo("Andre"));
        assertThat(date,
                equalTo(DateTimeUtils.toJodaDateTime("1971-03-24 06:41:11")
                        .toDate()));

        assertThat(Assertion.assertExists(context, tableMetaData, dataRow1),
                is(true));
        assertThat(Assertion.assertExists(context, tableMetaData, dataRow2),
                is(true));

        stmt.close();
        connection.rollback();
        connection.close();
    }

}

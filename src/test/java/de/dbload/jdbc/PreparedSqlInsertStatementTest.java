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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import de.dbload.DbloadContext;
import de.dbload.impl.DefaultDbloadContext;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;
import de.dbload.utils.TestMetaDataFactory;
import de.dbload.utils.TransactionalTest;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A test case for {@link AbstractPreparedSqlStatement}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class PreparedSqlInsertStatementTest extends TransactionalTest {

    private DbloadContext dbloadContext;
    private TableMetaData tableMetaData;

    @BeforeEach
    public void before() {
        dbloadContext = new DefaultDbloadContext(conn);
        tableMetaData = TestMetaDataFactory.createPersonMetaData();
    }

    @Test
    public void dbloadInsert() throws SQLException {
        DataRow dataRow1 = new DataRow();
        dataRow1.put("id", "1");
        dataRow1.put("lastname", "Winkler");
        dataRow1.put("firstname", "Andre");
        dataRow1.put("age", "43");
        dataRow1.put("sex", "0");
        dataRow1.put("birthday", "1971-03-24 06:41:11");

        DataRow dataRow2 = new DataRow();
        dataRow2.put("id", "2");
        dataRow2.put("lastname", "Winkler");
        dataRow2.put("firstname", "Lars");
        dataRow2.put("age", "40");
        dataRow2.put("sex", "0");
        dataRow2.put("birthday", "1974-06-02 10:00:00");

        try (PreparedSqlInsertStatement dbloadInsert = new PreparedSqlInsertStatement(dbloadContext, tableMetaData)) {
            dbloadInsert.execute(dataRow1);
            dbloadInsert.execute(dataRow2);
        }

        try (Statement stmt = dbloadContext.getConnection().createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery("select * from person order by id")) {
                resultSet.next();

                assertThat(resultSet.getLong("id")).isEqualTo(1L);
                assertThat(resultSet.getString("lastname")).isEqualTo("Winkler");
                assertThat(resultSet.getString("firstname")).isEqualTo("Andre");

                Date date = resultSet.getTimestamp("birthday");
                DateTime dateTime = new DateTime(date.getTime());

                DateTime date_19710324 = DateTimeUtils.toJodaDateTime("1971-03-24 06:41:11");

                assertThat(dateTime).isEqualTo(date_19710324);
            }
        }
    }

}

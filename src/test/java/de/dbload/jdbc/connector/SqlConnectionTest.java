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

package de.dbload.jdbc.connector;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

/**
 * Test for the MySql JDBC connection string.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class SqlConnectionTest {

    @Test
    void testMySqlConnectionStringWithAutoCommit() throws Exception {
        DatabasePropertyReader dpr = new DatabasePropertyReader();
        String url = dpr.getDatabaseUrl();

        Connection conn = DriverManager.getConnection(url);
        assertThat(conn).isNotNull();
    }

    @Test
    void timestampToOffsetDateTime() {
        DatabasePropertyReader dpr = new DatabasePropertyReader();
        String url = dpr.getDatabaseUrl();
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement st = conn.createStatement();
            st.execute("DELETE FROM PERSON WHERE firstname = 'test'");
            st.execute("INSERT INTO PERSON(firstname, lastname, birthday) VALUES('test', 'test', '1971-02-03 05:55:55+00:00')");
            ResultSet rs = st.executeQuery("SELECT birthday FROM person p WHERE p.firstname = 'test'");
            rs.next();
            OffsetDateTime odt = rs.getObject(1, OffsetDateTime.class);
            assertThat(odt).isInstanceOf(OffsetDateTime.class);

            System.out.println(odt.getClass().getName());  // java.time.OffsetDateTime
            System.out.println(odt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX")));  // 1981-02-03 00:00:00+01:00
            System.out.println(odt.atZoneSameInstant(ZoneId.of("Europe/Berlin")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX z")));  // 1981-02-03 00:00:00 CET
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}

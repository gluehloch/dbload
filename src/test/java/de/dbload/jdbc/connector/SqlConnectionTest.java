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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");
    private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX z");
    private ZoneId europeBerlin = ZoneId.of("Europe/Berlin");

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
            st.execute("INSERT INTO PERSON(firstname, lastname, birthday) VALUES('test', 'test', '1971-02-03 05:55:55+4:00')");
            st.execute("INSERT INTO PERSON(firstname, lastname, birthday) VALUES('test', 'test', '1971-02-03 07:55:55')");
            ResultSet rs = st.executeQuery("SELECT birthday FROM person p WHERE p.firstname = 'test'");
            read(rs);
            read(rs);

            ResultSet rs2 = st.executeQuery("select now() from dual");
            read(rs2);

            st.execute("DELETE FROM PERSON");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void read(ResultSet rs) throws Exception {
        rs.next();
        OffsetDateTime odt = rs.getObject(1, OffsetDateTime.class);
        assertThat(odt).isInstanceOf(OffsetDateTime.class);
        System.out.println("=== row ===");
        System.out.println("OffsetDateTime.format: " + odt.format(formatter));
        System.out.println("ZonedDateTime.format : " + odt.atZoneSameInstant(europeBerlin).format(formatter2));
    }

}

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.Connection;

import org.junit.Test;

import de.dbload.jdbc.connector.JdbcMySqlConnector;

/**
 * Test for {@link JdbcMySqlConnector}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcMySqlConnectorTest {

    @Test
    public void testJdbcMySqlConnector() throws Exception {
        Connection connection = JdbcMySqlConnector.createMySqlConnection(
                "dbload", "dbload", "localhost", "dbload");
        assertThat(connection, notNullValue());

        connection.rollback();
        connection.close();
    }

    @Test
    public void testJdbcMySqlConnectorCreateUrl() {
        assertThat(JdbcMySqlConnector.createUrl("localhost", "myDatabase"),
                equalTo("jdbc:mysql://localhost/myDatabase"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJdbcMySqlConnectorCreateUrlWithBlankParam() {
        assertThat(JdbcMySqlConnector.createUrl(" ", "myDatabase"),
                equalTo("jdbc:mysql://localhost/myDatabase"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJdbcMySqlConnectorCreateUrlWithNullParam() {
        assertThat(JdbcMySqlConnector.createUrl(null, "myDatabase"),
                equalTo("jdbc:mysql://localhost/myDatabase"));
    }

}

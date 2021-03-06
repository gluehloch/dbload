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

import org.junit.jupiter.api.Test;

/**
 * Test for the MySql JDBC connection string.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlConnectionTest {

    @Test
    public void testMySqlConnectionStringWithAutoCommit() throws Exception {
        DatabasePropertyReader dpr = new DatabasePropertyReader();
        String url = dpr.getDatabaseUrl();

        Connection conn = DriverManager.getConnection(url);
        assertThat(conn).isNotNull();
    }

}

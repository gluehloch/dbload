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

import java.sql.Connection;

/**
 * Some utility methods to create a {@link Connection} to a MySQL database. All
 * connections are created with <code>autocommit=false</code>.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class JdbcMySqlConnector extends AbstractJdbcConnector {

    /** The MariaDB connection string. */
    private static final String MYSQL_CONNECTION = "jdbc:mysql://%s/%s";

    private static JdbcMySqlConnector jdbcMySqlConnector;

    public JdbcMySqlConnector() {
        super(MYSQL_CONNECTION);    }

    private static JdbcMySqlConnector connector() {
        synchronized (JdbcMySqlConnector.class) {
            if (jdbcMySqlConnector == null) {
                jdbcMySqlConnector = new JdbcMySqlConnector();
            }
        }
        return jdbcMySqlConnector;
    }

    public static Connection createMySqlConnection(String user, String password,
            String server, int port, String databaseName) {

        return connector().createConnection(user, password, server, port,
                databaseName);
    }

    public static Connection createMySqlConnection(String user, String password,
            String server, String databaseName) {

        return connector().createConnection(user, password, server,
                databaseName);
    }

}

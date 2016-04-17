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
public class JdbcMariaDbConnector extends AbstractJdbcConnector {

    /** The MySQL connection string. */
    private static final String MARIADB_CONNECTION = "jdbc:mariadb://%s/%s";

    private static JdbcMariaDbConnector jdbcMariaDbConnector;

    public JdbcMariaDbConnector() {
        super(MARIADB_CONNECTION);
    }

    private static JdbcMariaDbConnector connector() {
        synchronized (JdbcMariaDbConnector.class) {
            if (jdbcMariaDbConnector == null) {
                jdbcMariaDbConnector = new JdbcMariaDbConnector();
            }
        }
        return jdbcMariaDbConnector;
    }

    public static Connection createMariaDbConnection(String user, String password,
            String server, int port, String databaseName) {

        return connector().createConnection(user, password, server, port,
                databaseName);
    }

    public static Connection createMariaDbConnection(String user, String password,
            String server, String databaseName) {

        return connector().createConnection(user, password, server,
                databaseName);
    }

    /*
     * public static String createUrl(String server, String databaseName) { if
     * (StringUtils.isBlank(server) || StringUtils.isBlank(databaseName)) {
     * throw new IllegalArgumentException(); }
     * 
     * return String.format("jdbc:mysql://%s/%s", server, databaseName); }
     * 
     * public static String createUrl(String server, int port, String
     * databaseName) { if (StringUtils.isBlank(server) ||
     * StringUtils.isBlank(databaseName)) { throw new
     * IllegalArgumentException(); }
     * 
     * // TODO "jdbc:mysql://localhost/test?user=monty&password=greatsqldb //
     * TODO "jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword"
     * return String.format("jdbc:mysql://%s:%d/%s", server, port,
     * databaseName); }
     */

}

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
import java.sql.DriverManager;
import java.sql.SQLException;

import de.dbload.impl.DbloadException;

/**
 * Creates a JDBC connection.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcConnector {

    /**
     * Creates a JDBC connection.
     * 
     * @param user
     *            the database user
     * @param password
     *            the database user password
     * @param dbUrl
     *            the database url
     * @return a JDBC connection
     */
    public static Connection createConnection(String user, String password,
            String dbUrl) {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DbloadException(String.format(
                    "Unable to connect to database with url '%s'", dbUrl), ex);
        }
        return conn;
    }

}

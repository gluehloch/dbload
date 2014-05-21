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

package de.dbload.jdbc.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import de.dbload.DbloadException;

/**
 * Some utility methods to create a {@link Connection} to a MySQL database.
 * All connections are created with <code>autocommit=false</code>.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcMySqlConnector {

    public static Connection createMySqlConnection(String user,
	    String password, String server, int port, String databaseName) {

	return createConnection(user, password,
		createUrl(server, port, databaseName));
    }

    public static Connection createMySqlConnection(String user,
	    String password, String server, String databaseName) {

	return createConnection(user, password, createUrl(server, databaseName));
    }

    public static String createUrl(String server, String databaseName) {
	if (StringUtils.isBlank(server) || StringUtils.isBlank(databaseName)) {
	    throw new IllegalArgumentException();
	}

	return String.format("jdbc:mysql://%s/%s", server, databaseName);
    }

    public static String createUrl(String server, int port, String databaseName) {
	if (StringUtils.isBlank(server) || StringUtils.isBlank(databaseName)) {
	    throw new IllegalArgumentException();
	}

	// TODO "jdbc:mysql://localhost/test?user=monty&password=greatsqldb
	return String.format("jdbc:mysql://%s:%d/%s", server, port,
		databaseName);
    }

    private static Connection createConnection(String user, String password,
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

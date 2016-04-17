/*
 * Copyright 2016 Andre Winkler
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

import org.apache.commons.lang.StringUtils;

/**
 * The utility to connect to a database properly.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
abstract class AbstractJdbcConnector {

    /** Connect with host and port. */
    private static String HOST_AND_PORT = "%s:%d";

    private final String connectionTemplate;

    public AbstractJdbcConnector(String _connectionTemplate) {
        connectionTemplate = _connectionTemplate;
    }

    public Connection createConnection(String user, String password,
            String server, int port, String databaseName) {

        return JdbcConnector.createConnection(user, password,
                createUrl(server, port, databaseName));
    }

    public Connection createConnection(String user, String password,
            String server, String databaseName) {

        return JdbcConnector.createConnection(user, password,
                createUrl(server, databaseName));
    }

    String getConnectionTemplate() {
        return connectionTemplate;
    }

    String createUrl(String server, String databaseName) {

        if (StringUtils.isBlank(server) || StringUtils.isBlank(databaseName)) {
            throw new IllegalArgumentException();
        }

        return String.format(getConnectionTemplate(), server, databaseName);
    }

    String createUrl(String server, int port, String databaseName) {

        if (StringUtils.isBlank(server) || StringUtils.isBlank(databaseName)
                || port < 1) {

            throw new IllegalArgumentException();
        }

        return createUrl(String.format(HOST_AND_PORT, server, port),
                databaseName);
    }

}

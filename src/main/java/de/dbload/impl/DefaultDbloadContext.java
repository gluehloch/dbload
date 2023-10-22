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

package de.dbload.impl;

import java.sql.Connection;

import de.dbload.DbloadContext;
import de.dbload.JdbcTypeConverter;

/**
 * Holds the database connection.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DefaultDbloadContext implements DbloadContext {

    private final Connection conn;
    private final JdbcTypeConverter jdbcTypeConverter;

    /**
     * Constructor.
     *
     * @param conn              jdbc database connection
     * @param jdbcTypeConverter jdbc type converter
     */
    public DefaultDbloadContext(Connection conn, JdbcTypeConverter jdbcTypeConverter) {
        this.conn = conn;
        this.jdbcTypeConverter = jdbcTypeConverter;
    }

    @Override
    public Connection connection() {
        return conn;
    }

    @Override
    public JdbcTypeConverter converter() {
        return jdbcTypeConverter;
    }

}

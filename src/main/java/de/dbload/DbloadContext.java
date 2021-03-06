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

package de.dbload;

import java.sql.Connection;

/**
 * Defines JDBC connection and JDBC type converter.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public interface DbloadContext {

    /**
     * Returns a JDBC connection.
     *
     * @return a JDBC connection
     */
    Connection getConnection();

    /**
     * Returns a JDBC type converter
     *
     * @return a JDBC type converter
     */
    JdbcTypeConverter getJdbcTypeConverter();

}
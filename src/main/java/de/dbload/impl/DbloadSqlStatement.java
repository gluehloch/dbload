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

import java.io.Closeable;
import java.sql.SQLException;

import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * A dbload sql statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
interface DbloadSqlStatement extends Closeable {

    /**
     * Set the current table name.
     *
     * @param  tableMetaData table name.
     * @throws SQLException Da ging was schief...
     */
    void newTableMetaData(TableMetaData tableMetaData) throws SQLException;

    /**
     * Get the current table name.
     *
     * @return the current table name
     */
    String getTableName();

    /**
     * Exceute a SQL statement with <code>data</code> as parameter.
     *
     * @param  data         Holds the data
     * @throws SQLException Da ging was schief...
     */
    void execute(DataRow data) throws SQLException;

    /**
     * Close this resource.
     */
    void close();

}

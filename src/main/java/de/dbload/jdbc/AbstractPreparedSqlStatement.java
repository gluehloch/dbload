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

package de.dbload.jdbc;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.dbload.DataRow;
import de.dbload.DbloadContext;
import de.dbload.meta.TableMetaData;

/**
 * Create and hold a {@link PreparedStatement}.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public abstract class AbstractPreparedSqlStatement implements Closeable {

    private final DbloadContext dbloadContext;
    private final PreparedStatement stmt;
    private final TableMetaData tableMetaData;

    private boolean executionResult;

    /**
     * Constructor.
     * 
     * @param _context
     *            dbload context
     * @param _tableMetaData
     *            table meta data
     * @throws SQLException
     *             Something is wrong
     */
    public AbstractPreparedSqlStatement(DbloadContext _context,
            TableMetaData _tableMetaData) throws SQLException {

        dbloadContext = _context;
        tableMetaData = _tableMetaData;
        stmt = PreparedStatementBuilder.prepareStatement(_context,
                new SqlInsertStatement(_tableMetaData));
    }

    /**
     * The prepared SQL statement.
     * 
     * @return the prepared SQL statement
     */
    protected PreparedStatement getPreparedStatement() {
        return stmt;
    }

    protected void applyParams(DataRow dataRow) throws SQLException {
        PreparedStatementBuilder.applyParams(dataRow, tableMetaData,
                dbloadContext.getJdbcTypeConverter(), stmt);
    }

    /**
     * Execute an insert for the assigned data.
     * 
     * @param data
     *            the data to use as parameters for the query
     * @throws SQLException
     *             Something is wrong
     */
    public abstract void execute(DataRow data) throws SQLException;

    public void close() {
        JdbcUtils.close(stmt);
    }

    /**
     * Returns the execution result.
     * 
     * @return Returns <code>true</code>, if the executed statement returns a
     *         {@link ResultSet} object. Returns <code>false</code>, if the
     *         executed statement returns the count of executed updates.
     */
    boolean isResultSet() {
        return executionResult;
    }

}

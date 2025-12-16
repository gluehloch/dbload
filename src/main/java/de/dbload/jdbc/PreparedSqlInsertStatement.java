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

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dbload.DbloadContext;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * Holds a prepared INSERT statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class PreparedSqlInsertStatement extends AbstractPreparedSqlStatement {

    private static final Logger LOG = LoggerFactory
            .getLogger(PreparedSqlInsertStatement.class);

    private boolean preparedStatementReturnsWithResultSet;
    private boolean batchAdded = false;

    public PreparedSqlInsertStatement(final DbloadContext _context, final TableMetaData _tableMetaData)
            throws SQLException {

        super(_context, _tableMetaData, PreparedStatementBuilder
                .prepareStatement(_context, new SqlInsertStatementBuilder(_tableMetaData)));
    }

    public void addBatch(DataRow data) throws SQLException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("AddBatch \n\t[{}] with data \n\t[{}]", getPreparedStatement(), data);
        }
        applyParams(data);
        getPreparedStatement().addBatch();
        batchAdded = true;
    }

    public final void execute() throws SQLException {
        if (batchAdded) {
            getPreparedStatement().executeBatch();
            batchAdded = false;
        } else {
            if (LOG.isInfoEnabled()) {
                LOG.info("No batch to execute: {}", getPreparedStatement());
            }
        }
    }

    /**
     * Returns the execution result.
     *
     * @return Returns <code>true</code>, if the executed statement returns a {@link ResultSet} object. Returns
     *         <code>false</code>, if the executed statement returns the count of executed updates.
     */
    public boolean isExecutionResult() {
        return preparedStatementReturnsWithResultSet;
    }

    @Override
    boolean hasResultSet() {
        return preparedStatementReturnsWithResultSet;
    }

}

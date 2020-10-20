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

import de.dbload.DbloadContext;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds a prepared SELECT statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class PreparedSqlSelectStatement extends AbstractPreparedSqlStatement {

    private static final Logger LOG = LoggerFactory
            .getLogger(PreparedSqlSelectStatement.class);

    private boolean preparedStatementReturnsWithResultSet;
    private ResultSet resultSet;

    public PreparedSqlSelectStatement(DbloadContext _context,
            TableMetaData _tableMetaData) throws SQLException {

        super(_context, _tableMetaData, PreparedStatementBuilder
                .prepareStatement(_context, new SqlSelectStatementBuilder(
                        _tableMetaData)));
    }

    @Override
    public void execute(DataRow data) throws SQLException {
        applyParams(data);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Executing \n\t[{}] with data \n\t[{}]",
                    getPreparedStatement(), data);
        }

        preparedStatementReturnsWithResultSet = getPreparedStatement()
                .execute();
        resultSet = getPreparedStatement().getResultSet();
    }

    /**
     * Returns the execution result.
     *
     * @return Returns <code>true</code>, if the executed statement returns a
     * {@link ResultSet} object. Returns <code>false</code>, if the
     * executed statement returns the count of executed updates.
     */
    @Override
    public boolean hasResultSet() {
        return preparedStatementReturnsWithResultSet;
    }

    /**
     * Returns the result set.
     *
     * @return the result set
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

}

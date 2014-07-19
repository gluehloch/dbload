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

package de.dbload.assertion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dbload.DbloadContext;
import de.dbload.jdbc.PreparedStatementBuilder;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * The context of one or many assertions.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class DbloadAssertionContext {

    private static final Logger LOG = LoggerFactory
            .getLogger(DbloadAssertionContext.class);

    private final DbloadContext dbloadContext;
    private final TableMetaData tableMetaData;
    private final PreparedStatement statement;

    /**
     * Constructor.
     *
     * @param _dbloadContext
     *            the database context
     * @param _tableMetaData
     *            the meta data of the table under test
     * @param _statement
     *            the prepared select statement
     */
    DbloadAssertionContext(DbloadContext _dbloadContext,
            TableMetaData _tableMetaData, PreparedStatement _statement) {

        dbloadContext = _dbloadContext;
        tableMetaData = _tableMetaData;
        statement = _statement;
    }

    /**
     * Start assertion test
     *
     * @param _dataRow
     *            the expected data
     * @return <code>true</code> or <code>false</code>
     * @throws SQLException
     *             something bad happens
     */
    public boolean assertExists(DataRow _dataRow) throws SQLException {
        PreparedStatementBuilder.applyParams(_dataRow, tableMetaData,
                dbloadContext.getJdbcTypeConverter(), statement);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Execute query {}", statement);
        }

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

}

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

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.dbload.DbloadContext;
import de.dbload.JdbcTypeConverter;
import de.dbload.impl.DefaultDbloadContext;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * Creates a {@link PreparedStatement} by {@link DefaultDbloadContext} and
 * {@link TableMetaData}.
 *
 * @author Andre Winkler. https://www.andre-winkler.de
 */
public class PreparedStatementBuilder {

    /**
     * Creates a {@link PreparedStatement}.
     *
     * @param _context     dbload context (database connection)
     * @param sqlStatement The SQL statement (INSERT, SELECT or UPDATE)
     * @return a PreparedStatement
     * @throws SQLException that was not ok!
     */
    public static PreparedStatement prepareStatement(DbloadContext _context,
            SqlStatementBuilder sqlStatement) throws SQLException {

        return _context.getConnection().prepareStatement(sqlStatement.createSql());
    }

    /**
     * Apply some data to the parameters of a JDBC prepared statement.
     *
     * @param  _data              the data to apply to the params
     * @param  _tableMetaData     the table meta data
     * @param  _jdbcTypeConverter a jdbc type converter
     * @param  _stmt              a JDBC prepared statement
     * @throws SQLException       that was not ok!
     */
    public static void applyParams(DataRow _data, TableMetaData _tableMetaData,
            JdbcTypeConverter _jdbcTypeConverter, PreparedStatement _stmt)
            throws SQLException {

        int index = 1; // JDBC parameter index starts with 1
        for (ColumnMetaData columnMetaData : _tableMetaData.getColumns()) {
            String value = _data.get(columnMetaData.getColumnKey());
            Object typedValue = _jdbcTypeConverter.convert(columnMetaData, value);

            _jdbcTypeConverter.setTypedValue(_stmt, index, columnMetaData, typedValue);
            index++;
        }
    }

}

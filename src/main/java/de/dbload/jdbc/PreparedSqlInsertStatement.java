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
import java.sql.SQLException;

import de.dbload.DataRow;
import de.dbload.DbloadContext;
import de.dbload.meta.TableMetaData;

/**
 * Create and hold a {@link PreparedStatement}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class PreparedSqlInsertStatement implements Closeable {

    private final DbloadContext dbloadContext;
    private final PreparedStatement stmt;
    private final TableMetaData tableMetaData;

    public PreparedSqlInsertStatement(DbloadContext _context,
            TableMetaData _tableMetaData) throws SQLException {

        dbloadContext = _context;
        tableMetaData = _tableMetaData;
        stmt = PreparedStatementBuilder.prepareStatement(_context,
                new SqlInsertStatement(_tableMetaData));
    }

    /**
     * Execute an insert for the assigned data.
     *
     * @param data
     *            the data to insert
     * @throws SQLException
     *             Something is wrong
     */
    public void execute(DataRow data) throws SQLException {
        PreparedStatementBuilder.applyParams(data, tableMetaData,
                dbloadContext.getJdbcTypeConverter(), stmt);
        stmt.execute();
    }

    public void close() {
        JdbcUtils.close(stmt);
    }

}

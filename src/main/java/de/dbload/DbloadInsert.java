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

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

import de.dbload.jdbc.JdbcTypeConverter;
import de.dbload.jdbc.JdbcUtils;
import de.dbload.jdbc.PreparedStatementBuilder;
import de.dbload.jdbc.SqlInsertStatement;
import de.dbload.meta.TableMetaData;

/**
 * Load data.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class DbloadInsert implements Closeable {

    private final PreparedStatement stmt;
    private final TableMetaData tableMetaData;
    private final Locale locale;

    public DbloadInsert(DbloadContext _context, TableMetaData _tableMetaData,
            Locale _locale) throws SQLException {

        locale = _locale;
        tableMetaData = _tableMetaData;
        stmt = PreparedStatementBuilder.prepareStatement(_context,
                new SqlInsertStatement(_tableMetaData));
    }

    public void insert(DataRow data) throws SQLException {
        PreparedStatementBuilder.applyParams(data, tableMetaData,
                new JdbcTypeConverter(locale), stmt);
        stmt.execute();
    }

    public void close() {
        JdbcUtils.close(stmt);
    }

}

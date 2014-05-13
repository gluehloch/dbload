/*
 * Copyright 2014 Andre Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dbload;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.joda.time.DateTime;

import de.dbload.jdbc.JdbcUtils;
import de.dbload.jdbc.SqlInsertStatement;
import de.dbload.jdbc.common.DefaultInsertStatementBuilder;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;

/**
 * Load data.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadInsert implements Closeable {

    private SqlInsertStatement sqlStatement;
    private PreparedStatement stmt;
    private TableMetaData tableMetaData;

    public DbloadInsert(DbloadContext _context, TableMetaData _tableMetaData)
            throws SQLException {

        tableMetaData = _tableMetaData;
        stmt = prepareStatement(_context, _tableMetaData);
    }

    private PreparedStatement prepareStatement(DbloadContext _context,
            TableMetaData tableMetaData) throws SQLException {

        DefaultInsertStatementBuilder builder = new DefaultInsertStatementBuilder();
        sqlStatement = builder.create(tableMetaData);
        return _context.getConnection().prepareStatement(sqlStatement.getSql());
    }

    public void insert(DataRow data) throws SQLException {
        applyParams(data, tableMetaData, stmt);
        stmt.execute();
    }

    private void applyParams(DataRow data, TableMetaData tableMetaData,
            PreparedStatement stmt) throws SQLException {

        stmt.setString(1, "Hallo");

        DateTime jodaDateTime = DateTimeUtils.toJodaDateTime("20140324060500");
        Date date = jodaDateTime.toDate();

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(date.getTime());

        stmt.setInt(1, 1);
        stmt.setString(2, "winkler");
        stmt.setString(3, "andre");
        stmt.setInt(4, 43);
        stmt.setInt(5, 0);
        stmt.setTimestamp(6, sqlTimestamp);
    }

    public void close() {
        JdbcUtils.close(stmt);
    }

}

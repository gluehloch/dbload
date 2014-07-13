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

import java.sql.SQLException;

import de.dbload.DataRow;
import de.dbload.DbloadContext;
import de.dbload.jdbc.PreparedSqlInsertStatement;
import de.dbload.meta.TableMetaData;

/**
 * Writes the insert sql script to the database.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadSqlInsert implements DbloadInsert {

    private final DbloadContext context;

    private PreparedSqlInsertStatement preparedSqlInsertStatement;

    /**
     * Constructor.
     *
     * @param _context
     *            The dbload context.
     */
    public DbloadSqlInsert(DbloadContext _context) {
        context = _context;
    }

    @Override
    public void newTableMetaData(TableMetaData tableMetaData) throws SQLException {
        preparedSqlInsertStatement = new PreparedSqlInsertStatement(context,
                tableMetaData);
    }

    @Override
    public void insert(DataRow dataRow) throws SQLException {
        preparedSqlInsertStatement.execute(dataRow);
    }

    @Override
    public void close() {
        preparedSqlInsertStatement.close();
    }

}

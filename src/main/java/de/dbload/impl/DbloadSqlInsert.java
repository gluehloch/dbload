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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dbload.DbloadContext;
import de.dbload.jdbc.AbstractPreparedSqlStatement;
import de.dbload.jdbc.PreparedSqlInsertStatement;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.StopWatch;

/**
 * Writes the insert sql script to the database.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadSqlInsert implements DbloadSqlStatement {

    private static final Logger LOG = LoggerFactory.getLogger(DbloadSqlInsert.class);

    private final DbloadContext context;
    private final StopWatch stopWatch = new StopWatch();

    private TableMetaData currentTableMetaData;
    private int numberOfRows;
    private AbstractPreparedSqlStatement preparedSqlStatement;

    /**
     * Constructor.
     *
     * @param _context The dbload context.
     */
    public DbloadSqlInsert(DbloadContext _context) {
        context = _context;
    }

    @Override
    public void newTableMetaData(TableMetaData tableMetaData) throws SQLException {
        close();
        currentTableMetaData = tableMetaData;
        numberOfRows = 0;
        stopWatch.start();
        preparedSqlStatement = new PreparedSqlInsertStatement(context, tableMetaData);
    }

    @Override
    public void addBatch(DataRow dataRow) throws SQLException {
        preparedSqlStatement.addBatch(dataRow);
        numberOfRows++;
    }

    @Override
    public void close() {
        if (preparedSqlStatement != null) {
            preparedSqlStatement.close();
            stopWatch.stop();

            LOG.atInfo().log("DBLOAD stats: Tablename: {}, Number of rows: {}, Time [ms]: {}",
                    new Object[] { currentTableMetaData.getTableName(),
                            numberOfRows, stopWatch.duration().toMillis()
                    });

            numberOfRows = 0;
        }
    }

    @Override
    public void execute() throws SQLException {
        if (preparedSqlStatement != null) {
            preparedSqlStatement.execute();
        }
    }

    @Override
    public String toString() {
        return preparedSqlStatement.toString();
    }

    @Override
    public String getTableName() {
        return currentTableMetaData.getTableName();
    }

}

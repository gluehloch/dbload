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

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dbload.DbloadContext;
import de.dbload.jdbc.PreparedSqlSelectStatement;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * Executes the sql select script to the database.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadSqlSelect implements DbloadSqlStatement {

    private final DbloadContext context;

    private PreparedSqlSelectStatement preparedSqlStatement;

    private int resultRowCounter;

    /**
     * Constructor.
     * 
     * @param _context
     *            The dbload context.
     */
    public DbloadSqlSelect(DbloadContext _context) {
        context = _context;
    }

    @Override
    public void newTableMetaData(TableMetaData tableMetaData)
            throws SQLException {

        if (preparedSqlStatement != null) {
            preparedSqlStatement.close();
        }
        
        preparedSqlStatement = new PreparedSqlSelectStatement(context,
                tableMetaData);
    }

    @Override
    public void execute(DataRow dataRow) throws SQLException {
        // TODO ganz so universell ist das hier nicht. Diese Klasse wird fuer
        // Assertions verwendet. Hier wird eine Zeile erwartet.
        preparedSqlStatement.execute(dataRow);
        ResultSet resultSet = preparedSqlStatement.getResultSet();
        resultRowCounter = 0;
        while (resultSet.next()) {
            resultRowCounter++;
        }
    }

    @Override
    public void close() {
        preparedSqlStatement.close();
    }

    public int getResultRowCounter() {
        return resultRowCounter;
    }

}

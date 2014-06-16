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

package de.dbload.jdbc;

import de.dbload.meta.TableMetaData;

/**
 * The representation of a SQL statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlInsertStatement {

    private TableMetaData tableMetaData;

    public SqlInsertStatement(TableMetaData _tableMetaData) {
        tableMetaData = _tableMetaData;
    }

    /**
     * Creates an INSERT SQL string like
     * <pre>INSERT INTO tablename(col1, col2, col3) VALUES (?, ?, ?);</pre>
     *
     * @return A SQL insert statement
     */
    public String createSql() {
        StringBuffer stmt = new StringBuffer("INSERT INTO ");
        stmt.append(tableMetaData.getTableName());

        String columnDescription = SqlStatementBuilderUtils
                .createColumnDescription(tableMetaData.getColumns());
        stmt.append("(").append(columnDescription).append(") VALUES(");

        String questionMarkPerColumn = SqlStatementBuilderUtils
                .createColumnValues(tableMetaData.getColumns());
        stmt.append(questionMarkPerColumn).append(")");

        return stmt.toString();
    }

}

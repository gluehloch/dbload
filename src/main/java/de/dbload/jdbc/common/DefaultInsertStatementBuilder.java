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

package de.dbload.jdbc.common;

import de.dbload.jdbc.SqlStatement;
import de.dbload.jdbc.SqlStatementBuilderUtils;
import de.dbload.meta.TableMetaData;

/**
 * Creates an insert statement for the MySQL database.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DefaultInsertStatementBuilder implements InsertStatementBuilder {

    public SqlStatement create(TableMetaData tableMetaData) {
        // INSERT INTO tablename(col1, col2, col3) VALUES (?, ?, ?);
        StringBuffer stmt = new StringBuffer("INSERT INTO ");
        stmt.append(tableMetaData.getTableName());
        
        String columnDescription = SqlStatementBuilderUtils.createColumnDescription(tableMetaData.getColumns());
        stmt.append("(").append(columnDescription).append(") VALUES(");
        
        String questionMarkPerColumn = SqlStatementBuilderUtils.createColumnValues(tableMetaData.getColumns());
        stmt.append(questionMarkPerColumn).append(")");
        
        SqlStatement sqlStmt = new SqlStatement(stmt.toString());
        return sqlStmt;
    }

}

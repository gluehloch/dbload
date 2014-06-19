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

import de.dbload.meta.TableMetaData;

/**
 * Creates a SELECT SQL string like
 *
 * <pre>
 * SELECT c1, c2, c3 FROM tablename WHERE c1 = ? AND c2 = ? AND c3 = ?;
 * </pre>
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlSelectStatement implements SqlStatement {

    private TableMetaData tableMetaData;

    /**
     * Constructor.
     *
     * @param _tableMetaData
     *            the meta data of a database table
     */
    public SqlSelectStatement(TableMetaData _tableMetaData) {
        tableMetaData = _tableMetaData;
    }

    /**
     * Create an SELECT SQL string like
     *
     * <pre>
     * SELECT c1, c2, c3 FROM tablename WHERE c1 = ? AND c2 = ? AND c3 = ?;
     * </pre>
     *
     * @return A SQL select statement
     */
    @Override
    public String createSql() {
        StringBuffer stmt = new StringBuffer("SELECT ");
        stmt.append(SqlStatementBuilderUtils
                .createColumnDescription(tableMetaData.getColumns()));
        stmt.append(" FROM ");
        stmt.append(tableMetaData.getTableName());
        stmt.append(" WHERE ");
        stmt.append(SqlStatementBuilderUtils.createAndCondition(tableMetaData
                .getColumns()));
        return stmt.toString();
    }

}

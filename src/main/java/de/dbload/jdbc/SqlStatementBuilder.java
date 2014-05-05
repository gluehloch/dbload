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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.TableMetaData;
import de.dbload.meta.ColumnMetaData.Type;

/**
 * Creates SQL statements.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlStatementBuilder {

    public SqlStatement insert(TableMetaData tableMetaData) {
        StringBuffer insertSqlCommand = new StringBuffer("INSERT INTO ");
        insertSqlCommand.append(tableMetaData.getTableName());
        insertSqlCommand.append('(');

        List<ColumnMetaData> columns = tableMetaData.getColumns();
        createColumnDescription(columns);

        insertSqlCommand.append(")\r\n VALUES (");
        for (int i = 0; i < columns.size(); i++) {
            ColumnMetaData column = columns.get(i);
            Object insertme;
            if (column.getColumnType() == Type.DATE) {
                insertme = "to_date('" + data.get(i) + "', '"
                        + ORACLE_DATE_FORMAT + "')";
            } else {
                // int[] types = jdbcInsert.getInsertTypes();
                // insertme = InOutUtils.toString(types[i], data.get(i));
                String val = data.get(i);
                if (StringUtils.isBlank(val)) {
                    insertme = "NULL";
                } else {
                    insertme = "'" + val + "'";
                }
            }

            insertSqlCommand.append(insertme);
            if (i < columns.size() - 1) {
                insertSqlCommand.append(',');
            }
        }
        insertSqlCommand.append(')');
    }

    /**
     * Creates a list of column names like "col1, col2, col3" from a meta description.
     *
     * @param columns the column description
     * @return a String
     */
    public String createColumnDescription(List<ColumnMetaData> columns) {
        if (columns == null || columns.size() == 0) {
            throw new IllegalArgumentException("columns is null or empty");
        }

        StringBuffer insertSqlCommand = new StringBuffer();
        for (int i = 0; i < columns.size(); i++) {
            ColumnMetaData column = columns.get(i);
            insertSqlCommand.append(column.getColumnName());
            if (i < columns.size() - 1) {
                insertSqlCommand.append(", ");
            }
        }
        return insertSqlCommand.toString();
    }

}

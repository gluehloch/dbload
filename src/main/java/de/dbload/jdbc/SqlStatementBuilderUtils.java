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

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;

/**
 * Creates SQL statements.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class SqlStatementBuilderUtils {

    /**
     * Creates a list of column names like "col1, col2, col3" from a meta description.
     *
     * @param  columns the column description
     * @return         a String
     */
    public static String createColumnDescription(ColumnsMetaData columns) {
        checkArguments(columns);
        return joinColumns(columns, new ColumnNameIterator()).toString();
    }

    /**
     * Creates a String like "?, ?, ?" with number of question marks equal to the number of columns.
     *
     * @param  columns colum description
     * @return         Something like "?, ?, ?"
     */
    public static String createQuestionMarkPerColumn(ColumnsMetaData columns) {
        checkArguments(columns);
        return joinColumns(columns, new QuestionMarkIterator()).toString();
    }

    /**
     * Creates an AND condition with all columns.
     *
     * @param  columns column description
     * @return         c1 = ? AND c2 = ? AND c3 = ?
     */
    public static String createAndCondition(ColumnsMetaData columns) {
        boolean first = true;
        StringBuilder andCondition = new StringBuilder();
        for (ColumnMetaData columnMetaData : columns) {
            if (!first) {
                andCondition.append("AND ");
            }
            andCondition.append(columnMetaData.getColumnKey().getColumnName()).append(" = ? ");
            first = false;
        }
        return andCondition.toString().trim();
    }

    private static void checkArguments(ColumnsMetaData columns) {
        if (columns == null || columns.size() == 0) {
            throw new IllegalArgumentException("columns is null or empty");
        }
    }

    /**
     * Joins the columns.
     *
     * @param  columns        the columns to join
     * @param  columnIterator column iterator
     * @return                the joined columns
     */
    private static StringBuilder joinColumns(ColumnsMetaData columns,
            ColumnIterator columnIterator) {

        boolean first = true;
        StringBuilder insertSqlCommand = new StringBuilder();
        for (ColumnMetaData column : columns) {
            if (!first) {
                insertSqlCommand.append(", ");
            }

            insertSqlCommand.append(columnIterator.get(column));
            first = false;
        }
        return insertSqlCommand;
    }

    private static interface ColumnIterator {
        public String get(ColumnMetaData column);
    }

    private static class ColumnNameIterator implements ColumnIterator {
        public String get(ColumnMetaData column) {
            return column.getColumnKey().getColumnName();
        }
    }

    private static class QuestionMarkIterator implements ColumnIterator {
        public String get(ColumnMetaData column) {
            if (column.getColumnType().equals(Type.BIT)) {
                // TODO Is this MySQL specific?
                // return "b?";
                return "?";
            } else {
                return "?";
            }
        }
    }

}

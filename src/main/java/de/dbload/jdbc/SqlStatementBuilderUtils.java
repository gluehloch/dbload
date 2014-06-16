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

import de.dbload.meta.ColumnMetaData;
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
     * @param columns the column description
     * @return a String
     */
    public static String createColumnDescription(ColumnsMetaData columns) {
        checkArguments(columns);
        return joinColumns(columns, new GetColumnNameIterator()).toString();
    }

    /**
     * Creates a String like "?, ?, ?" with number of question marks equal to
     * the number of columns.
     *
     * @param columns colum description
     * @return Something like "?, ?, ?"
     */
    public static String createColumnValues(ColumnsMetaData columns) {
        checkArguments(columns);
        return joinColumns(columns, new QuestionMarkIterator()).toString();
    }

    private static void checkArguments(ColumnsMetaData columns) {
        if (columns == null || columns.size() == 0) {
            throw new IllegalArgumentException("columns is null or empty");
        }
    }

    private static StringBuffer joinColumns(ColumnsMetaData columns,
            ColumnIterator columnIterator) {

        boolean first = true;
        StringBuffer insertSqlCommand = new StringBuffer();
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

    private static class GetColumnNameIterator implements ColumnIterator {

        public String get(ColumnMetaData column) {
            return column.getColumnName();
        }

    }

    private static class QuestionMarkIterator implements ColumnIterator {

        public String get(ColumnMetaData column) {
            return "?";
        }

    }

}

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

import java.sql.*;

import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;

/**
 * Jdbc Utilities. Only for internal DBLoad use.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcUtils {

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                // Ignore me
            }
        }
    }

    public static void delete(Connection conn, String tableName) {
        String delete = String.format("DELETE FROM %s", tableName);

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(delete);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static ResultSetMetaData findMetaData(Connection conn, String tableName) {
        String sql = String.format("SELECT * FROM %s WHERE 1 = 0", tableName);

        try (Statement stmt = conn.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(sql)) {
                return resultSet.getMetaData();
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Create the dbload meta data of a {@link ResultSetMetaData}.
     *
     * @param  resultSetMetaData the java sql meta data
     * @return                   A description of the table columns for dbload
     * @throws SQLException      Something is wrong
     */
    public static TableMetaData toTableMetaData(ResultSetMetaData resultSetMetaData) throws SQLException {
        if (resultSetMetaData.getColumnCount() < 1) {
            throw new IllegalArgumentException("Min column count is one.");
        }

        String tableName = resultSetMetaData.getTableName(1);
        for (int i = 2; i <= resultSetMetaData.getColumnCount(); i++) {
            if (!resultSetMetaData.getTableName(i).equals(tableName)) {
                throw new IllegalArgumentException(
                        "All columns must be of table '" + tableName + "'.");
            }
        }

        ColumnsMetaData columnsMetaData = new ColumnsMetaData();
        int numberOfColumns = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            Type columnType = Type.valueOf(resultSetMetaData.getColumnType(i));
            columnsMetaData.column(columnName, columnType);
        }

        return new TableMetaData(tableName, columnsMetaData);
    }

}

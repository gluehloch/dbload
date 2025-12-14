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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import de.dbload.meta.ColumnKey;
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

    /**
     * Die Tabellen Metadaten aus der CSV Datei werden mit den Metadaten aus der JDBC Connection abgeglichen. Die CSV
     * Metadaten sind dabei führend zu betrachten. D.h. werden aus der JDBC Connection für die Tabelle mehr Spalten
     * ermittelt, die nicht in den Metadaten der CSV Datei stehen, dann sind diese zu ignorieren.
     *
     * @param  conn             JDBC Connection
     * @param  csvTableMetaData Die Tabellen Metadaten, wie sie aus der CSV Datei ermittelt wurden.
     * @return                  Entspricht den CSV Metadaten. Angereichert mit Typ-Informationen.
     */
    public static TableMetaData findMetaData(Connection conn, TableMetaData csvTableMetaData) {
        String sql = String.format("SELECT * FROM %s WHERE 1 = 0", csvTableMetaData.getTableName());

        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            return toTableMetaData(csvTableMetaData, metaData);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Create the dbload meta data of a {@link ResultSetMetaData}.
     *
     * @param  csvTableMetaData  Die Metadaten aus der CSV Datei.
     * @param  resultSetMetaData the java sql meta data
     * @return                   A description of the table columns for dbload
     * @throws SQLException      Something is wrong
     */
    private static TableMetaData toTableMetaData(TableMetaData csvTableMetaData, ResultSetMetaData resultSetMetaData)
            throws SQLException {
        if (resultSetMetaData.getColumnCount() < 1) {
            throw new IllegalArgumentException("Min column count is one.");
        }

        String tableName = resultSetMetaData.getTableName(1);
        if (!csvTableMetaData.getTableName().equalsIgnoreCase(tableName)) {
            throw new IllegalStateException("Table names csv vs jdbc are not equal");
        }

        for (int i = 2; i <= resultSetMetaData.getColumnCount(); i++) {
            if (!resultSetMetaData.getTableName(i).equals(tableName)) {
                throw new IllegalArgumentException(String.format("All columns must be of table %s.", tableName));
            }
        }

        ColumnsMetaData columnsMetaData = new ColumnsMetaData();
        int numberOfColumns = resultSetMetaData.getColumnCount();

        for (int i = 1; i <= numberOfColumns; i++) {
            String jdbcColumnName = resultSetMetaData.getColumnName(i);
            Type columnType = Type.valueOf(resultSetMetaData.getColumnType(i));

            ColumnKey columnKey = ColumnKey.of(jdbcColumnName);
            if (csvTableMetaData.getColumn(columnKey).isPresent()) {
                columnsMetaData.column(columnKey, columnType);
            }
        }

        return new TableMetaData(tableName, columnsMetaData);
    }

}

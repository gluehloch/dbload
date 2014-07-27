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

import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.TableMetaData;

/**
 * Jdbc Utilities.
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

    public static ResultSetMetaData findMetaData(Connection conn,
            String tableName) {

        String sql = String.format("SELECT * FROM %s WHERE 1 = 0",tableName);

        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            return resultSet.getMetaData();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * TODO Da muss ich noch mal ran...
     *
     * @param resultSetMetaData
     * @return
     * @throws SQLException
     */
    public static TableMetaData toTableMetaData(
            ResultSetMetaData resultSetMetaData) throws SQLException {

        int numberOfColumns = resultSetMetaData.getColumnCount();
        for (int i = 0; i <= numberOfColumns; i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            int columnTypeAsInt = resultSetMetaData.getColumnType(i);

            Type columnType = null;

            switch (columnTypeAsInt) {
            case java.sql.Types.BIGINT:
                // TODO Das wird jetzt komisch hier. Alles auf meinen eigenen
                // Typ mappen? Hat das einen Vorteil?

                // Zur Zeit kann die BIT Spalte nicht mit einem String Wert
                // in der MySQL Datenbank befuellt werden. Wert zu gross
                // Fehlermeldung.
                break;
            case java.sql.Types.BIT:
                // columnType = Type.NUMBER_BIT;
                break;
            default:
                columnType = Type.DEFAULT;
            }
        }

        return null;
        //return new TableMetaData(_tableName, _columns);
    }

}

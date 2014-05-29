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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static void findMetaData(Connection conn, String tableName) {
        String sql = "SELECT * FROM " + tableName + " WHERE 1 = 0";

        try {
            Statement stmt = conn.createStatement();
            boolean execute = stmt.execute(sql);
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

}

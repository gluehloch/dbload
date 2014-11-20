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

package de.dbload.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Gives a JDBC connection to test.
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public abstract class TransactionalTest {

    protected Connection conn;
    
    public void before() throws SQLException {
        conn = TestConnectionFactory.connectToTestDatabase();
    }

    public void after() throws SQLException {
        SQLException exe = null;
        try {
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
            exe = ex;
        }
        
        conn.close();
        if (exe != null) {
            throw exe;
        }
    }

}

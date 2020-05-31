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

package de.dbload.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import de.dbload.impl.DbloadException;
import de.dbload.jdbc.connector.DatabasePropertyReader;

/**
 * Open and close a JDBC connection for a JUnit test case.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public abstract class TransactionalTest {

    protected Connection conn;

    @Before
    public void openConnection() throws Exception {
        DatabasePropertyReader dpr = new DatabasePropertyReader();
        String url = dpr.getDatabaseUrl();
        if (url == null) {
            throw new DbloadException("Databse URL property not found.");
        }
        conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
    }

    @After
    public void closeConnection() throws SQLException {
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

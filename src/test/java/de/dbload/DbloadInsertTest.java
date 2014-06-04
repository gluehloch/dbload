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

package de.dbload;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import org.junit.Test;

import de.dbload.jdbc.connector.JdbcMySqlConnector;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;

/**
 * A test case for {@link DbloadInsert}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadInsertTest {

    @Test
    public void dbloadInsert() throws SQLException {
        Connection connection = JdbcMySqlConnector.createMySqlConnection(
                "dbload", "dbload", "localhost", "dbload");
        DbloadContext context = new DbloadContext(connection);
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData("id", Type.NUMBER));
        // TODO Missing columns...
        TableMetaData tableMetaData = new TableMetaData("person", columns);
        DbloadInsert dbloadInsert = new DbloadInsert(context, tableMetaData, Locale.GERMANY);
    }

}

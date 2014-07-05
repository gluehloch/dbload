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

package de.dbload.assertion;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.dbload.DataRow;
import de.dbload.DbloadContext;
import de.dbload.jdbc.PreparedStatementBuilder;
import de.dbload.jdbc.SqlSelectStatement;
import de.dbload.meta.TableMetaData;

/**
 * Some assertions...
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class Assertion {

    public static void assertExists(Class<?> clazz) {
        assertEquals(0, 0);
        throw new AssertionError();
    }

    public static boolean assertExists(DbloadContext _context,
            TableMetaData _tableMetaData, List<DataRow> _dataRow)
            throws SQLException {

        SqlSelectStatement sqlSelectStatement = new SqlSelectStatement(
                _tableMetaData);
        PreparedStatement stmt = PreparedStatementBuilder.prepareStatement(
                _context, sqlSelectStatement);

        DbloadAssertionContext assertionContext = new DbloadAssertionContext(
                _context, _tableMetaData, stmt);

        boolean ok = true;
        for (DataRow dataRow : _dataRow) {
            ok = ok && assertionContext.assertExists(dataRow);
        }
        return ok;
    }

    public static boolean assertExists(DbloadContext _context,
            TableMetaData _tableMetaData, DataRow _dataRow) throws SQLException {

        SqlSelectStatement sqlSelectStatement = new SqlSelectStatement(
                _tableMetaData);
        PreparedStatement stmt = PreparedStatementBuilder.prepareStatement(
                _context, sqlSelectStatement);

        DbloadAssertionContext assertionContext = new DbloadAssertionContext(
                _context, _tableMetaData, stmt);

        return assertionContext.assertExists(_dataRow);
    }

}

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

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import de.dbload.DbloadContext;
import de.dbload.csv.reader.ResourceDataReader;
import de.dbload.csv.reader.ResourceReader;
import de.dbload.csv.reader.ResourceReaderCallback;
import de.dbload.impl.DbloadException;
import de.dbload.jdbc.PreparedStatementBuilder;
import de.dbload.jdbc.SqlSelectStatementBuilder;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * Some assertions...
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class Assertion {

    private Assertion() {
        // Do not instantiate me!
    }

    public static void assertExists(final DbloadContext context, Class<?> clazz) {
        try (ResourceDataReader rdr = new ResourceDataReader(clazz)) {

            final TableMetaDataHolder tableMetaDataHolder = new TableMetaDataHolder();

            ResourceReader resourceReader = new ResourceReader();
            resourceReader.start(rdr, new ResourceReaderCallback() {
                @Override
                public void newTableMetaData(TableMetaData _tableMetaData) {
                    tableMetaDataHolder.setValue(_tableMetaData);
                }

                @Override
                public void newDataRow(DataRow dataRow) {
                    try {
                        assertExists(context, tableMetaDataHolder.getValue(), dataRow);
                    } catch (SQLException ex) {
                        throw new DbloadException(ex);
                    }
                }
            });

        } catch (IOException ex) {
            throw new DbloadException(ex);
        }
    }

    private static class TableMetaDataHolder {
        private TableMetaData tableMetaData;

        public void setValue(TableMetaData _tableMetaData) {
            tableMetaData = _tableMetaData;
        }

        public TableMetaData getValue() {
            return tableMetaData;
        }
    }

    public static boolean assertExists(DbloadContext _context,
            TableMetaData _tableMetaData, List<DataRow> _dataRow)
            throws SQLException {

        SqlSelectStatementBuilder sqlSelectStatement = new SqlSelectStatementBuilder(
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

        SqlSelectStatementBuilder sqlSelectStatement = new SqlSelectStatementBuilder(
                _tableMetaData);
        PreparedStatement stmt = PreparedStatementBuilder.prepareStatement(
                _context, sqlSelectStatement);

        DbloadAssertionContext assertionContext = new DbloadAssertionContext(
                _context, _tableMetaData, stmt);

        return assertionContext.assertExists(_dataRow);
    }

}

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

package de.dbload;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import de.dbload.csv.ColumnTypeParser;
import de.dbload.csv.ResourceDataReader;
import de.dbload.csv.ResourceParser;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;

/**
 * Entry point for uploading data to the database.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class Dbload {

    /**
     * Start upload.
     * 
     * @param context
     *            the context for dbload
     * @param clazz
     *            used as classloader root
     * @throws IOException
     *             Unable to read data file
     * @throws SQLException
     *             Unable to write to database
     */
    public static void start(DbloadContext context, Class<?> clazz)
            throws IOException, SQLException {

        try (ResourceDataReader resourceDataReader = new ResourceDataReader(
                clazz)) {

            resourceDataReader.open();
            ResourceParser resourceParser = new ResourceParser();

            String currentTableName = null;
            TableMetaData currentTableMetaData = null;

            String line = null;
            do {
                line = resourceDataReader.readLine();
                ResourceParser.ParserState parserState = resourceParser
                        .parse(line);
                switch (parserState) {
                case COLUMN_DEFINITION:
                    if (currentTableName == null) {
                        throw new IllegalStateException(
                                "Find column description without a table name!");
                    }

                    List<String> currentColumnNames = resourceParser
                            .readColumnNames(line);
                    ColumnsMetaData columnsMetaData = ColumnTypeParser
                            .parseColumnsMetaData(currentColumnNames);
                    currentTableMetaData = new TableMetaData(currentTableName,
                            columnsMetaData);
                    break;
                case COMMENT_OR_EMPTY:
                    break;
                case DATA_DEFINITION:
                    DataRow dataRow = resourceParser.readRow(
                            currentTableMetaData.getColumns().getColumnNames(),
                            line);
                    // TODO Was passiert mit der DataRow? Gleich in die
                    // Datenbank oder erst zwischen lagern fuer das spaetere
                    // Ablegen in der Datenbank?

                    // TODO Das DbloadInsert koennte nach jeder
                    // Tabellendefinition neu angelegt werden.
                    DbloadInsert dbloadInsert = new DbloadInsert(context,
                            currentTableMetaData, Locale.GERMANY);
                    dbloadInsert.insert(dataRow);
                    break;
                case TABLE_DEFINITION:
                    currentTableName = resourceParser.readTableDefinition(line);
                    break;
                default:
                    break;

                }

            } while (!resourceDataReader.endOfFile());
            context.getConnection().commit();
        }
    }

}

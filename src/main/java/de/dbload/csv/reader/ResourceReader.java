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

package de.dbload.csv.reader;

import java.io.IOException;
import java.util.List;

import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * Some class to do something.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceReader {

    /**
     * Start file parsing.
     *
     * @param resourceDataReader
     *            The resource to read from.
     * @param resourceReaderCallback
     *            Send this callback handler the current parsing state.
     * @throws IOException
     *             Ups
     */
    public void start(ResourceDataReader resourceDataReader,
            ResourceReaderCallback resourceReaderCallback) throws IOException {

        resourceDataReader.open();
        ResourceParser resourceParser = new ResourceParser();

        String currentTableName = null;
        TableMetaData currentTableMetaData = null;
        int lineNo = 1;
        String line = null;

        do {
            line = resourceDataReader.readLine();
            switch (resourceParser.parse(lineNo, line)) {
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

                resourceReaderCallback.newTableMetaData(currentTableMetaData);

                break;
            case COMMENT_OR_EMPTY:
                break;
            case DATA_DEFINITION:
                DataRow dataRow = resourceParser.readRow(currentTableMetaData
                        .getColumns().getColumnNames(), lineNo, line);

                resourceReaderCallback.newDataRow(dataRow);

                break;
            case TABLE_DEFINITION:
                currentTableName = resourceParser.readTableDefinition(line);
                break;
            default:
                break;
            }
            
            lineNo++;
        } while (!resourceDataReader.endOfFile());
    }

}

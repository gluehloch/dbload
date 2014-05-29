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

import java.io.IOException;
import java.sql.Connection;

import de.dbload.csv.ResourceParser;
import de.dbload.csv.ResourceParser.ParserState;
import de.dbload.meta.TableMetaData;

public class ImportData {

    /**
     * Load data to the database.
     * 
     * @param conn
     *            a database connection
     * @param testcase
     *            the test case class
     * @throws IOException
     *             ups
     */
    public void start(Connection conn, Class<?> testcase) throws IOException {
        start(conn, testcase.getSimpleName() + ".dat", testcase);
    }

    /**
     * Load data to the database.
     * 
     * @param conn
     *            a database connection
     * @param resourceName
     *            the name of a resource
     * @param resourceClass
     *            the resource root class
     * @throws IOException
     *             ups
     */
    public void start(Connection conn, String resourceName,
            Class<?> resourceClass) throws IOException {

        ResourceFileInsert resourceFileInsert = new ResourceFileInsert(
                new ResourceNativeSqlInsert(jdbcTemplate), resourceName);
        ResourceDataReader rdr = new ResourceDataReader(resourceName,
                resourceClass);

        try {
            rdr.open();
            ResourceParser resourceParser = new ResourceParser();

            do {
                String line = rdr.readLine();
                ParserState parserState = resourceParser.parse(line);

                switch (parserState) {
                case DATA_DEFINITION:
                    resourceFileInsert.insert(resourceParser.getData());
                    break;
                case COLUMN_DEFINITION:
                    TableMetaData tableMetaData = new TableMetaData(
                            resourceParser.getTableName(),
                            resourceParser.getColumns());
                    resourceFileInsert.newInsert(tableMetaData);
                    break;
                case TABLE_DEFINITION:
                    break;
                case COMMENT_OR_EMPTY:
                    break;
                default:
                    break;
                }
            } while (!rdr.endOfFile());
        } finally {
            resourceFileInsert.close();
            rdr.close();
        }
    }

}

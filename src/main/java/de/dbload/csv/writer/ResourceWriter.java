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

package de.dbload.csv.writer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import de.dbload.csv.reader.ResourceReaderCallback;
import de.dbload.impl.DbloadException;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * Write some SQL results to a <code>.dat</code> file.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceWriter {

    private final Path path;
    private final Charset utf8;
    private final String linefeet;

    public ResourceWriter(Path _path) {
        path = _path;
        utf8 = Charset.forName("UTF-8");
        linefeet = System.getProperty("line.separator").toString();
    }

    public void start(Connection conn, String sqlSelect, boolean append)
            throws SQLException {

        if (!append) {
            File file = path.toFile();
            if (file.exists()) {
                file.delete();
            }
        }

        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(
                path, append ? StandardOpenOption.APPEND
                        : StandardOpenOption.CREATE));
                Writer writer = new OutputStreamWriter(out, utf8)) {
            
            PrintWriter pw = new PrintWriter(writer, true);
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet resultSet = stmt.executeQuery(sqlSelect)) {

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    
                    pw.print("### TAB ");
                    pw.print(metaData.getTableName(1));
                    pw.println(linefeet);
                    pw.print("### ");

                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        pw.print(metaData.getColumnName(i));
                        if (i < metaData.getColumnCount()) {
                            pw.print(" | ");
                        }
                    }
                    pw.println();

                    while (resultSet.next()) {
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            pw.print(resultSet.getString(i));
                            if (i < metaData.getColumnCount()) {
                                pw.print(" | ");
                            }
                        }
                        pw.println();
                    }
                }
            }

        } catch (IOException ex) {
            throw new DbloadException(ex);
        }
    }

    public class Callback implements ResourceReaderCallback {

        private final Writer writer;

        public Callback(Writer _writer) {
            writer = _writer;
        }

        /**
         * Inspected a new {@link TableMetaData} description.
         *
         * @param tableMetaData
         *            table meta data description
         */
        public void newTableMetaData(TableMetaData tableMetaData) {
            ColumnsMetaData columnsMetaData = tableMetaData.getColumns();
            List<String> columnNames = columnsMetaData.getColumnNames();

            try {
                writer.write("### ");
                writer.write(tableMetaData.getTableName());
            } catch (IOException ex) {

            }
        }

        /**
         * Inspected a new {@link DataRow} for the last finded table.
         *
         * @param dataRow
         *            a new data row
         */
        public void newDataRow(DataRow dataRow) {

        }

    }

}

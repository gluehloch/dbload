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

package de.dbload.csv;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

public class ResourceWriter {

    private Writer writer;

    public void start() {
        OutputStream newOutputStream = Files.newOutputStream(null, null);

        Path path = new Path(); Paths.get(null, null)
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(
                StandardOpenOption.CREATE))) {

            out.write(data, 0, data.length);
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    private List<String> toList(String... strings) {
        List<String> list = new ArrayList<>();
        for (String str : strings) {
            list.add(str);
        }
        return list;
    }

    public class Callback implements ResourceReaderCallback {

        /**
         * Inspected a new {@link TableMetaData} description.
         *
         * @param tableMetaData
         *            table meta data description
         */
        public void newTableMetaData(TableMetaData tableMetaData) {
            try {
                ResourceWriter.this.writer.write("### ");
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

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

package de.dbload.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.dbload.Dbload;
import de.dbload.meta.ColumnKey;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * Test for class {@link DbloadFileInsert}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class DbloadFileInsertTest {

    private Path tempDir;

    @BeforeEach
    public void before() throws IOException {
        tempDir = Files.createTempDirectory("dbload");
    }

    @Test
    void testResourceFileInsert() throws Exception {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData(ColumnKey.of("id"), Type.LONG));
        columns.addColumn(new ColumnMetaData(ColumnKey.of("name"), Type.VARCHAR));
        columns.addColumn(new ColumnMetaData(ColumnKey.of("vorname"), Type.VARCHAR));
        columns.addColumn(new ColumnMetaData(ColumnKey.of("age"), Type.INTEGER));
        columns.addColumn(new ColumnMetaData(ColumnKey.of("sex"), Type.INTEGER));
        columns.addColumn(new ColumnMetaData(ColumnKey.of("birthday"), Type.DATE));
        TableMetaData tableMetaData = new TableMetaData("person", columns);

        DataRow data = new DataRow();
        data.put(ColumnKey.of("id"), "0");
        data.put(ColumnKey.of("name"), "Winkler");
        data.put(ColumnKey.of("vorname"), "Andre");
        data.put(ColumnKey.of("age"), "43");
        data.put(ColumnKey.of("sex"), "0");
        data.put(ColumnKey.of("birthday"), "1971-03-24 06:41:11");

        File directory = tempDir.toFile();
        try (DbloadFileInsert rfi = new DbloadFileInsert(directory, Dbload.class)) {
            rfi.newTableMetaData(tableMetaData);
            rfi.addBatch(data);
        }
    }

}

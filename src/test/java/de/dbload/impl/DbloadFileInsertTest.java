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

package de.dbload.impl;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.dbload.DataRow;
import de.dbload.Dbload;
import de.dbload.impl.DbloadFileInsert;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;

/**
 * Test for class {@link DbloadFileInsert}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadFileInsertTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testResourceFileInsert() throws Exception {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData("id", Type.NUMBER_INTEGER));
        columns.addColumn(new ColumnMetaData("name", Type.STRING));
        columns.addColumn(new ColumnMetaData("vorname", Type.STRING));
        columns.addColumn(new ColumnMetaData("age", Type.NUMBER_INTEGER));
        columns.addColumn(new ColumnMetaData("sex", Type.NUMBER_INTEGER));
        columns.addColumn(new ColumnMetaData("birthday", Type.DATE));
        TableMetaData tableMetaData = new TableMetaData("person", columns);

        DataRow data = new DataRow();
        data.put("id", "0");
        data.put("name", "Winkler");
        data.put("vorname", "Andre");
        data.put("age", "43");
        data.put("sex", "0");
        data.put("birthday", "1971-03-24 06:41:11");

        File directory = folder.newFolder();
        try (DbloadFileInsert rfi = new DbloadFileInsert(directory,
                Dbload.class)) {

            rfi.newInsert(tableMetaData);
            rfi.insert(data);
        }
    }

}

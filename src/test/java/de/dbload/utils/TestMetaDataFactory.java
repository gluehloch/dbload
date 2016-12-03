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

package de.dbload.utils;

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;

/**
 * Creates some meta data for tables of the test database.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class TestMetaDataFactory {

    /**
     * Create the meta data for table <code>PERSON</code>.
     *
     * @return the table meta data
     */
    public static TableMetaData createPersonMetaData() {
        ColumnsMetaData columns = new ColumnsMetaData();
        columns.addColumn(new ColumnMetaData("id", Type.LONG));
        columns.addColumn(new ColumnMetaData("name", Type.VARCHAR));
        columns.addColumn(new ColumnMetaData("vorname", Type.VARCHAR));
        columns.addColumn(new ColumnMetaData("age", Type.INTEGER));
        columns.addColumn(new ColumnMetaData("sex", Type.INTEGER));
        columns.addColumn(new ColumnMetaData("birthday", Type.DATE_TIME));
        TableMetaData tableMetaData = new TableMetaData("person", columns);
        return tableMetaData;
    }

}

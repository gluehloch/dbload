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

package de.dbload.meta;


/**
 * Holds the database table meta data.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class TableMetaData {

    private final String tableName;
    private final ColumnsMetaData columnsMetaData;

    /**
     * Constructor
     * 
     * @param _tableName the table name
     * @param _columns the columns of the table
     */
    public TableMetaData(String _tableName, ColumnsMetaData _columns) {
        tableName = _tableName;
        columnsMetaData = _columns;
    }

    public String getTableName() {
        return tableName;
    }

    public ColumnsMetaData getColumns() {
        return columnsMetaData;
    }

}

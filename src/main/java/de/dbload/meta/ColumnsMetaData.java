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

package de.dbload.meta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.dbload.meta.ColumnMetaData.Type;

/**
 * Holds the meta data of all columns of a table.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ColumnsMetaData implements Iterable<ColumnMetaData> {

    private List<ColumnMetaData> columns = new ArrayList<>();

    public ColumnsMetaData() {
    }

    public Iterator<ColumnMetaData> iterator() {
        return columns.iterator();
    }

    public ColumnMetaData get(int index) {
        return columns.get(index);
    }

    public int size() {
        return columns.size();
    }

    public void addColumn(ColumnMetaData column) {
        columns.add(column);
    }

    public ColumnsMetaData column(String columnName, Type type) {
        addColumn(new ColumnMetaData(columnName, type));
        return this;
    }

    /**
     * Collects all column names and returns them as an ArrayList.
     *
     * @return a list of column names
     */
    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<String>();
        for (ColumnMetaData column : columns) {
            columnNames.add(column.getColumnName());
        }
        return columnNames;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("ColumnsMetaData [columns=");
        for (int i = 0; i < columns.size(); i++) {
            buf.append(columns.get(i));
            if (i < columns.size() - 1) {
                buf.append(", ");
            }
        }
        buf.append("]");

        return buf.toString();
    }

}

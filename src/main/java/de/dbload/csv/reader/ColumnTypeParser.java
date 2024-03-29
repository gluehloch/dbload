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

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.dbload.meta.ColumnKey;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;

/**
 * Find the type of a column.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ColumnTypeParser {

    /**
     * Find name and type of a column description.
     *  
     * @param  columnKeys The names and types some columns.
     * @return            the column meta data
     */
    public static ColumnsMetaData parseColumnsMetaData(List<ColumnKey> columnKeys) {
        ColumnsMetaData columnsMetaData = new ColumnsMetaData();
        for (ColumnKey columnNameAndType : columnKeys) {
            columnsMetaData.addColumn(parseColumnMetaData(columnNameAndType));
        }
        return columnsMetaData;
    }

    /**
     * Creates column meta data from a column description.
     *
     * @param  columnNameAndType a column description with name and type
     * @return                   the column meta data
     */
    public static ColumnMetaData parseColumnMetaData(ColumnKey columnNameAndType) {
        Type columnType = ColumnTypeParser.findType(columnNameAndType);
        String normalizedColumnName = StringUtils.substringBefore(
                columnNameAndType.getColumnName(), "(").trim();
        return new ColumnMetaData(ColumnKey.of(normalizedColumnName), columnType);
    }

    /**
     * Find the column type.
     *
     * @param  columnDescription the column description
     * @return                   the column type
     */
    protected static ColumnMetaData.Type findType(ColumnKey columnDescription) {
        if (containsDate(columnDescription)) {
            return Type.DATE_TIME;
        } else if (containsBit(columnDescription)) {
            return Type.BIT;
        } else {
            // Is this the default?
            return Type.VARCHAR;
        }
    }

    protected static boolean containsDate(ColumnKey columnKey) {
        return (StringUtils.containsIgnoreCase(columnKey.getColumnName(), "(date)"));
    }

    protected static boolean containsBit(ColumnKey columnKey) {
        return (StringUtils.containsIgnoreCase(columnKey.getColumnName(), "(bit)"));
    }

}

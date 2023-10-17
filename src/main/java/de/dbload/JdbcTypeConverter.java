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

package de.dbload;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.dbload.meta.ColumnMetaData;

/**
 * Type converter utility. Different databases use different converters.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public interface JdbcTypeConverter {

    /**
     * Convert a String value to the associated column type.
     *
     * @param columnMetaData meta data of the database column
     * @param value          the String value to convert
     * @return the converted value (String, Number, Date)
     */
    Object toSqlValue(ColumnMetaData columnMetaData, String value);

    /**
     * Calls the appropriate <code>PreparedStatement#setXxx</code> typed setter.
     *
     * @param stmt           The prepared statement
     * @param index          The index of the parameter. Something like column index.
     * @param columnMetaData The meta data
     * @param value          The value to set. Already converted to the expected type.
     * @throws SQLException Something wrong here.
     */
    void setSqlVariable(PreparedStatement stmt, int index, ColumnMetaData columnMetaData, Object value)
            throws SQLException;

}
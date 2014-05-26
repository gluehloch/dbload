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

package de.dbload.jdbc;

import java.math.BigDecimal;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import de.dbload.meta.ColumnMetaData;

/**
 * Convert a Java type to the associated JDBC type.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcTypeConverter {

    public Object convert(ColumnMetaData columnMetaData, String value) {
	Object returnValue = null;
	switch (columnMetaData.getColumnType()) {
	case STRING:
	    if (value != null) {
		returnValue = value;
	    }
	    break;
	case NUMBER:
	    if (value != null) {
		BigDecimal number = BigDecimal.
	    }
	default:
	    stmt.setObject(index, value);
	}
    }

}

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

package de.dbload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.ColumnsMetaData;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;

/**
 * Execute an INSERT statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class ResourceSqlInsert implements ResourceInsert {

	private TableMetaData tableMetaData;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newInsert(TableMetaData tableMetaData) {
		this.tableMetaData = tableMetaData;

		simpleJdbcInsert.withTableName(tableMetaData.getTableName());

		ColumnsMetaData columns = tableMetaData.getColumns();
		String[] strings = new String[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			strings[i] = columns.get(i).getColumnName();
		}

		simpleJdbcInsert.usingColumns(strings);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insert(List<String> data) {
		ColumnsMetaData columns = tableMetaData.getColumns();
		Map<String, Object> params = new HashMap<>();

		for (int i = 0; i < columns.size(); i++) {
			ColumnMetaData column = columns.get(i);
			Object insertme;
			if (column.getColumnType() == Type.DATE) {
				DateTimeFormatter format = DateTimeFormat.forPattern(DateTimeUtils.DATE_FORMAT);
				String value = data.get(i);
				DateTime dateTime = null;

				if (StringUtils.isBlank(value)) {
					insertme = null;
				} else {
					try {
						dateTime = format.parseDateTime(value);
					} catch (IllegalArgumentException ex) {
						throw new IllegalArgumentException(
								"Invalid format for table ->"
										+ tableMetaData.getTableName()
										+ "<- at column index ->" + i + "<-.",
								ex);
					}
					// insertme = dateTime.toDate();
					// insertme = new
					// java.sql.Timestamp(dateTime.toDate().getTime());
					insertme = new SqlParameterValue(java.sql.Types.TIMESTAMP,
							new java.sql.Date(dateTime.toDate().getTime()));
				}
			} else {
				insertme = data.get(i);
			}

			// System.out.println(insertme);
			params.put(column.getColumnName(), insertme);
		}

		simpleJdbcInsert.execute(params);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		// Do nothing...
	}

}

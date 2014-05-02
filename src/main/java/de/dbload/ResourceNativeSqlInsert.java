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

import java.util.List;

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.TableMetaData;

class ResourceNativeSqlInsert implements ResourceInsert {

	private final JdbcTemplate jdbcTemplate;

	private TableMetaData tableMetaData;

	/**
	 * Konstruktor
	 * 
	 * @param jdbcTemplate
	 *            JdbcTemplate fuer ein einfaches Insert
	 */
	public ResourceNativeSqlInsert(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newInsert(TableMetaData tableMetaData) {
		this.tableMetaData = tableMetaData;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insert(List<String> data) {
		StringBuffer insertSqlCommand = new StringBuffer("I")
				.append("NSERT INTO ");
		insertSqlCommand.append(tableMetaData.getTableName());
		insertSqlCommand.append('(');

		List<ColumnMetaData> columns = tableMetaData.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			ColumnMetaData column = columns.get(i);
			insertSqlCommand.append(column.getColumnName());
			if (i < columns.size() - 1) {
				insertSqlCommand.append(',');
			}
		}
		insertSqlCommand.append(")\r\n VALUES (");
		for (int i = 0; i < columns.size(); i++) {
			ColumnMetaData column = columns.get(i);
			Object insertme;
			if (column.getColumnType() == Type.DATE) {
				insertme = "to_date('" + data.get(i) + "', '"
						+ ORACLE_DATE_FORMAT + "')";
			} else {
				// int[] types = jdbcInsert.getInsertTypes();
				// insertme = InOutUtils.toString(types[i], data.get(i));
				String val = data.get(i);
				if (org.apache.commons.lang.StringUtils.isBlank(val)) {
					insertme = "NULL";
				} else {
					insertme = "'" + val + "'";
				}
			}

			insertSqlCommand.append(insertme);
			if (i < columns.size() - 1) {
				insertSqlCommand.append(',');
			}
		}
		insertSqlCommand.append(')');

		jdbcTemplate.execute(insertSqlCommand.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		// Do nothing...
	}

}

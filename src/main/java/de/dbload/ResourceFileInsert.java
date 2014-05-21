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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;

/**
 * Writes the insert sql script to the file system.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceFileInsert implements ResourceInsert {

    private TableMetaData tableMetaData;
    private File sqlOutputFile;
    private FileOutputStream fos;
    private Writer writer;

    /**
     * Constructor
     * 
     * @param resourceInsertDeko
     *            resource to decorate
     * @param testcase
     *            the name of the testcase
     */
    public ResourceFileInsert(File directory,
	    ResourceInsert resourceInsertDeko, String testcase) {

	sqlOutputFile = new File(directory, testcase + ".sql");
	if (sqlOutputFile.exists()) {
	    sqlOutputFile.delete();
	}

	try {
	    fos = new FileOutputStream(sqlOutputFile, true);
	    writer = new OutputStreamWriter(fos, "UTF-8");
	} catch (IOException ex) {
	    throw new IllegalStateException(ex);
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void newInsert(TableMetaData tableMetaData) {
	this.tableMetaData = tableMetaData;

	try {
	    writer.write("\r\n");
	} catch (IOException ex) {
	    throw new IllegalStateException(ex);
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(List<String> data) {
	StringBuffer insertSqlCommand = new StringBuffer("INSERT INTO ");
	insertSqlCommand.append(tableMetaData.getTableName());
	insertSqlCommand.append('(');

	// List<ColumnMetaData> columns = tableMetaData.getColumns();
	boolean first = true;
	for (ColumnMetaData column : tableMetaData.getColumns()) {
	    if (!first) {
		insertSqlCommand.append(',');
	    }
	    insertSqlCommand.append(column.getColumnName());
	    first = false;
	}

	insertSqlCommand.append(")\r\n VALUES (");
	first = true;
	for (ColumnMetaData column : tableMetaData.getColumns()) {
	    if (!first) {
		insertSqlCommand.append(',');
	    }
	    Object insertme;
	    if (column.getColumnType() == Type.DATE) {
		insertme = "to_date('" + data.get(i) + "', '"
			+ DateTimeUtils.ORACLE_DATE_FORMAT + "')";
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

	}
	insertSqlCommand.append(");");

	try {
	    writer.write(insertSqlCommand.toString());
	    writer.write("\r\n");
	} catch (IOException ex) {
	    throw new IllegalStateException(ex);
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
	try {
	    if (writer != null) {
		writer.close();
	    }

	    if (fos != null) {
		fos.close();
	    }
	} catch (IOException ex) {
	    // Ok. Something is wrong...
	}
    }

}

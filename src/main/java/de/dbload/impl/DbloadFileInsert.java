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

package de.dbload.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;

/**
 * Writes the insert sql script to the file system.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadFileInsert implements DbloadSqlStatement {

    private TableMetaData tableMetaData;
    private File sqlOutputFile;
    private FileOutputStream fos;
    private Writer writer;

    /**
     * Constructor
     *
     * @param directory export directory
     * @param clazz     the classpath resource
     */
    public DbloadFileInsert(File directory, Class<?> clazz) {
        this(directory, clazz.getName());
    }

    /**
     * Constructor
     *
     * @param directory export directory
     * @param testcase  the name of the testcase
     */
    public DbloadFileInsert(File directory, String testcase) {
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
     * Set the current table name.
     *
     * @param _tableMetaData table name.
     */
    @Override
    public void newTableMetaData(TableMetaData _tableMetaData) {
        this.tableMetaData = _tableMetaData;

        try {
            writer.write("\r\n");
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Insert a new row of data.
     *
     * @param data the data to insert
     */
    @Override
    public void addBatch(DataRow data) {
        StringBuilder insertSqlCommand = new StringBuilder("INSERT INTO ");
        insertSqlCommand.append(tableMetaData.getTableName());
        insertSqlCommand.append('(');

        // List<ColumnMetaData> columns = tableMetaData.getColumns();
        boolean first = true;
        for (ColumnMetaData column : tableMetaData.getColumns()) {
            if (!first) {
                insertSqlCommand.append(", ");
            }
            insertSqlCommand.append(column.getColumnKey().getColumnName());
            first = false;
        }

        insertSqlCommand.append(")\r\n VALUES (");
        first = true;
        for (ColumnMetaData column : tableMetaData.getColumns()) {
            if (!first) {
                insertSqlCommand.append(", ");
            }
            Object insertme;
            if (column.getColumnType() == Type.DATE) {
                insertme = "to_date('" + data.get(column.getColumnKey())
                        + "', '" + DateTimeUtils.ORACLE_DATE_FORMAT + "')";
            } else {
                // int[] types = jdbcInsert.getInsertTypes();
                // insertme = InOutUtils.toString(types[i], data.get(i));
                String val = data.get(column.getColumnKey());
                if (StringUtils.isBlank(val)) {
                    insertme = "NULL";
                } else {
                    insertme = "'" + val + "'";
                }
            }
            first = false;
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

    @Override
    public void execute() throws SQLException {
        // No-op: File-based insert doesn't require batch execution"
    }

    /**
     * Close this resource.
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

    @Override
    public String getTableName() {
        return tableMetaData.getTableName();
    }

}

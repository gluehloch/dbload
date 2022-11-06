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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dbload.DbloadContext;
import de.dbload.csv.reader.ResourceDataReader;
import de.dbload.csv.reader.ResourceReader;
import de.dbload.csv.reader.ResourceReaderCallback;
import de.dbload.csv.writer.ResourceWriter;
import de.dbload.jdbc.JdbcUtils;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * The worker!
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DefaultDbloadImpl {

    private static final Logger LOG = LoggerFactory
            .getLogger(DefaultDbloadImpl.class);

    /**
     * Start upload.
     *
     * @param  context         the context for dbload
     * @param  readFromFile    read the data from this file
     * @throws DbloadException Some problems with files or datasources
     */
    public void readFromFile(DbloadContext context, File readFromFile) {
        try {
            InputStream is = new FileInputStream(readFromFile);
            startReading(is, context);
        } catch (FileNotFoundException ex) {
            LOG.error("Dbload throws an error.", ex);
            throw new DbloadException(ex);
        }
    }

    /**
     * Start upload.
     *
     * @param context  the context for dbload
     * @param clazz    used as classloader for the resource
     * @param resource the resource to load from the classpath
     */
    public void readFromClasspathResource(DbloadContext context, Class<?> clazz, String resource) {
        InputStream is = clazz.getResourceAsStream(resource);
        startReading(is, context);
    }

    /**
     * Start upload.
     *
     * @param  context         the context for dbload
     * @param  clazz           used as classloader root for the data file
     * @throws DbloadException Some problems with files or datasources
     */
    public void readFromClasspathResource(DbloadContext context, Class<?> clazz) {
        final String classpathResourceName = clazz.getSimpleName() + ".dat";

        if (LOG.isDebugEnabled()) {
            LOG.debug("Loading dat file: classpath=[{}], resource=[{}].", clazz.getName(), classpathResourceName);
        }

        InputStream is = clazz.getResourceAsStream(classpathResourceName);

        if (is == null) {
            LOG.error("There is no classpath resource for {}.", classpathResourceName);
            throw new IllegalArgumentException(classpathResourceName);
        }

        startReading(is, context);
    }

    private void startReading(final InputStream is, final DbloadContext context) {
        try (ResourceDataReader rdr = new ResourceDataReader(is)) {

            final DbloadSqlInsert dbloadSqlInsert = new DbloadSqlInsert(context);

            ResourceReader resourceReader = new ResourceReader();
            resourceReader.start(rdr, new ResourceReaderCallback() {
                @Override
                public void newTableMetaData(TableMetaData tableMetaData) {
                    try {
                        TableMetaData metaData = JdbcUtils.findMetaData(context.getConnection(), tableMetaData);

                        //
                        // TODO Less column data then meta data???
                        // tableMetaData.
                        //

                        dbloadSqlInsert.newTableMetaData(metaData);
                    } catch (SQLException ex) {
                        throw new DbloadException(ex);
                    }
                }

                @Override
                public void newDataRow(DataRow dataRow) {
                    try {
                        dbloadSqlInsert.execute(dataRow);
                    } catch (SQLException ex) {
                        String error = "Unable to execute INSERT ["
                                + dbloadSqlInsert.toString()
                                + "] statement with params [" + dataRow + "]";
                        LOG.error(error);
                        throw new DbloadException(error, ex);
                    }
                }
            });

            // Autoclose does not work here. But on exception, there is no
            // close!
            dbloadSqlInsert.close();

        } catch (IOException ex) {
            LOG.error("Dbload throws an error.", ex);
            throw new DbloadException(ex);
        }
    }

    /**
     * Export all tables to a file.
     *
     * @param context     the database JDBC connection
     * @param writeToFile the file to write to
     * @param tableNames  the database tables to export
     */
    public void writeToFile(DbloadContext context, File writeToFile, String[] tableNames) {
        Objects.requireNonNull(tableNames);
        List<String> list = new ArrayList<String>();
        for (String tableName : tableNames) {
            list.add(tableName);
        }

        writeToFile(context, writeToFile, list);
    }

    /**
     * Export all tables to a file.
     *
     * @param context     the database JDBC connection
     * @param writeToFile the file to write to
     * @param tableNames  the database tables to export
     */
    public void writeToFile(DbloadContext context, File writeToFile,
            List<String> tableNames) {

        ResourceWriter rw = new ResourceWriter(writeToFile);
        try {
            for (String tableName : tableNames) {
                String select = String.format("SELECT * FROM %s", tableName);
                rw.start(context.getConnection(), select, true);
            }
        } catch (SQLException ex) {
            LOG.error("Unable to write export file!", ex);
            throw new DbloadException(ex);
        }
    }

}

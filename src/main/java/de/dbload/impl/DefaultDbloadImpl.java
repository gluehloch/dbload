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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dbload.DbloadContext;
import de.dbload.csv.reader.ResourceDataReader;
import de.dbload.csv.reader.ResourceReader;
import de.dbload.csv.reader.ResourceReaderCallback;
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
     * @param context
     *            the context for dbload
     * @param readFromFile
     *            read the data from this file
     * @throws DbloadException
     *             Some problems with files or datasources
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
     * @param context
     *            the context for dbload
     * @param clazz
     *            used as classloader root for the data file
     * @throws DbloadException
     *             Some problems with files or datasources
     */
    public void readFromClasspathResource(DbloadContext context, Class<?> clazz) {
        InputStream is = clazz.getResourceAsStream(clazz.getSimpleName()
                + ".dat");
        startReading(is, context);
    }

    private void startReading(InputStream is, DbloadContext context) {
        try (ResourceDataReader rdr = new ResourceDataReader(is);
                DbloadSqlInsert dbloadSqlInsert = new DbloadSqlInsert(context);) {

            ResourceReader resourceReader = new ResourceReader();
            resourceReader.start(rdr, new ResourceReaderCallback() {
                @Override
                public void newTableMetaData(TableMetaData tableMetaData) {
                    try {
                        dbloadSqlInsert.newTableMetaData(tableMetaData);
                    } catch (SQLException ex) {
                        throw new DbloadException(ex);
                    }
                }

                @Override
                public void newDataRow(DataRow dataRow) {
                    try {
                        dbloadSqlInsert.execute(dataRow);
                    } catch (SQLException ex) {
                        throw new DbloadException(ex);
                    }
                }
            });

        } catch (IOException ex) {
            LOG.error("Dbload throws an error.", ex);
            throw new DbloadException(ex);
        }
    }

}

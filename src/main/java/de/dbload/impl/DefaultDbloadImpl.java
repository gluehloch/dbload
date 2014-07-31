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

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dbload.DbloadContext;
import de.dbload.csv.ResourceDataReader;
import de.dbload.csv.ResourceReader;
import de.dbload.csv.ResourceReaderCallback;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * The worker!
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DefaultDbloadImpl {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDbloadImpl.class);

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
    public void start(DbloadContext context, Class<?> clazz) {
        try (ResourceDataReader rdr = new ResourceDataReader(clazz);
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
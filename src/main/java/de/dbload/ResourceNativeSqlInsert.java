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

import java.sql.SQLException;
import java.util.Locale;

import de.dbload.meta.TableMetaData;

/**
 * TODO
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceNativeSqlInsert implements ResourceInsert {

    private final DbloadContext dbloadContext;
    private final Locale locale;

    private DbloadInsert dbloadInsert;

    public ResourceNativeSqlInsert(DbloadContext _context,
            TableMetaData _tableMetaData, Locale _locale) throws SQLException {

        dbloadContext = _context;
        locale = _locale;

        dbloadInsert = new DbloadInsert(dbloadContext, _tableMetaData, locale);
    }

    /**
     * {@inheritDoc}
     *
     * @throws SQLException
     */
    @Override
    public void newInsert(TableMetaData tableMetaData) throws SQLException {
        dbloadInsert.close();
        dbloadInsert = new DbloadInsert(dbloadContext, tableMetaData, locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(DataRow data) throws SQLException {
        dbloadInsert.insert(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        dbloadInsert.close();
    }

}

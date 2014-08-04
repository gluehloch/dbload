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

package de.dbload.csv.reader;

import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;

/**
 * Gives informations about the current parsing state.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public interface ResourceReaderCallback {

    /**
     * Inspected a new {@link TableMetaData} description.
     *
     * @param tableMetaData
     *            table meta data description
     */
    public void newTableMetaData(TableMetaData tableMetaData);

    /**
     * Inspected a new {@link DataRow} for the last finded table.
     *
     * @param dataRow
     *            a new data row
     */
    public void newDataRow(DataRow dataRow);

}

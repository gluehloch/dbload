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

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

import de.dbload.meta.TableMetaData;

/**
 * The interface for executing insert to a database.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
interface ResourceInsert extends Closeable {

	/**
	 * Set the current table name.
	 * 
	 * @param tableMetaData table name.
	 */
	void newInsert(TableMetaData tableMetaData);

	/**
	 * Insert a new row of data.
	 * 
	 * @param data the data to insert
	 */
	void insert(List<String> data) throws SQLException;

	/**
	 * Close this resource.
	 */
	void close();

}

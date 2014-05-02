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

import de.dbload.meta.TableMetaData;

class ResourceInsertDeko implements ResourceInsert {

	private final ResourceInsert resourceInsertDeko;

	/**
	 * Konstruktor
	 * 
	 * @param resourceInsertDeko
	 *            ...
	 */
	public ResourceInsertDeko(ResourceInsert resourceInsertDeko) {
		this.resourceInsertDeko = resourceInsertDeko;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newInsert(TableMetaData tableMetaData) {
		resourceInsertDeko.newInsert(tableMetaData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insert(List<String> data) {
		resourceInsertDeko.insert(data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		resourceInsertDeko.close();
	}

}

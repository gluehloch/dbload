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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.dbload.meta.ColumnMetaData;

/**
 * Test fuer {@link ResourceParser}.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceParserTest {

	/**
	 * Test {@link ResourceParser#readTableDefinition(String)}
	 */
	@Test
	public void testResourceParserTableName() {
		ResourceParser resourceParser = new ResourceParser();
		String readTableDefinition = resourceParser
				.readTableDefinition("### TAB table_name");
		assertEquals("table_name", readTableDefinition);
	}

	/**
	 * Test {@link ResourceParser#readColumns(String)}
	 */
	@Test
	public void testDataLoderParseColumns() {
		ResourceParser resourceParser = new ResourceParser();
		List<ColumnMetaData> readColumns = resourceParser
				.readColumns("### col1 | col2(date) | col3");

		assertEquals("col1", readColumns.get(0).getColumnName());
		assertEquals("col2", readColumns.get(1).getColumnName());
		assertEquals(ColumnMetaData.Type.DATE, readColumns.get(1)
				.getColumnType());
		assertEquals("col3", readColumns.get(2).getColumnName());
	}

	/**
	 * Test {@link ResourceParser#readData(String)}
	 */
	@Test
	public void testResourceParserDataRow() {
		ResourceParser resourceParser = new ResourceParser();
		List<String> data = null;
		data = resourceParser.readData("dat1 | dat2|dat3  | dat4 | | ");

		assertEquals("dat1", data.get(0));
		assertEquals("dat2", data.get(1));
		assertEquals("dat3", data.get(2));
		assertEquals("dat4", data.get(3));
		assertEquals("", data.get(4));
		assertEquals("", data.get(5));

		data = resourceParser.readData("dat1 | dat2|dat3  | dat4 || ");

		assertEquals("dat1", data.get(0));
		assertEquals("dat2", data.get(1));
		assertEquals("dat3", data.get(2));
		assertEquals("dat4", data.get(3));
		assertEquals("", data.get(4));
		assertEquals("", data.get(5));

		data = resourceParser.readData("dat1 | dat2|dat3  | dat4 ||  |");

		assertEquals("dat1", data.get(0));
		assertEquals("dat2", data.get(1));
		assertEquals("dat3", data.get(2));
		assertEquals("dat4", data.get(3));
		assertEquals("", data.get(4));
		assertEquals("", data.get(5));
		assertEquals("", data.get(6));
	}

}

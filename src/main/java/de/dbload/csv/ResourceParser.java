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

package de.dbload.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import de.dbload.DataRow;

/**
 * Read a resource.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceParser {

    private String tableName;
    private List<String> columns;
    private DataRow data;
    private ParserState parserState;

    enum ParserState {
	TABLE_DEFINITION, COLUMN_DEFINITION, DATA_DEFINITION, COMMENT_OR_EMPTY
    }

    /**
     * The name of the current table.
     * 
     * @return table name
     */
    public String getTableName() {
	return tableName;
    }

    /**
     * The column description.
     * 
     * @return Column description.
     */
    public List<String> getColumns() {
	return columns;
    }

    /**
     * The data for a table row.
     * 
     * @return data of a row in a table.
     */
    public DataRow getDataRow() {
	return data;
    }

    /**
     * Reads the table definition
     * 
     * @param line One line from the resource
     * @return ParserState
     */
    public ParserState parse(String line) {
	String currentLine = StringUtils.trimToEmpty(line);

	if (currentLine.startsWith("### TAB")) {
	    tableName = readTableDefinition(line);
	    parserState = ParserState.TABLE_DEFINITION;
	} else if (currentLine.startsWith("###")) {
	    columns = readColumnNames(line);
	    parserState = ParserState.COLUMN_DEFINITION;
	} else if ((currentLine.length() > 0 && currentLine.charAt(0) == '#')
		|| StringUtils.isBlank(currentLine)) {
	    parserState = ParserState.COMMENT_OR_EMPTY;
	} else if (!StringUtils.isBlank(currentLine)) {
	    readRow(columns, line);
	    parserState = ParserState.DATA_DEFINITION;
	}
	return parserState;
    }

    /**
     * Reading a line of data.
     * 
     * @param line the data
     * @return list with data
     */
    public DataRow readRow(List<String> columns, String line) {
	data = new DataRow();
	StringTokenizer stok = new StringTokenizer(line, "|", true);
	boolean lastTokenIsDelim = false;
	int index = 0;
	while (stok.hasMoreTokens()) {
	    String token = stok.nextToken();
	    if (StringUtils.equals("|", token)) {
		if (lastTokenIsDelim) {
		    if (index >= columns.size()) {
			throw new IllegalStateException(
				"More data than columns!");
		    }

		    data.put(columns.get(index++), null);
		}
		lastTokenIsDelim = true;
	    } else {
		// Check, if more data than defined columns
		if (index >= columns.size()) {
		    throw new IllegalStateException("More data than columns!");
		}

		data.put(columns.get(index++), (token == null ? null
			: StringUtils.trimToNull(token)));
		lastTokenIsDelim = false;
	    }
	}

	if (lastTokenIsDelim) {
	    if (index >= columns.size()) {
		throw new IllegalStateException("More data than columns!");
	    }
	    data.put(columns.get(index), null);
	}

	return data;
    }

    /**
     * Read the column definition
     * 
     * @param columnDefinition column definitions
     * @return column description
     */
    public List<String> readColumnNames(String columnDefinition) {
	List<String> columns = new ArrayList<>();

	String tmp = columnDefinition.trim();
	if (!tmp.startsWith("###")) {
	    throw new IllegalStateException("Expected ###");
	}

	tmp = StringUtils.removeStart(tmp, "###");

	StringTokenizer stok = new StringTokenizer(tmp, "|");
	while (stok.hasMoreTokens()) {
	    String token = stok.nextToken();

	    if (StringUtils.contains(token, "(date)")) {
		token = StringUtils.remove(token, "(date)");
	    }

	    columns.add(token.trim());
	}

	return columns;
    }

    /**
     * Read the table definition
     * 
     * @param tableDefinition
     *            the table definition
     * @return the table name
     */
    public String readTableDefinition(String tableDefinition) {
	String tableName = "";

	StringTokenizer stok = new StringTokenizer(tableDefinition);
	if (stok.hasMoreTokens()) {
	    String token = stok.nextToken();
	    if (!StringUtils.equals(token, "###")) {
		throw new IllegalStateException("Expected ###");
	    }
	}

	if (stok.hasMoreTokens()) {
	    String token = stok.nextToken();
	    if (!StringUtils.equals(token, "TAB")) {
		throw new IllegalStateException("Expected TAB");
	    }
	}

	if (stok.hasMoreTokens()) {
	    String token = stok.nextToken();
	    if (StringUtils.isBlank(token)) {
		throw new IllegalStateException();
	    }

	    tableName = token;
	}

	return tableName;
    }

}

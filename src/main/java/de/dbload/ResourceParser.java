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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import de.dbload.meta.ColumnMetaData;

class ResourceParser {

	private String tableName;
	private List<ColumnMetaData> columns;
	private List<String> data;
	private ParserState parserState;

	enum ParserState {
		TABLE_DEFINITION, COLUMN_DEFINITION, DATA_DEFINITION, COMMENT_OR_EMPTY
	}

	/**
	 * Der aktuelle Tabellenname.
	 * 
	 * @return Der Tabellenname
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Die Namen der Tabellenspalten.
	 * 
	 * @return Die Namen der Tabellenspalten.
	 */
	public List<de.dbload.meta.ColumnMetaData> getColumns() {
		return columns;
	}

	/**
	 * Die Daten zu der Tabelle.
	 * 
	 * @return Die Daten zu der Tabelle.
	 */
	public List<String> getData() {
		return data;
	}

	/**
	 * Liest die Tabellendefinition
	 * 
	 * @param line
	 *            Eine Zeile aus der Ressource.
	 * @return ParserState
	 */
	public ParserState parse(String line) {
		String currentLine = org.apache.commons.lang.StringUtils
				.trimToEmpty(line);

		if (currentLine.startsWith("### TAB")) {
			tableName = readTableDefinition(line);
			parserState = ParserState.TABLE_DEFINITION;
		} else if (currentLine.startsWith("###")) {
			columns = readColumns(line);
			parserState = ParserState.COLUMN_DEFINITION;
		} else if ((currentLine.length() > 0 && currentLine.charAt(0) == '#')
				|| org.apache.commons.lang.StringUtils.isBlank(currentLine)) {
			parserState = ParserState.COMMENT_OR_EMPTY;
		} else if (!org.apache.commons.lang.StringUtils.isBlank(currentLine)) {
			readData(line);
			parserState = ParserState.DATA_DEFINITION;
		}
		return parserState;
	}

	/**
	 * Liest eine Datenzeile ein.
	 * 
	 * @param line
	 *            Die in die Tabelle einzufuegenden Daten
	 * @return Eine Liste mit den Daten
	 */
	public List<String> readData(String line) {
		data = new ArrayList<>();
		StringTokenizer stok = new StringTokenizer(line, "|", true);
		boolean lastTokenIsDelim = false;
		while (stok.hasMoreTokens()) {
			String token = stok.nextToken();
			if (org.apache.commons.lang.StringUtils.equals("|", token)) {
				if (lastTokenIsDelim) {
					data.add("");
				}
				lastTokenIsDelim = true;
			} else {
				data.add(token == null ? null : token.trim());
				lastTokenIsDelim = false;
			}
		}

		if (lastTokenIsDelim) {
			data.add("");
		}

		return data;
	}

	/**
	 * Liest die Spaltennamen
	 * 
	 * @param columnDefinition
	 *            Die Spaltendefinition
	 * @return Eine Liste mit Spaltennamen
	 */
	public List<ColumnMetaData> readColumns(String columnDefinition) {
		List<ColumnMetaData> columns = new ArrayList<>();

		String tmp = columnDefinition.trim();
		if (!tmp.startsWith("###")) {
			throw new IllegalStateException("Expected ###");
		}

		tmp = org.apache.commons.lang.StringUtils.removeStart(tmp, "###");

		StringTokenizer stok = new StringTokenizer(tmp, "|");
		while (stok.hasMoreTokens()) {
			String token = stok.nextToken();

			ColumnMetaData.Type type = ColumnMetaData.Type.DEFAULT;
			if (org.apache.commons.lang.StringUtils.contains(token, "(date)")) {
				token = org.apache.commons.lang.StringUtils.remove(token,
						"(date)");
				type = ColumnMetaData.Type.DATE;
			}

			columns.add(new ColumnMetaData(token.trim(), type));
		}

		return columns;
	}

	/**
	 * Liest die Tabellendefinition
	 * 
	 * @param tableDefinition
	 *            Die Tabellendefinition
	 * @return Der Tabellennamne
	 */
	public String readTableDefinition(String tableDefinition) {
		String tableName = "";

		StringTokenizer stok = new StringTokenizer(tableDefinition);
		if (stok.hasMoreTokens()) {
			String token = stok.nextToken();
			if (!org.apache.commons.lang.StringUtils.equals(token, "###")) {
				throw new IllegalStateException("Expected ###");
			}
		}

		if (stok.hasMoreTokens()) {
			String token = stok.nextToken();
			if (!org.apache.commons.lang.StringUtils.equals(token, "TAB")) {
				throw new IllegalStateException("Expected TAB");
			}
		}

		if (stok.hasMoreTokens()) {
			String token = stok.nextToken();
			if (org.apache.commons.lang.StringUtils.isBlank(token)) {
				throw new IllegalStateException();
			}

			tableName = token;
		}

		return tableName;
	}

}

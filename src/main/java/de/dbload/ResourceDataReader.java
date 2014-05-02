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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reads the data resource.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceDataReader {

	private final Class<?> resourceClass;
	private final String resourceName;

	private InputStream resourceAsStream;
	private InputStreamReader inputStreamReader;
	private BufferedReader bufferedReader;
	private String line;

	/**
	 * Konstruktor
	 * 
	 * @param resourceName
	 *            der Name der zu ladenden Klassenpfadressource
	 */
	public ResourceDataReader(String resourceName) {
		this(resourceName, ResourceDataReader.class);
	}

	/**
	 * Konstruktor
	 * 
	 * @param resourceName
	 *            der Name der zu ladenden Klassenpfadressource
	 * @param resourceClass
	 *            die Resource relativ zu dieser Klasse
	 */
	public ResourceDataReader(String resourceName, Class<?> resourceClass) {
		this.resourceName = resourceName;
		this.resourceClass = resourceClass;
	}

	/**
	 * Oeffnen der Ressource.
	 * 
	 * @throws IOException
	 *             Ups
	 */
	public void open() throws IOException {
		resourceAsStream = resourceClass.getResourceAsStream(resourceName);
		if (resourceAsStream == null) {
			throw new IOException("Die Ressource '" + resourceName
					+ "' kann nicht geoeffnet werden!");
		}
		inputStreamReader = new InputStreamReader(resourceAsStream);
		bufferedReader = new BufferedReader(inputStreamReader);
	}

	/**
	 * Liest eine Resourcendatei ein.
	 * 
	 * @return Eine Zeile aus der Datei
	 * @throws IOException
	 *             Ups
	 */
	public String readLine() throws IOException {
		line = bufferedReader.readLine();
		return line;
	}

	/**
	 * Ende der Datei erreicht?
	 * 
	 * @return Liefert <code>true</code>, falls das Ende der Datei erreicht
	 *         wurde.
	 */
	public boolean endOfFile() {
		return null == line;
	}

	/**
	 * Schliesst die gebundenen Ressourcen
	 * 
	 * @throws IOException
	 *             Ups
	 */
	public void close() throws IOException {
		if (bufferedReader != null) {
			bufferedReader.close();
		}

		if (inputStreamReader != null) {
			inputStreamReader.close();
		}

		if (resourceAsStream != null) {
			resourceAsStream.close();
		}
	}

}

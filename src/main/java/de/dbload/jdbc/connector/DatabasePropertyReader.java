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

package de.dbload.jdbc.connector;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads the database properties.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DatabasePropertyReader {

    /**
     * Read the properties from the default file <code>/db.properties</code>
     * 
     * @return the database properties
     * @throws IOException
     *             Ups
     */
    public Properties read() throws IOException {
        return read("/db.properties");
    }

    /**
     * Read the properties.
     * 
     * @param resource
     *            the resource path name
     * @return the database properties
     * @throws IOException
     *             Ups
     */
    public Properties read(String resource) throws IOException {
        InputStream is = DatabasePropertyReader.class
                .getResourceAsStream(resource);
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }

    /**
     * Get the database url.
     * 
     * @param properties
     *            the database properties
     * @return the database url
     */
    public String getDatabaseUrl(Properties properties) {
        return properties.getProperty("dbload.database.url");
    }

}
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import de.dbload.impl.DbloadException;

/**
 * Reads the database properties.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DatabasePropertyReader {

    private static final String DB_PROPERTIES_FILE = "/db.properties";
    private static final String DB_PROPERTIES_FILE_MY = "/my-db.properties";
    
    private static final String DBLOAD_DATABASE_URL_KEY = "dbload.database.url";

    private final Properties properties = new Properties();

    /**
     * Read the properties from the default file <code>/db.properties</code>, or
     * from <code>/my-db.properties</code> or
     * from the system environment. Environment variable wins before file! The
     * <code>my-db.properties</code> file wins before <code>/db.properties</code>.
     */
    private Properties read() {
        Properties properties = readFromFile(new Properties(), DB_PROPERTIES_FILE);
        readFromFile(properties, DB_PROPERTIES_FILE_MY);

        String propertyFromEnv = System.getenv(DBLOAD_DATABASE_URL_KEY);
        if (StringUtils.isNotBlank(propertyFromEnv)) {
            properties.put(DBLOAD_DATABASE_URL_KEY, propertyFromEnv);
        }

        return properties;
    }

    private Properties readFromFile(Properties properties, String fileName) {
        try {
            Properties propertiesFromFile = readFromClasspathFile(fileName);
            properties.putAll(propertiesFromFile);
        } catch (FileNotFoundException ex) {
            // ignore me
        } catch (IOException ex) {
            throw new DbloadException(ex);
        }
        return properties;
    }
    
    /**
     * Read the properties.
     * 
     * @param resource the resource path name
     * @return the database properties
     * @throws IOException Unable to read the property file.
     */
    private Properties readFromClasspathFile(String resource) throws IOException {
        InputStream is = DatabasePropertyReader.class.getResourceAsStream(resource);
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }

    /**
     * Get the database url.
     *
     * @return the database url
     */
    public String getDatabaseUrl() {
        return read().getProperty(DBLOAD_DATABASE_URL_KEY);
    }

}

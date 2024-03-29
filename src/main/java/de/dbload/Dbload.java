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

package de.dbload;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import de.dbload.impl.DefaultDbloadContext;
import de.dbload.impl.DefaultDbloadImpl;

/**
 * Entry point for uploading data to the database.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class Dbload {

    private Dbload() {
        // Private!!!
    }

    /**
     * Start upload.
     *
     * @param context the context for dbload
     * @param clazz   used as classloader root for the data file
     */
    public static void read(DbloadContext context, Class<?> clazz) {
        DefaultDbloadImpl dbload = new DefaultDbloadImpl();
        dbload.readFromClasspathResource(context, clazz);
    }

    /**
     * Start upload.
     *
     * @param context  the context for dbload
     * @param clazz    used as classloader root for the data file
     * @param resource the resource to load with the classloader
     */
    public static void read(DbloadContext context, Class<?> clazz, String resource) {
        DefaultDbloadImpl dbload = new DefaultDbloadImpl();
        dbload.readFromClasspathResource(context, clazz, resource);
    }

    /**
     * Start upload
     *
     * @param connection the database JDBC connection
     * @param clazz      used as classloader root for the data file
     */
    public static void read(Connection connection, Class<?> clazz) {
        DbloadContext context = DefaultDbloadContext.of(connection);
        DefaultDbloadImpl dbload = new DefaultDbloadImpl();
        dbload.readFromClasspathResource(context, clazz);
    }

    /**
     * Start upload
     *
     * @param connection the database JDBC connection
     * @param clazz      used as classloader root for the data file
     * @param resource   the resource to load with the classloader
     */
    public static void read(Connection connection, Class<?> clazz, String resource) {
        DbloadContext context = DefaultDbloadContext.of(connection);
        DefaultDbloadImpl dbload = new DefaultDbloadImpl();
        dbload.readFromClasspathResource(context, clazz, resource);
    }

    /**
     * Read the data from a file and put it to the database.
     *
     * @param connection   the database JDBC connection
     * @param readFromFile the file to read from
     */
    public static void read(Connection connection, File readFromFile) {
        DbloadContext context = DefaultDbloadContext.of(connection);
        DefaultDbloadImpl dbload = new DefaultDbloadImpl();
        dbload.readFromFile(context, readFromFile);
    }

    /**
     * Read the data from a file and put it to the database.
     *
     * @param context  the context for dbload
     * @param readFromFile the file to read from
     */
    public static void read(DbloadContext context, File readFromFile) {
        DefaultDbloadImpl dbload = new DefaultDbloadImpl();
        dbload.readFromFile(context, readFromFile);
    }

    /**
     * Export all tables to a file.
     *
     * @param connection  the database JDBC connection
     * @param writeToFile the file to write to
     * @param tableNames  the database tables to export
     */
    public static void write(Connection connection, File writeToFile, String[] tableNames) {
        DbloadContext context = DefaultDbloadContext.of(connection);
        DefaultDbloadImpl dbload = new DefaultDbloadImpl();
        dbload.writeToFile(context, writeToFile, tableNames);
    }

    /**
     * Export all tables to a file.
     *
     * @param connection  the database JDBC connection
     * @param writeToFile the file to write to
     * @param tableNames  the database tables to export
     */
    public static void write(Connection connection, File writeToFile, List<String> tableNames) {
        DbloadContext context = DefaultDbloadContext.of(connection);
        DefaultDbloadImpl dbload = new DefaultDbloadImpl();
        dbload.writeToFile(context, writeToFile, tableNames);
    }

}

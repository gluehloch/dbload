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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reads the data resource.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceDataReader implements Closeable {

    private InputStream resourceAsStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private String line;

    /**
     * Constructor.
     *
     * @param inputStream the dat resource as stream
     */
    public ResourceDataReader(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalStateException("The inputStream is not defined.");
        }
        resourceAsStream = inputStream;
    }

    /**
     * Opens the resource.
     *
     * @throws IOException Ups
     */
    public void open() throws IOException {
        if (resourceAsStream == null) {
            throw new IllegalStateException("The inputStream is not defined.");
        }
        inputStreamReader = new InputStreamReader(resourceAsStream, "UTF-8");
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    /**
     * Read one line of the resource.
     *
     * @return line of data
     * @throws IOException Ups
     */
    public String readLine() throws IOException {
        line = bufferedReader.readLine();
        return line;
    }

    /**
     * Reached end of file?
     *
     * @return Returns <code>true</code>, if reached end of file
     */
    public boolean endOfFile() {
        return null == line;
    }

    /**
     * Close all resources.
     */
    public void close() {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                // ok
            }
        }

        if (inputStreamReader != null) {
            try {
                inputStreamReader.close();
            } catch (IOException ex) {
                // ok
            }
        }

        if (resourceAsStream != null) {
            try {
                resourceAsStream.close();
            } catch (IOException ex) {
                // ok
            }
        }
    }

}

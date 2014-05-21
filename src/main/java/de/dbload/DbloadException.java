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

/**
 * {@link RuntimeException} for some DBLoad problems.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadException extends RuntimeException {

    private static final long serialVersionUID = -6258629715078833636L;

    public DbloadException() {
	super();
    }

    public DbloadException(Exception ex) {
	super(ex);
    }

    public DbloadException(String message) {
	super(message);
    }

    public DbloadException(String message, Exception ex) {
	super(message, ex);
    }

}

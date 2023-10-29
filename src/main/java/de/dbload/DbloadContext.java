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

import java.sql.Connection;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Defines JDBC connection and JDBC type converter.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public interface DbloadContext {

    Connection connection();

    JdbcTypeConverter converter();

    ZoneId zoneId();

    DateTimeFormatter dateTimeFormatter();

    DecimalFormat decimalFormat();

}
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

package de.dbload.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.dbload.DbloadException;

/**
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcTemplate {

	private Connection conn;

	public void execute(String sql) {
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException ex) {
			throw new DbloadException(ex);
		}
	}

	public void executeBatch(String sql) {
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			//
		} catch (SQLException ex) {
			throw new DbloadException(ex);
		}
	}

}

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

import de.dbload.meta.TableMetaData;

/**
 * The representation of a SQL statement.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class SqlInsertStatement {

    private String sql;
    private TableMetaData tableMetaData;

    public SqlInsertStatement(String _sql, TableMetaData _tableMetaData) {
        sql = _sql;
        tableMetaData = _tableMetaData;
    }

    public String getSql() {
        return sql;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sql == null) ? 0 : sql.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SqlInsertStatement other = (SqlInsertStatement) obj;
        if (sql == null) {
            if (other.sql != null)
                return false;
        } else if (!sql.equals(other.sql))
            return false;
        return true;
    }

}

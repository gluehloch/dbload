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

package de.dbload.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * A row of data or a container for key/values.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DataRow {

    private final Map<ColumnKey, String> map = new HashMap<>();

    public void put(ColumnKey columnName, String value) {
        map.put(columnName, value);
    }

    public int size() {
        return map.size();
    }

    public String get(ColumnKey columnKey) {
        return map.get(columnKey);
    }

    public Map<ColumnKey, String> asMap() {
        return map;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((map == null) ? 0 : map.hashCode());
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
        DataRow other = (DataRow) obj;
        if (map == null) {
            if (other.map != null)
                return false;
        } else if (!map.equals(other.map))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DataRow [map=" + map + "]";
    }

}

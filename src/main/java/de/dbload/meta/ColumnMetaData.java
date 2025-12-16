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

/**
 * Holds the name and type of a database column.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ColumnMetaData {

    private final ColumnKey columnKey;

    private final Type columnType;

    public enum Type {
        VARCHAR(java.sql.Types.VARCHAR),

        /**
         * Bit information. Very small.
         */
        BIT(java.sql.Types.BIT),

        /**
         * A big integer type.
         */
        LONG(java.sql.Types.BIGINT),

        /**
         * number with no decimals like byte, short, int
         */
        INTEGER(java.sql.Types.INTEGER),

        /**
         * number with decimals like float, double or BigDecimal
         */
        DECIMAL(java.sql.Types.DECIMAL),

        /**
         * date (date without time)
         */
        DATE(java.sql.Types.DATE),

        /**
         * time (time without date)
         */
        TIME(java.sql.Types.TIME),

        /**
         * date and time
         */
        DATE_TIME(java.sql.Types.TIMESTAMP),

        /**
         * Boolean
         */
        BOOLEAN(java.sql.Types.BOOLEAN);

        private int javaSqlType;

        Type(int type) {
            javaSqlType = type;
        }

        public int getJavaSqlType() {
            return javaSqlType;
        }

        public static Type valueOf(int javaSqlType) {
            for (Type type : Type.values()) {
                if (type.getJavaSqlType() == javaSqlType) {
                    return type;
                }
            }
            throw new IllegalStateException("Unknown column type found: '"
                    + javaSqlType + "'");
        }
    }

    /**
     * Constructor
     *
     * @param columnKey  column key name
     * @param columnType column type
     */
    public ColumnMetaData(ColumnKey columnKey, Type columnType) {
        this.columnKey = columnKey;
        this.columnType = columnType;
    }

    /**
     * Column key name
     *
     * @return Column key name
     */
    public ColumnKey getColumnKey() {
        return columnKey;
    }

    /**
     * Column type
     *
     * @return Column type
     */
    public Type getColumnType() {
        return columnType;
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", columnKey.getColumnName(), columnType);
    }

}

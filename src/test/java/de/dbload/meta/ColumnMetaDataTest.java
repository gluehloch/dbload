package de.dbload.meta;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ColumnMetaDataTest {

    @Test
    void columnMetaDataToString() {
        ColumnMetaData columnMetaData = new ColumnMetaData("column", ColumnMetaData.Type.VARCHAR);
        assertThat(columnMetaData.toString()).isEqualTo("column[VARCHAR]");
    }

}

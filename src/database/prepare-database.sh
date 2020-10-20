#!/bin/bash
CREATE_MARIADB_USER='create_mysql.sql'
CREATE_MARIADB_SCHEMA='create_mysql_test_schema.sql'

mysql -u root -h 127.0.0.1 < ${CREATE_MARIADB_USER}
mysql -u dbload --password=dbload -D dbload -h 127.0.0.1 < ${CREATE_MARIADB_SCHEMA}


#!/bin/bash
CREATE_MARIADB_USER='create_mysql.sql'
CREATE_MARIADB_SCHEMA='create_mysql_test_schema.sql'

mysql -u root -h 192.168.99.100 -P 3310 < ${CREATE_MARIADB_USER}
mysql -u dbload --password=dbload -D dbload -h 192.168.99.100 -P 3310 < ${CREATE_MARIADB_SCHEMA}


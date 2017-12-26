#!/bin/sh
CREATE_MARIADB_USER='create_mysql.sql'
CREATE_MARIADB_SCHEMA='create_mysql_test_schema.sql'

mysql -u root --password=root < /tmp/${CREATE_MARIADB_USER}
mysql -u dbload --password=dbload < /tmp/${CREATE_MARIADB_SCHEMA}

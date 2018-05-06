#!/bin/bash
export DOCKER_NAME='dbload'
export DOCKER_DB_HOST='192.168.99.100'

# Die Testdatenbank laueft unter Port 3310. Damit gehe ich dem Konflikt mit
# der betoffice Datenbank aus dem Weg.
docker run --expose=3306 -p 3310:3306 --name ${DOCKER_NAME} -e MYSQL_ALLOW_EMPTY_PASSWORD=true -d mariadb:latest

#export DOCKER_CONTAINER_ID=$(docker inspect -f '{{.Id}}' ${DOCKER_NAME})
#CREATE_MARIADB_USER='create_mysql.sql'
#CREATE_MARIADB_SCHEMA='create_mysql_test_schema.sql'

#docker cp ${CREATE_MARIADB_USER} ${DOCKER_CONTAINER_ID}:/tmp/${CREATE_MARIADB_USER}
#docker cp ${CREATE_MARIADB_SCHEMA} ${DOCKER_CONTAINER_ID}:/tmp/${CREATE_MARIADB_SCHEMA}
#docker cp prepare-database.sh  ${DOCKER_CONTAINER_ID}:/tmp/prepare-database.sh

#sleep 30s

#docker exec ${DOCKER_NAME} /tmp/prepare-database.sh

mysql -u root --password=root -h $DOCKER_DB_HOST < create_mysql.sql
mysql -u root --password=root -h $DOCKER_DB_HOST -D dbload < create_mysql_test_schema.sql

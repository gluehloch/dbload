#/bin/bash
export DOCKER_NAME='dbloaddb'
export DOCKER_DB_HOST='192.168.99.100'
docker run --expose=3306 -p 3306:3306 --name ${DOCKER_NAME} -e MYSQL_ROOT_PASSWORD=root -d mariadb:latest

export DOCKER_CONTAINER_ID=$(docker inspect -f '{{.Id}}' ${DOCKER_NAME})
CREATE_MARIADB_USER='create_mysql.sql'
CREATE_MARIADB_SCHEMA='create_mysql_test_schema.sql'

docker cp ${CREATE_MARIADB_USER} ${DOCKER_CONTAINER_ID}:/tmp/${CREATE_MARIADB_USER}
docker cp ${CREATE_MARIADB_SCHEMA} ${DOCKER_CONTAINER_ID}:/tmp/${CREATE_MARIADB_SCHEMA}

#mysql -u root --password=root -h $DOCKER_DB_HOST < create_mysql.sql
#mysql -u root --password=root -h $DOCKER_DB_HOST < create_mysql_test_schema.sql

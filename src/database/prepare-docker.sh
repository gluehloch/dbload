#/bin/bash
export DOCKER_DB_HOST='192.168.99.100'
docker run --expose=3306 -p 3306:3306 --name dbloaddb -e MYSQL_ROOT_PASSWORD=root -d mariadb:latest

export DOCKER_CONTAINER_ID=$(docker inspect -f   '{{.Id}}'  dbloaddb)
echo $DOCKER_CONTAINER_ID

#mysql -u root --password=root -h $DOCKER_DB_HOST < create_mysql.sql
#mysql -u root --password=root -h $DOCKER_DB_HOST < create_mysql_test_schema.sql

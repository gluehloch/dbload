# Mit diesem Kommando das Image bauen:
# docker build -t gluehloch/dbloaddb .

# Mit diesen Kommandos das Image starten:
# docker run --expose=3306 -p 3306:3306 --name dbloaddb -d gluehloch/dbloaddb:latest
# Oder das Standard-Image von MariaDB
# docker run --expose=3306 -p 3306:3306 --name dbloaddb -e MYSQL_ROOT_PASSWORD=root -d mariadb:latest

# Eine Bash in dem laufenden Container starten:
# docker exec -it dbloaddb bash

#FROM gluehloch/mariadb-awi:latest
FROM mariadb:latest
# RUN apt-get -y update && apt-get install -y vim

ENV MYSQL_ROOT_PASSWORD_FILE=root
ENV TMP_DIR=/tmp/dbload
# docker-entrypoint-initdb.d
ENV DOCKER_ENTRYPOINT_DIR=/docker-entrypoint-initdb.d

ADD create-dbload.sql $DOCKER_ENTRYPOINT_DIR
RUN chmod -R ugo+rwx /docker-entrypoint-initdb.d

# TODO Does not work ... cure me!
# COPY *.sql ${TMP_DIR}

# TODO Das funktioniert nicht. Der Server ist in diesem Zustand nicht erreichbar.
#RUN /bin/bash -c 'mysql -u root --password=root << /tmp/create_mysql.sql'
#RUN /bin/bash -c 'mysql -u dbload --password=dbload << /tmp/create_mysql_test_schema.sql'

EXPOSE 3306

# docker run --name betofficedb -e MYSQL_ROOT_PASSWORD=root -d mariadb:latest

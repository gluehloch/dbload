#FROM gluehloch/mariadb-awi:latest
FROM mariadb:latest
# RUN apt-get -y update && apt-get install -y vim

ENV MYSQL_ROOT_PASSWORD_FILE=root
ENV TMP_DIR=/tmp/

# TODO Does not work ... cure me!
COPY *.sql ${TMP_DIR}

# TODO Das funktioniert nicht. Der Server ist in diesem Zustand nicht erreichbar.
RUN /bin/bash -c 'mysql -u root --password=root << /tmp/create_mysql.sql'
RUN /bin/bash -c 'mysql -u dbload --password=dbload << /tmp/create_mysql_test_schema.sql'

EXPOSE 3306

# docker run --name betofficedb -e MYSQL_ROOT_PASSWORD=root -d mariadb:latest

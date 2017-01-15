FROM gluehloch/mariadb-awi:latest
RUN apt-get -y update && apt-get install -y vim
COPY *.sql /tmp/

#RUN /bin/bash -c 'mysql -u root << /tmp/create_mysql.sql'
#RUN /bin/bash -c 'mysql -u dbload --password=dbload << /tmp/create_mysql_test_schema.sql'


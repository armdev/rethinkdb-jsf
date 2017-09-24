# rethinkdb-jsf

rethinkdb jsf 2.3 project

Project automatically generated fake data each 5 second and save in rethinkdb.
In the index.jsf page primefaces datatable automatically updated and show records from db.

1. mvn clean package -U
2. rethinkdb --bind all
3. copy/paste rethink-web.war to tomcat/webapps
4. start tomcat on port 8585
5. access http://localhost:8585/rethink-web/index.jsf




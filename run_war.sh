mkdir -p /data/log/

mongod --dbpath /data/db --logpath /data/log/mongo.log --fork

apache-tomcat-9.0.89/bin/catalina.sh run
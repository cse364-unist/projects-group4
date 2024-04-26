mkdir -p /data/log/

mongod --dbpath /data/db --logpath /data/log/mongo.log --fork

cd project

mvn package

java -jar target/cse364-project-1.0-SNAPSHOT.jar
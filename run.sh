mkdir -p /data/log/

mongod --dbpath /data/db --logpath /data/log/mongo.log --fork
# mongod --dbpath ~/data/db --logpath ~/data/log/mongodb/mongo.log

#git clone https://github.com/cse364-unist/projects-group4.git
#git checkout milestone3
# cd projects-group4

cd project

mvn -f pom.xml package  # For JAR
mvn -f pom_war.xml package  # For WAR

java -jar target/cse364-project-1.0-SNAPSHOT.jar

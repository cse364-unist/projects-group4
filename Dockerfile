# Use Ubuntu 22.04 as base image
FROM ubuntu:22.04

ENV TZ="Asia/Seoul"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Install prerequisites
RUN apt-get update \
    && apt-get install -y wget gnupg2 \
    && rm -rf /var/lib/apt/lists/*

# Import MongoDB public GPG key
RUN wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -

# Add MongoDB repository to sources list
RUN echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/6.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-6.0.list

# Update package list and install MongoDB

RUN apt-get update \
    && apt-get install --assume-yes git \
    && apt-get install -y mongodb-org \
    && rm -rf /var/lib/apt/lists/*

# Create necessary directories
RUN mkdir -p /data/db /data/configdb

# Change ownership of directories
RUN chown -R mongodb:mongodb /data/db /data/configdb

# Add your stuff below:
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven
RUN apt install curl unzip
WORKDIR /app

# COPY project/ /app/project/
COPY ROOT.war /app/ROOT.war

COPY run.sh /app/run.sh
COPY run_war.sh /app/run_war.sh

RUN chmod +x /app/run.sh
RUN chmod +x /app/run_war.sh

# install tomcat
RUN wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.89/bin/apache-tomcat-9.0.89.zip
RUN unzip apache-tomcat-9.0.89.zip

RUN rm -r -f apache-tomcat-9.0.89/webapps/ROOT
RUN mv ROOT.war apache-tomcat-9.0.89/webapps/ROOT.war
RUN chmod +x apache-tomcat-9.0.89/bin/catalina.sh

# Run it
CMD ["bash", "run_war.sh"]
# docker run -d -p 8080:8080 host image_name
FROM openjdk:8-jdk-alpine

RUN apk --update add tzdata
RUN echo "America/Bogota" > /etc/timezone
RUN ln -snf /usr/share/zoneinfo/America/Bogota /etc/localtime && echo America/Bogota > /etc/timezone

ARG DEPENDENCY=./target
COPY ${DEPENDENCY}/apisaludcitasinorden.jar /home/apisaludcitasinorden.jar

ENV SERVER_PORT=SERVER_PORT 
ENV APIGEE=APIGEE 
ENV APIGEE_CLIENT_ID=APIGEE_CLIENT_ID 
ENV APIGEE_CLIENT_SECRET=APIGEE_CLIENT_SECRET 
ENV APP_NAME=APP_NAME 
ENV APP_VERSION=APP_VERSION 
ENV AZURE_INSIGHT_KEY=AZURE_INSIGHT_KEY 
ENV ELASTICSEARCH_INDEX=ELASTICSEARCH_INDEX 
ENV ELASTICSEARCH_TYPE=ELASTICSEARCH_TYPE 
ENV MYSQL_CONNECTION_SPRING=MYSQL_CONNECTION_SPRING 
ENV MYSQL_PASSWORD=MYSQL_PASSWORD 
ENV MYSQL_USER=MYSQL_USER 

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=release","/home/apisaludcitasinorden.jar"]
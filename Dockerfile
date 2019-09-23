FROM openjdk:8-jdk-alpine

ARG DEPENDENCY=target/dependency
ARG DEPLOYMENT_PORT
ARG CONNECTIONSTRINGBLOB
ARG CONTAINERNAME
ARG MYSQL_USER      
ARG MYSQL_PASSWORD    
ARG MYSQL_CONECTION_SPRING   
ARG EMAIL_HOST 
ARG EMAIL 
ARG EMAIL_PASSWORD 
ARG EMAIL_PORT           

CMD apk --update add tzdata && \ cp -R /usr/share/zoneinfo/America/Bogota /etc/localtime && \ apk del tzdata && \ rm -rf /var/cache/apk/* 
COPY ${DEPENDENCY}/BOOT-INF/lib /back-dist/lib
COPY ${DEPENDENCY}/META-INF /back-dist/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /back-dist
ENTRYPOINT ["java","-cp","back-dist:back-dist/lib/*","com.colsubsidio.salud.AppPortalSaludApplication"]
CMD [ "env" ]
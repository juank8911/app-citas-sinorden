FROM openjdk:8-jdk-alpine
CMD apk --update add tzdata && \ cp -R /usr/share/zoneinfo/America/Bogota /etc/localtime && \ apk del tzdata && \ rm -rf /var/cache/apk/* 
ARG DEPENDENCY=target
COPY ${DEPENDENCY}/salud-backend-citas-sinorden-0.0.1.jar /home/salud-backend-citas-sinorden-0.0.1.jar
COPY ${DEPENDENCY}/classes/props /home/props
COPY ${DEPENDENCY}/classes/templates /home/templates
ENV ENVIROMENTS=ENVIROMENTS

RUN echo $ENVIROMENTS
ENTRYPOINT ["java","-jar","/home/salud-backend-citas-sinorden-0.0.1.jar"]
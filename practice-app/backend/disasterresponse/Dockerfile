FROM maven:3.6.3-openjdk-17 as stage1
WORKDIR /app
COPY pom.xml .
# go-offline using the pom.xml
RUN mvn dependency:go-offline
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:17-jdk
VOLUME /tmp
EXPOSE 4000
ARG JAR_FILE=/app/target/disasterresponse-0.0.1-SNAPSHOT.jar
COPY --from=stage1 ${JAR_FILE} ./app.jar
ENTRYPOINT ["java","-jar","./app.jar"]

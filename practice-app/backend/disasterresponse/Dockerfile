FROM openjdk:17-jdk
VOLUME /tmp
EXPOSE 4000
ARG JAR_FILE=target/disasterresponse-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
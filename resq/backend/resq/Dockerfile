FROM openjdk:17-jdk
WORKDIR /app
COPY target/resq-0.0.1-SNAPSHOT.jar /app/app.jar
COPY target/project_env /app/config
ENV resq.appdir=/app/config
ENTRYPOINT ["java","-jar","/app/app.jar"]
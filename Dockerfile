FROM maven:3.6.1-jdk-8-slim AS build
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp
RUN mvn -DDB_URL=jdbc:postgresql://172.17.0.5:5432/hrms -DDB_USERNAME=camunda -DDB_PASSWORD=camunda -Dserver.port=7661 package

FROM openjdk:8-jdk-alpine
LABEL platform="msp"
LABEL service_type="apiservice"
LABEL service_name="hrms_database"
LABEL service_area="hrms"
VOLUME /tmp
EXPOSE 7661
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
#ADD target/hrms_database.jar /app/app.jar
COPY --from=build /tmp/target/*.jar /app/app.jar
WORKDIR /app 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-Dserver.port=7661", "-jar", "/app/app.jar"]

#
# To Create Docker Build From Command Line
# docker build ./ -t hrms_database
# To Run Docker Image
# docker run -p 7661:7661 -e "DB_URL=jdbc:postgresql://172.17.0.5:5432/hrms" -e "DB_USERNAME=camunda" -e "DB_PASSWORD=camunda" -e "-Dserver.port=7661" hrms_database
#
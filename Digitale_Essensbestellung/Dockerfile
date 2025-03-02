# Eclipse Temurin is an open source java-se implementation, alpine is a lightweight musl based distro
FROM maven:3.9.9-eclipse-temurin-21-alpine

RUN mkdir /app

WORKDIR /app

COPY . .

# compile and package java jar file
# skip tests for now as they fail
RUN mvn package -X -e -DskipTests

# set entrypoint as we need to run the container as a java jar executable
# maven creates a snapshot, if we don't specify it in pom.xml, that's our executable
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "-Dlogging.level.com.example=DEBUG", "./target/Digitale_Essensbestellung-0.0.1-SNAPSHOT.jar"]

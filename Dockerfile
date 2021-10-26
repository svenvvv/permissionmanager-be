FROM maven:3.8.2-openjdk-17 AS build

WORKDIR /be
COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN mvn compile package

FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar
COPY --from=build /be/${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

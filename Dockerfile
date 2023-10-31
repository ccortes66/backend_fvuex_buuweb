FROM maven:3.9.4-eclipse-temurin-17-alpine as builder

COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder target/*.war app.war
EXPOSE 8081
CMD [ "java","-jar", "app.war","--spring.profiles.active=prod"]
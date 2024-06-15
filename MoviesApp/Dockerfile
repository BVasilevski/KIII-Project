FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw

RUN ./mvnw clean install -DskipTests

FROM openjdk:17-slim
VOLUME /tmp

COPY --from=build /workspace/app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

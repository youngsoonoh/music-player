FROM adoptopenjdk:11-jre-hotspot as builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew build -x test


FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY build/elasticsearch /build/elasticsearch
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/app.jar"]

FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY build/elasticsearch /build/elasticsearch
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/app.jar"]

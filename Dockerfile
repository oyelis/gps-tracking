FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} gps-tracking.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/gps-tracking.jar"]
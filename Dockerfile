FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8085
ADD ./target/api-comovimientos-0.0.1-SNAPSHOT.jar api-comovimientos.jar
ENTRYPOINT ["java","-jar","/api-comovimientos.jar"]
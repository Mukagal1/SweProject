FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/SWE-0.0.1-SNAPSHOT.jar SWE-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SWE-0.0.1-SNAPSHOT.jar"]
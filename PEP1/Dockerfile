FROM openjdk:17
ARG JAR_FILE=target/PEP1.jar
COPY ${JAR_FILE} PEP1.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/PEP1.jar"]

FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/spring-boot-docker-0.0.2.jar /usr/local/lib/spring-boot-docker-0.0.2.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/spring-boot-docker-0.0.2.jar"]


#FROM openjdk:11.0.4-jre-slim-buster
#RUN mvn clean install
#COPY "./target/spring-boot-docker-0.0.2.jar" "app.jar"
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]
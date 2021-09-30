FROM openjdk:12.0.2
RUN mkdir /app
EXPOSE 9999
COPY ./build/libs/eureka-server-1.0.0-SNAPSHOT.jar /app/java-application.jar
WORKDIR /app
CMD "java" "-jar" "java-application.jar"
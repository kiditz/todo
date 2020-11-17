FROM openjdk:8-jdk-alpine
COPY build/libs/app.jar /app/app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
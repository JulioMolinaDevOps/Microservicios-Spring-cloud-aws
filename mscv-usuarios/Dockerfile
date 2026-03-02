FROM openjdk:17.0.2-jdk

LABEL authors="moli"

WORKDIR /app

COPY ./target/mscv-usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar","mscv-usuarios-0.0.1-SNAPSHOT.jar"]
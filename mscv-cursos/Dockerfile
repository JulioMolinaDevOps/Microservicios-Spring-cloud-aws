FROM openjdk:17.0.2-jdk

LABEL authors="moli"

WORKDIR /app

COPY ./target/mscv-cursos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

ENTRYPOINT ["java", "-jar","mscv-cursos-0.0.1-SNAPSHOT.jar"]
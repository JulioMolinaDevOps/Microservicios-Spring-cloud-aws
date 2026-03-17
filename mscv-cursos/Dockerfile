FROM eclipse-temurin:17-jre-alpine

LABEL authors="moli"

WORKDIR /app

COPY ./target/mscv-cursos-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8002

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", "app.jar"]
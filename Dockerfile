FROM eclipse-temurin:17.0.10_7-jdk

COPY target/api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

# Build da API MegaFarma (Java 21 / Maven)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn -B -DskipTests package

# Imagem de execução menor: sem Maven ou código-fonte
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/megafarma-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

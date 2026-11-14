FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml ./
COPY src ./src 
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN mkdir -p /app/data && useradd --uid 10001 --create-home student \
     && chown -R student:student /app

COPY --from=build /build/target/ideas-api.jar ./app.jar
ENV DB_PATH=/app/data/ideas.db
USER student
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
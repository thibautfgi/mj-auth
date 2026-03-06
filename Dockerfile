FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY start-auth.sh /app/start-auth.sh
RUN sed -i 's/\r//' /app/start-auth.sh && chmod +x /app/start-auth.sh

EXPOSE 8081
ENTRYPOINT ["/app/start-auth.sh"]

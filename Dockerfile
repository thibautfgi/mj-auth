FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Script de démarrage : convertit DATABASE_URL (format Render postgresql://user:pass@host:port/db) en variables JDBC Spring
RUN printf '#!/bin/sh\n\
if [ -n "$DATABASE_URL" ]; then\n\
  # Extrait user:pass@host:port/db depuis postgresql://user:pass@host:port/db\n\
  STRIPPED=$(echo "$DATABASE_URL" | sed "s|^postgresql://||")\n\
  DB_USERINFO=$(echo "$STRIPPED" | cut -d@ -f1)\n\
  DB_HOSTPART=$(echo "$STRIPPED" | cut -d@ -f2)\n\
  export SPRING_DATASOURCE_USERNAME=$(echo "$DB_USERINFO" | cut -d: -f1)\n\
  export SPRING_DATASOURCE_PASSWORD=$(echo "$DB_USERINFO" | cut -d: -f2)\n\
  export SPRING_DATASOURCE_URL="jdbc:postgresql://${DB_HOSTPART}"\n\
fi\n\
# Render injecte PORT, on l\x27utilise sinon 8081 par defaut\n\
APP_PORT=${PORT:-8081}\n\
exec java -Dserver.port=$APP_PORT -jar app.jar "$@"\n' > /app/start.sh && chmod +x /app/start.sh

EXPOSE 8081
ENTRYPOINT ["/app/start.sh"]


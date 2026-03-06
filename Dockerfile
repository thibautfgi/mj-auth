FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Script de démarrage : convertit DATABASE_URL (format Render) en variables JDBC Spring
RUN printf '#!/bin/sh\n\
if [ -n "$DATABASE_URL" ]; then\n\
  # DATABASE_URL = postgresql://user:pass@host:port/db\n\
  export SPRING_DATASOURCE_URL="jdbc:$(echo $DATABASE_URL | sed '"'"'s|postgresql://[^@]*@|postgresql://|'"'"')"\n\
  DB_USERINFO=$(echo $DATABASE_URL | sed '"'"'s|postgresql://||'"'"' | cut -d@ -f1)\n\
  export SPRING_DATASOURCE_USERNAME=$(echo $DB_USERINFO | cut -d: -f1)\n\
  export SPRING_DATASOURCE_PASSWORD=$(echo $DB_USERINFO | cut -d: -f2)\n\
fi\n\
exec java -jar app.jar "$@"\n' > /app/start.sh && chmod +x /app/start.sh

EXPOSE 8081
ENTRYPOINT ["/app/start.sh"]


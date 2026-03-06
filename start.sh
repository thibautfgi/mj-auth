#!/bin/sh

# Convertit DATABASE_URL format Render (postgresql://user:pass@host:port/db)
# en variables JDBC lisibles par Spring Boot
if [ -n "$DATABASE_URL" ]; then
  # Supprime le préfixe postgresql://
  STRIPPED=$(echo "$DATABASE_URL" | sed 's|^postgresql://||')
  # Récupère user:pass (avant @)
  DB_USERINFO=$(echo "$STRIPPED" | cut -d@ -f1)
  # Récupère host:port/db (après @)
  DB_HOSTPART=$(echo "$STRIPPED" | cut -d@ -f2)

  export SPRING_DATASOURCE_USERNAME=$(echo "$DB_USERINFO" | cut -d: -f1)
  export SPRING_DATASOURCE_PASSWORD=$(echo "$DB_USERINFO" | cut -d: -f2)
  export SPRING_DATASOURCE_URL="jdbc:postgresql://${DB_HOSTPART}"
fi

# Render injecte PORT (généralement 10000), sinon on utilise 8081
APP_PORT=${PORT:-8081}

exec java -Dserver.port=$APP_PORT -jar app.jar "$@"


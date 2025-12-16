#!/bin/bash

API_KEY="changeme123"
URL_TO_TEST="https://google.com"
FRONTEND_URL="http://localhost:8081"

echo "🔄 1. Stopping and cleaning old Docker containers..."
docker-compose down -v --remove-orphans
docker system prune -af -f

echo "🔨 2. Building Docker images..."
docker-compose build --no-cache

echo "🚀 3. Starting Docker containers..."
docker-compose up -d

echo "⏳ 4. Waiting for backend to become healthy..."
until [ "$(docker inspect --format='{{.State.Health.Status}}' api-backend 2>/dev/null)" == "healthy" ]; do
  echo "   ... waiting for backend to be healthy ..."
  sleep 2
done

echo "✅ Backend is healthy!"

echo "🌐 5. Testing backend API directly..."
curl -s "http://localhost:8080/api/check?url=${URL_TO_TEST}" -H "X-API-KEY: ${API_KEY}"
echo -e "\n✅ Backend /check OK"

echo "📜 6. Fetching history from backend..."
curl -s "http://localhost:8080/api/history" -H "X-API-KEY: ${API_KEY}"
echo -e "\n✅ Backend /history OK"

echo "🧪 7. Testing via proxy (frontend)..."
curl -s "http://localhost:8081/api/check?url=${URL_TO_TEST}" -H "X-API-KEY: ${API_KEY}"
echo -e "\n✅ Proxy /check OK"

curl -s "http://localhost:8081/api/history" -H "X-API-KEY: ${API_KEY}"
echo -e "\n✅ Proxy /history OK"

echo "🌍 8. Opening frontend in browser..."
# Mac
open $FRONTEND_URL 2>/dev/null ||
# Linux
xdg-open $FRONTEND_URL 2>/dev/null ||
# Windows Git Bash
start $FRONTEND_URL 2>/dev/null

echo "🎉 All tests passed! Frontend is running at: $FRONTEND_URL"

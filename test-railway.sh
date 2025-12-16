#!/bin/bash

# ============================
# 🛠 KONFIGURERA DINA URL:ER:
# ============================

BACKEND_URL="https://api-watchdog.up.railway.app"
FRONTEND_URL="https://api-watchdog-frontend.up.railway.app"
API_KEY="changeme123"
TEST_URL="https://google.com"

# ============================
# 🔍 Testa backend direkt
# ============================

echo "🧪 Testar backend..."
RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}" "$BACKEND_URL/api/check?url=$TEST_URL" -H "X-API-KEY: $API_KEY")

BODY=$(echo "$RESPONSE" | sed '$d')
STATUS=$(echo "$RESPONSE" | tail -n1 | cut -d':' -f2)

if [ "$STATUS" == "200" ]; then
  echo "✅ Backend OK"
  echo "$BODY"
else
  echo "❌ Backend FAIL (status $STATUS)"
  exit 1
fi

# ============================
# 🔍 Testa frontend via proxy
# ============================

echo ""
echo "🧪 Testar frontend (proxy via /api)..."
FRONT_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}" "$FRONTEND_URL/api/check?url=$TEST_URL" -H "X-API-KEY: $API_KEY")

F_BODY=$(echo "$FRONT_RESPONSE" | sed '$d')
F_STATUS=$(echo "$FRONT_RESPONSE" | tail -n1 | cut -d':' -f2)

if [ "$F_STATUS" == "200" ]; then
  echo "✅ Frontend proxy OK"
  echo "$F_BODY"
else
  echo "❌ Frontend proxy FAIL (status $F_STATUS)"
  exit 1
fi

# ============================
# 🧪 Testar dashboard
# ============================

echo ""
echo "🌐 Testar att dashboard laddar..."
DASHBOARD_HTML=$(curl -s -L "$FRONTEND_URL")

if echo "$DASHBOARD_HTML" | grep -q "<title>API Watchdog Dashboard</title>"; then
  echo "✅ Dashboard HTML laddad!"
else
  echo "❌ Dashboard HTML saknas!"
  exit 1
fi

echo ""
echo "🎉 Alla tester klara. Ser ut att fungera!"

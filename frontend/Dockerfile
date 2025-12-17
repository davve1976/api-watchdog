# --- BUILD STAGE ---
FROM node:18 AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

ARG API_URL
ENV API_URL=${API_URL}

RUN npm run build

# --- RUNTIME STAGE ---
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Kopiera frontend-dist
COPY --from=build /app/dist .

# Kopiera och substituera Nginx template -> default.conf
COPY nginx.conf.template /etc/nginx/templates/default.conf.template
RUN apk add --no-cache gettext
CMD ["/bin/sh", "-c", "envsubst < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'"]

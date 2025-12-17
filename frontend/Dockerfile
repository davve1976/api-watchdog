FROM node:18-alpine as build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

ARG API_URL
ENV API_URL=$API_URL

RUN npm run build

# --- RUNTIME STAGE ---
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

COPY --from=build /app/dist .

# Sätt in config-template som substituerar ${API_URL}
COPY nginx.conf.template /etc/nginx/templates/default.conf.template

# Kör med envsubst via entrypoint
CMD ["/bin/sh", "-c", "envsubst < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]

# --- BUILD STAGE ---
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# --- RUNTIME STAGE ---
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy built frontend
COPY --from=build /app/dist .

# Copy template nginx config
COPY nginx.conf.template /etc/nginx/templates/default.conf.template

# Replace env variable and start nginx
CMD ["/bin/sh", "-c", "envsubst < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'"]

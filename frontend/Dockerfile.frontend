# Step 1: Build the frontend
FROM node:20 AS build

WORKDIR /app
COPY . .
RUN npm install && npm run build

# Step 2: NGINX serve
FROM nginx:stable-alpine

# Copy built frontend
COPY --from=build /app/dist /usr/share/nginx/html

# Copy the NGINX template
COPY nginx/templates/default.conf.template /etc/nginx/templates/default.conf.template

# Railway will substitute env vars into the template and start nginx
CMD ["nginx", "-g", "daemon off;"]

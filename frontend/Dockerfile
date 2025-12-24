FROM node:20 AS build
WORKDIR /app

COPY package*.json .
RUN npm install

COPY . .
RUN npm run build

FROM nginx:stable-alpine

COPY nginx/templates/default.conf.template /etc/nginx/templates/default.conf.template
COPY --from=build /app/dist /usr/share/nginx/html

CMD ["sh", "-c", "envsubst < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]

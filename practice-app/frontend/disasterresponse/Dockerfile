FROM node:14
WORKDIR /app
COPY package*.json ./
RUN npm install -g create-react-app
RUN npm install
COPY . .
CMD ["npm", "run", "build"]

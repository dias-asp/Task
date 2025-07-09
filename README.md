# Chat Application

This is a RESTful chat application that allows users to register, login, create chat rooms, and exchange messages with other users.

## Features

- User registration and authentication
- Public chat rooms
- Private messaging between users
- Message history

## Prerequisites

- Java 11 or higher
- PostgreSQL 12.3 or higher
- Docker and Docker Compose (for containerized deployment)

## Running the Application

### Running with Docker

1. Build the application:
   ```bash
   ./gradlew build
   ```

2. Start the application and database using Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. The application will be available at http://localhost:8080

## API Endpoints

### Authentication

- **POST /login** - Authenticate a user
  - Request body (x-www-form-urlencoded): `{ "login": "username", "password": "password" }`

### User Management

- **POST /users** - Register a new user
  - Request body: `{ "login": "username", "password": "password" }`

- **GET /users/current** - Get the currently authenticated user
  - Example: `GET /users/current`

- **GET /users** - Get all users
  - Example: `GET /users`

- **GET /users/{id}** - Get a user by ID
  - Example: `GET /users/1`

### Chat Rooms

- **POST /chat** - Create a new chat room
  - Request body: `{ "name": "room_name" }`

- **POST /chat/enter** - Enter an existing chat room
  - Request body: `{ "id": 1 }`

- **GET /chat** - Get all chat rooms for the current user
  - Example: `GET /chat`

### Messages

- **GET /chat/messages** - Get messages from a chat room
  - Request parameter: `chatRoomId`
  - Example: `GET /chat/messages?chatRoomId=1`

- **POST /chat/message** - Send a message to a chat room
  - Request body: `{ "text": "Hello!", "chatRoom": 1 }`
  - Example: `POST /chat/message` with body `{ "text": "Hello!", "chatRoom": 1 }`

- **GET /user/messages** - Get private messages between the current user and another user
  - Request parameter: `userId`
  - Example: `GET /user/messages?userId=2`

- **POST /user/message** - Send a private message to another user
  - Request body: `{ "text": "Hello!", "user": 2 }`

## Stopping the Application

### Stopping the Local Application

Press `Ctrl+C` in the terminal where the application is running.

### Stopping the Docker Containers

```bash
docker-compose down
```

To stop the application and remove all data (including the database volume):

```bash
docker-compose down -v
```
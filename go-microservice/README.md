# User Microservice

This User Microservice is a RESTful API developed with the Go programming language, utilizing the Gin framework for routing, GORM for object-relational mapping, and PostgreSQL as the database backend. It supports basic CRUD operations for managing user data.

### Prerequisites

- Go
- Docker

### Running the Service

To run the service with docker-compose
```shell
docker-compose up --build
```
The service will start and listen on port 8080.


To run the service without docker-compose
Firstly you need to have a postgres database running on your local machine.

```shell
docker run --name mypostgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=user_microservice -p 5432:5432 -d postgres
```

Then you can run the service with the following command
```shell
go run main.go
```

or with built binary for MacOS system

```shell
./user-microservice
```

The service will start and listen on port 8080.

### Build binary
You can build the binary for your operation system with the following command
```shell
go build -o user-microservice main.go
```

## API Endpoints

Below are the available API endpoints and example `curl` commands to interact with them.

### Create User

- **POST** `/save`

Creates a new user.


```shell
curl -X POST http://localhost:8080/save \
-d '{
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "name": "some name",
    "email": "email@email.com", 
    "date_of_birth": "2020-01-01T12:12:34+00:00"
}'
```

### Get User by ID

- **GET** `/:id`

Retrieves a user by their ID.


```shell
curl -X GET http://localhost:8080/1
```

### Update User

- **PUT** `/:id`

Updates an existing user.


```shell
curl -X PUT http://localhost:8080/1 \
-d '{
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "name": "some name",
    "email": "updatedemail@email.com", 
    "date_of_birth": "2020-01-01T12:12:34+00:00"
}'
```

### Delete User

- **DELETE** `/:id`

Deletes a user by their ID.


```shell
curl -X DELETE http://localhost:8080/1
```

### Get All Users

- **GET** `/`

Retrieves all users.


```shell
curl -X GET http://localhost:8080/
```

### Delete All Users

- **DELETE** `/`

Deletes all users.


```shell
curl -X DELETE http://localhost:8080/
```

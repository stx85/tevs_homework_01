## Build Project

To build the project stay in the root folder and type following:

```shell
./gradlew bootJar
```

All Projects will be built accordingly. \
Next start docker compose.

## Start Docker Compose

To start services, including kafka, use the following command:

```shell
# Building and Starting Services
docker compose up --build

# If already buildet, just start Services
docker compose up
```

## Check Eureka

http://localhost:8090/

## Test Network fail

docker network disconnect tevs_homework_01_tevs-net statusserver3

docker network connect tevs_homework_01_tevs-net statusserver3

## Start Frontend

```shell
cd frontend

npm install

ng serve
```

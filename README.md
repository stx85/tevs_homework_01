## Start Docker Compose

To start services, including kafka, use the following command:

```shell
# Building and Starting Services
docker compose up --build

# If already buildet, just start Services
docker compose up
```

## Zookeeper Installation

> 💡 Not necessary with docker compose

```shell
docker run -d \
 --name zookeeper \
 -p 2181:2181 \
 --network kafka-net \
 zookeeper
```

## KAFKA Installation

> 💡 Not necessary with docker compose

```shell
docker run -d --name kafka-server \
 --network kafka-net \
 --hostname kafka-server \
 -p 9092:9092 \
 -p 9093:9032 \
 -e ALLOW_PLAINTEXT_LISTENER=yes \
 -e KAFKA_CFG_BROKER_ID=1 \
 -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181 \
 -e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
 -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka-server:9092 \
 bitnami/kafka:3.4.0
```

## KAFKA UI Installation

> 💡 Not necessary with docker compose

```shell
docker run -d \
 --name kafka-ui \
 -p 8088:8080 \
 -e KAFKA_CLUSTERS_0_NAME=local \
 -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka-server:9092 \
 -e KAFKA_CLUSTERS_0_ZOOKEEPER=zookeeper:2181 \
 --network kafka-net \
 provectuslabs/kafka-ui:latest
```

## Hosts file changes

> 💡 Not necessary with docker compose

Open the file `C:\Windows\System32\drivers\etc\hosts` as administrator.

Add `127.0.0.1 kafka-server` at the end of the file.

Save and close.
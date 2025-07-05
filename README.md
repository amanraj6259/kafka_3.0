# kafka_3.0
Kafka pubsubmodel code

Apache Kafka -->Apache kafka is like communication system that helps different parts
of a computer system exchange data by publishing and subscribing to topics.

SENDER  -->(Publish) --->  APACHE KAFKA --->(Subscribe)--->RECEIVER

Consume by subscribing by receiver
Multiple receivers can be there

There is problems with Http protocol

Examples:
1)Ola driver location update
2)Zomato live food tracking
3)Notification System to huge user

                             ZOMATO

                         DELIEVERY BOY

USER                                          SERVER(DB)
(At each point                                If we send request
live track location                           to db to store live location
to user)     <--------------------            send user the location

Multiple users are there :-->eg 2 lakh users order , send live location update
database will read and hits will increase , throughput kam hota

Apache kafka -->user will subscribe , notifications will be send and after boy
deliever it to user it will store whole data in bulk to db

Database is used to store the data , we will use to query
Apache kafka ka throughput more , kam samay me kafi jyada data load





Download binary dowloads->scala
2) set class path -> environment variable

3)start kafka server
bin\windows\kafka-server-start.bat config\server.properties

4)start zookeeper 
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

5)Produce example message frm producer 

6)Create topics
D:\Kafka\kafka_2.13-3.9.0\bin\windows>kafka-topics.bat --create --topic user-topic --bootstrap-serverlocalhost:9092

7)to check the version

D:\Kafka\kafka_2.13-3.9.0\bin\windows>kafka-topics.bat --version

8) how to check topic created 
D:\Kafka\kafka_2.13-3.9.0\bin\windows>kafka-topics.bat --list --bootstrap-server localhost:9092

-->o/p=user-topic


9)create kafka producer

D:\Kafka\kafka_2.13-3.9.0\bin\windows>kafka-console-producer.bat --topic user-topic --bootstrap-server localhost:9092
>hey
>this is my message
>this is kafka
>messages are produces

10) to read the events 
we will use kafka console consumer

D:\Kafka\kafka_2.13-3.9.0\bin\windows>kafka-console-consumer.bat --topic user-topic --from-beginning --bootstrap-server localhost:9092
hey
this is my message
this is kafka
messages are produces


                                                                # kafka-ui using docker

wsl --update

docker pull wurstmeister/kafka

IntelliJ: install kafka-plugin

Save that YAML to docker-compose.yml.

Start both Kafka + Zookeeper:

docker-compose up


docker-compose up -d

docker ps is a command that lists all the running containers on your system.

It’s like asking:

“Hey Docker, what containers are currently up and running?”



✅ **Absolutely! Let’s break this down carefully so you understand what each part and property means in your `docker-compose.yml`.**

---

## 🚀 **Full `docker-compose.yml`**

```yaml
version: '3.1'

services:
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"

  kafka:
    image: wurstmeister/kafka
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181

  kafka-ui:
    image: provectuslabs/kafka-ui
    container_name: kafka-ui
    ports:
      - "8080:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092
    depends_on:
      - kafka
```

---

## 🌟 **Explanation of properties**

---

### ✅ `version: '3.1'`

Specifies the Compose file format version.

* `3.1` supports features like volumes, networks, depends\_on, etc.
* Works with Docker Engine 1.13+.

---

## 🟣 `services`

Defines the containers to run as part of your app.

---

### 🔹 **Service: zookeeper**

| Property                        | Meaning                                                                                                                                                          |
| ------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `image: wurstmeister/zookeeper` | Pulls and runs the `wurstmeister/zookeeper` image from Docker Hub.                                                                                               |
| `container_name: zookeeper`     | Gives this container the fixed name `zookeeper` (instead of auto-generated name).                                                                                |
| `ports: - "2181:2181"`          | Maps port 2181 of your machine (host) to port 2181 inside the container. <br>➡ 2181 is Zookeeper's client port (Kafka and tools use it to connect to Zookeeper). |

---

### 🔹 **Service: kafka**

| Property                    | Meaning                                                                                                                                 |
| --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| `image: wurstmeister/kafka` | Runs Kafka using the `wurstmeister/kafka` image.                                                                                        |
| `container_name: kafka`     | Names the container `kafka`.                                                                                                            |
| `ports: - "9092:9092"`      | Maps port 9092 on your machine to 9092 inside container (Kafka broker port). <br>➡ Kafka clients/producers/consumers will connect here. |
| `environment:`              | Provides environment variables that configure Kafka.                                                                                    |

➡ Inside `environment`:

| Variable                                  | Meaning                                                                                                |
| ----------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| `KAFKA_ADVERTISED_HOST_NAME: kafka`       | Kafka tells clients to connect using `kafka:9092`. <br>⚠ This is the internal Docker network hostname. |
| `KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181` | Tells Kafka where Zookeeper lives: container `zookeeper`, port `2181`.                                 |

---

### 🔹 **Service: kafka-ui**

| Property                        | Meaning                                                                                                            |
| ------------------------------- | ------------------------------------------------------------------------------------------------------------------ |
| `image: provectuslabs/kafka-ui` | Runs the Kafka UI from `provectuslabs/kafka-ui` image (Docker Hub).                                                |
| `container_name: kafka-ui`      | Names the container `kafka-ui`.                                                                                    |
| `ports: - "8080:8080"`          | Exposes port 8080 on your machine → port 8080 inside the container. <br>➡ Open in browser: `http://localhost:8080` |
| `environment:`                  | Configures the Kafka UI through env vars.                                                                          |
| `depends_on: - kafka`           | Ensures Kafka starts before Kafka UI starts.                                                                       |

➡ Inside `environment`:

| Variable                                        | Meaning                                                    |
| ----------------------------------------------- | ---------------------------------------------------------- |
| `KAFKA_CLUSTERS_0_NAME: local`                  | In the UI, your cluster will be labeled `local`.           |
| `KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092` | Kafka UI will connect to the Kafka broker at `kafka:9092`. |

---

## ⚡ **How it works**

* 🟣 **Zookeeper** starts → listens on `2181` → used by Kafka.
* 🟣 **Kafka** starts → connects to Zookeeper → listens on `9092`.
* 🟣 **Kafka UI** starts → connects to Kafka at `kafka:9092` → exposes `8080` for your browser.

---

## 🌐 **How to access the UI**

Once you run:

```bash
docker-compose up
```

➡ Open your browser:

```
http://localhost:8080
```

✅ You’ll see Kafka UI → can view topics, partitions, consumers, messages!

---

## ⚠ Important notes

* If `8080` is already used → change `8080:8080` to `8081:8080` or another free port.
* Inside Docker network, services refer to each other by their `container_name` or `service name` (here both are the same).

---

## 💡 Summary of key ports

| Port | Used by   | Purpose                                 |
| ---- | --------- | --------------------------------------- |
| 2181 | Zookeeper | Kafka uses this to connect to Zookeeper |
| 9092 | Kafka     | Producers/consumers connect to Kafka    |
| 8080 | Kafka UI  | Web UI for you to see topics etc        |

---

If you’d like, I can:
✅ Generate an annotated `docker-compose.yml`
✅ Show how to produce/consume messages
✅ Help you create a topic or send test messages through the UI

Just say the word! 🚀

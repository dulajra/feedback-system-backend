# Feedback Service
Sprint Boot Service for [Feedback System Frontend](https://github.com/dulajra/feedback-system-frontend). 

## Developer Guide 
### How to run on local machine
#### Prerequisite
1. Java 8 or higher
2. Docker
3. Docker Compose

Start the MySQL database container.

```
docker-compose -f CICD/docker/docker-compose.yml up -d feedback-db
```

Set the following environment variables on the host machine. 
If you want to point to a remote database, make sure to update the values accordingly.

```
export DB_HOST=localhost
export DB_NAME=FEEDBACK
export DB_USERNAME=root
export DB_PASSWORD=password
```

Build and run the service.

```
./gradlew build -x test
./gradlew bootRun
```

### How to run with docker 
#### Prerequisite
1. Docker
2. Docker Compose

Set the following environment variables on the host machine.

```
export DB_HOST=feedback-db
export DB_NAME=FEEDBACK
export DB_USERNAME=root
export DB_PASSWORD=password
```

Start both database container and service container.

```
docker-compose -f CICD/docker/docker-compose.yml up --build
```

Use the following command if you want to run in background.
 
```
docker-compose -f CICD/docker/docker-compose.yml up --build -d
```

Use the following command if you want to stop the containers running in the background mode.

```
docker-compose -f CICD/docker/docker-compose.yml down
```

### How to access
You can access the API on [http://localhsot:8080](http://localhsot:8080)

You can access the Swagger console on [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) for testing purposes. 

### Tests and Sonar
#### Run unit tests

```
./gradlew test
```

#### Run Sonar analysis
Start a local temporary sonar community server

```
docker run -p 9000:9000 sonarqube:7.9-community
```

Perform Sonar analysis

```
./gradlew sonarqube
```

Run sonar pointing to a remote server.
Set correct values for `SONAR_HOST` and `SONAR_TOKEN` environment variables before running the command.

```
./gradlew sonarqube -Dsonar.host.url=$SONAR_HOST -Dsonar.login=$SONAR_TOKEN
```

### How to run streaming server logs to Minio
I have created a separate docker setup to stream application logs to Minio.

Here I use the fluentd log driver as the docker log driver for application server container.
A fluentd container is also running as a side-car along with the server container. 
Server sends server logs to fluentd and fluentd buffers and sends those logs to Minio. 

Run the following command to start the server with Fluentd and Minio

```
docker-compose -f CICD/docker/docker-compose-fluentd.yml up --build
```

You can view the server logs in Minio Dashboard at `http://localhost:9000/`

Please note that in `CICD/docker/fluentd/fluent.conf` I have set `timekey` as `1 minute` and `chunk_limit_size` as `1 MB` 
to see the logs quickly in Minio. In real cases these values should be higher than this. 

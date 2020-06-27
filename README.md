# Feedback Service
Sprint Boot Service for Feedback Frontend. 

## Prerequisite
1. Java 8 or higher

## Developer Guide 
### How to run on local machine
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

Run the service.

```
./gradlew bootRun
```

### How to run with docker 
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

Use the following command if you want to stop the containers running in the background.

```
docker-compose -f CICD/docker/docker-compose.yml down
```

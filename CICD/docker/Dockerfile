FROM openjdk:8-jdk-alpine3.9 as builder

WORKDIR /app

COPY gradle ./gradle
COPY src ./src
COPY build.gradle .
COPY gradlew .
COPY settings.gradle .

RUN ./gradlew build -x test

FROM openjdk:8-jre-alpine3.9

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
COPY CICD/docker/wait-for.sh .

RUN chmod +x wait-for.sh

CMD echo "Waiting until DB is ready" && sh wait-for.sh $DB_HOST:3306 -t 300 -- java -jar app.jar

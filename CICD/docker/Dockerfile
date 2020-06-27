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

CMD java -jar app.jar

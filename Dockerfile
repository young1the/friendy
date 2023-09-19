FROM gradle:jdk17-alpine as builder
WORKDIR /build

COPY build.gradle /build/
COPY  settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN gradle build -x test --parallel

FROM openjdk:17-jdk-alpine as runner
COPY --from=builder /build/build/libs/friendy-0.0.1-SNAPSHOT.jar app.jar

RUN pwd
EXPOSE 8080

ENTRYPOINT ["java","-jar","-DAWS_DB_URL=${AWS_DB_URL}","-DAWS_DB_USERNAME=${AWS_DB_USERNAME}","-DAWS_DB_PASSWORD=${AWS_DB_PASSWORD}","-DWEATHER_API_KEY=${WEATHER_API_KEY}","/app.jar"]

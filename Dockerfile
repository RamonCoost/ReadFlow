FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle

RUN chmod +x gradlew

COPY src ./src

RUN ./gradlew clean bootJar

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/ReadFlow-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT ["java","-jar","ReadFlow-0.0.1-SNAPSHOT.jar"]
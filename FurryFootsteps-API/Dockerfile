FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM openjdk:17-oracle AS final
WORKDIR /app
COPY --from=build /app/target/FurryFootsteps-API-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "FurryFootsteps-API-0.0.1-SNAPSHOT.jar"]

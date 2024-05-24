FROM maven:3.8.7 as build

COPY . .

RUN mvn -B clean package -DskipTests

FROM openjdk:17

COPY --from=build target/*.jar RecyclPalApp_Backend.jar

EXPOSE 8080

# COPY target/RecyclPalApp_Backend ../app/
ENTRYPOINT ["java", "-jar", "RecyclPalApp_Backend.jar"]



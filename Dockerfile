FROM openjdk:17-oracle
COPY ./build/libs/*.jar currency-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/currency-app.jar"]

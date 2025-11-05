FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/itx-backend-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-default} -jar app.jar"]
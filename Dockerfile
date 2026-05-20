# etapa 1: build gera o arquivo .jar referente ao projeto em questão
FROM maven:3.9.15-amazoncorretto-21-al2023 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# etapa 2: Roda a aplicação 
FROM amazoncorretto:21-al2023 AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


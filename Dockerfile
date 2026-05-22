#Stage 1: dependencias
FROM eclipse-temurin:25-jdk AS dependencies
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

#Stage 2: build
FROM dependencies AS builder
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

#Stage 3: Runtime
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
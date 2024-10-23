# Bygger applikationen med JDK 21
FROM eclipse-temurin:21-jdk-jammy as builder

# Ställer in arbetskatalog
WORKDIR /opt/app

# Kopiera Maven-relaterade filer
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Ge körbehörighet till mvnw
RUN chmod +x mvnw

# Hämta beroenden
RUN ./mvnw dependency:go-offline

# Kopiera källkoden
COPY ./src ./src

# Bygg applikationen
RUN ./mvnw clean install -DskipTests

# Skapa det slutgiltiga bilden
FROM eclipse-temurin:21-jre-jammy

# Ställer in arbetskatalog
WORKDIR /opt/app

# Exponera porten som applikationen kommer att lyssna på
EXPOSE 8080

# Kopiera den byggda JAR-filen till den slutgiltiga bilden
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar

# Sätta upp applikationen för att köras
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar", "/opt/app/*.jar"]
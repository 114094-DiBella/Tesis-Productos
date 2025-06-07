# Dockerfile para Product Services
FROM openjdk:17-jdk-slim

# Instalar herramientas necesarias
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de Maven wrapper desde productservices/
COPY .mvn/ .mvn/
COPY mvnw /pom.xml ./

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargar dependencias (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY productservices/src/ src/

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Crear directorio para imágenes de productos
RUN mkdir -p /app/product-images

# Exponer puerto
EXPOSE 8083

# Crear usuario no-root para seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring
RUN chown -R spring:spring /app
USER spring:spring

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/productservices-0.0.1-SNAPSHOT.jar"]
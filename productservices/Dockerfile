# Dockerfile para Product Services
FROM openjdk:17-jdk-slim

# Instalar herramientas necesarias
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Establecer variables de entorno para codificación
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
ENV JAVA_OPTS="-Dfile.encoding=UTF-8 -Dproject.build.sourceEncoding=UTF-8"

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de Maven wrapper
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargar dependencias (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src/ ./src/

# Construir la aplicación con codificación específica
RUN ./mvnw clean package -DskipTests \
    -Dproject.build.sourceEncoding=UTF-8 \
    -Dproject.reporting.outputEncoding=UTF-8 \
    -Dmaven.compiler.encoding=UTF-8

# Crear directorio para imágenes de productos
RUN mkdir -p /app/product-images

# Exponer puerto
EXPOSE 8080

# Crear usuario no-root para seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring
RUN chown -R spring:spring /app
USER spring:spring

# Comando para ejecutar la aplicación
CMD ["java", "-Dfile.encoding=UTF-8", "-jar", "target/productservices-0.0.1-SNAPSHOT.jar"]
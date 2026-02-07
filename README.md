# Ejercicio Uno - Kafka con Spring Boot

Proyecto de ejemplo que muestra cómo publicar y consumir mensajes en Kafka
usando Spring Boot y Spring Cloud Stream.

## Qué hace este proyecto

- Exponer el endpoint `POST /api/mensajes` para publicar mensajes en Kafka.
- Publicar mensajes mediante el binding `mensajes-output`.
- Proveer un listener condicional (`kafka.listener.enabled`) que consume
  mensajes del binding `mensajesInput`.

## Requisitos

- JDK 17+ (recomendado JDK 21)
- Maven 3.6+
- Kafka (broker) en `localhost:9092` o según configuración

## Cómo ejecutar

Compilar y ejecutar con Maven:

```bash
./mvnw -DskipTests package
./mvnw spring-boot:run
```

## Configuración (archivo `application.yaml`)

Archivo: [src/main/resources/application.yaml](src/main/resources/application.yaml)

Propiedades clave:

- `spring.cloud.stream.kafka.binder.brokers`: brokers Kafka, p.ej. `localhost:9092`.
- `spring.cloud.stream.bindings.mensajes-output.destination`: topic de salida.
- `spring.cloud.stream.bindings.mensajesInput-in-0.destination`: topic de entrada.
- `spring.cloud.stream.bindings.mensajesInput-in-0.consumer.autoOffsetReset`: `earliest` o `latest`.
- `kafka.listener.enabled`: `true|false` para activar el listener de consumo.

Ejemplo mínimo (application.yaml):

```yaml
spring:
  application:
    name: ejercicio-uno
spring:
  cloud:
    stream:
      kafka:
        binder:
          brokers: localhost:9092
      bindings:
        mensajes-output:
          destination: mensajes
        mensajesInput-in-0:
          destination: mensajes
          consumer:
            autoOffsetReset: earliest

kafka:
  listener:
    enabled: true
```

Notas:

- Para desactivar el consumidor ponga `kafka.listener.enabled: false`.
- Para entornos con autenticación/SSL, añada la configuración del binder Kafka.

## Ejemplos prácticos

Enviar un mensaje:

```bash
curl -X POST http://localhost:8080/api/mensajes \
  -H "Content-Type: application/json" \
  -d '{"asunto":"hola","cuerpo":"mensaje de prueba"}'
```

Ver mensajes con consola Kafka:

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic mensajes --from-beginning
```

## Generar Javadoc

```bash
./mvnw javadoc:javadoc -pl :kafka-example-springboot
```

El Javadoc se generará en `target/site/apidocs`.

## Docker / Compose

Se recomienda usar `docker-compose` para levantar un Kafka local en desarrollo.
Incluye ejemplo en `docker-compose.kafka.yml`.

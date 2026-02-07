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
  # Nombre de la aplicación (útil en logs y herramientas de observabilidad)
  application:
    name: ejercicio-uno

  # Spring Cloud Function / Stream: define funciones y bindings usados
  cloud:
    # Nombre de la función definida en el contexto de Spring Cloud Function
    # En este proyecto la función/bean que recibe mensajes se llama `mensajesInput`
    function:
      definition: mensajesInput

    # Configuración de Spring Cloud Stream (binder Kafka)
    stream:
      kafka:
        binder:
          # Brokers Kafka a los que conectar (host:puerto). Puede reemplazarse
          # por una variable de entorno en entornos distintos a desarrollo.
          brokers: localhost:9092
          # Si es true, el binder intentará crear topics automáticamente al usar
          # bindings. Útil en desarrollo, evitar en producción si la gestión
          # de topics está controlada por la plataforma.
          autoCreateTopics: true

      # Bindings: mapeo entre canales de Spring Cloud Stream y topics Kafka
      bindings:
        # Binding de salida: publica mensajes en el topic `mensajes`
        mensajes-output: # El nombre del binding de salida (coincide con el usado en StreamBridge)
          destination: mensajes # El topic Kafka al que se publicará
          producer:
            # Número de particiones que el binder solicitará al crear el topic
            partitionCount: 1

        # Binding de entrada: función que consume del topic `mensajes`
        # La notación `mensajesInput-in-0` es el binding generado por la
        # definición de función `mensajesInput` (entrada 0).
        mensajesInput-in-0:
          destination: mensajes # El topic Kafka del que se consumirá
          consumer:
            # Número de particiones que se espera en el topic (coincidir con producer)
            partitionCount: 1
            # Offset reset: `earliest` para leer desde inicio o `latest` para solo nuevos
            autoOffsetReset: earliest
kafka:
  listener:
    # Habilita o deshabilita el listener/consumer definido en la aplicación.
    # Si es false la aplicación seguirá publicando mensajes, pero no consumirá.
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

## OpenAPI / Swagger UI

Este proyecto incluye `springdoc-openapi` y expone la documentación OpenAPI
automáticamente al arrancar la aplicación.

- Interfaz Swagger UI (navegador): http://localhost:8080/swagger-ui.html
- OpenAPI JSON (máquina / herramientas): http://localhost:8080/v3/api-docs

Si la aplicación se ejecuta en otro puerto (p. ej. `server.port`), sustituye
`8080` por el puerto correspondiente.

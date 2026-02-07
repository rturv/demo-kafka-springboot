package com.eventdriver.ejercicios.ejerciciouno.listener;

import com.eventdriver.ejercicios.ejerciciouno.model.MensajeRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

@Component
@ConditionalOnProperty(name = "kafka.listener.enabled", havingValue = "true")
public class MensajeListener {

    /**
     * Componente que define el bean consumidor `mensajesInput` para recibir
     * mensajes del topic configurado. Se crea solo si la propiedad
     * `kafka.listener.enabled` está a true.
     */

    private static final Logger logger = LoggerFactory.getLogger(MensajeListener.class);

    @Bean
    public Consumer<MensajeRequest> mensajesInput() {
        logger.info("Bean mensajesInput creado y listo para consumir");  // Log adicional para confirmar creación
        return mensaje -> {
            System.out.println("Mensaje recibido - Asunto: " + mensaje.getAsunto() + ", Cuerpo: " + mensaje.getCuerpo());  // Print temporal para consola
            logger.info("Mensaje recibido - Asunto: {}, Cuerpo: {}", mensaje.getAsunto(), mensaje.getCuerpo());
        };
    }
}
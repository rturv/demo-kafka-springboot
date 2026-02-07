package com.eventdriver.ejercicios.ejerciciouno.service;

import com.eventdriver.ejercicios.ejerciciouno.model.MensajeRequest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
/**
 * Servicio encargado de publicar mensajes usando Spring Cloud Stream
 * (StreamBridge) hacia el binding `mensajes-output`.
 * <p>
 * Devuelve un identificador (UUID) que representa el ID del mensaje
 * para propósitos de trazabilidad en la API REST.
 * </p>
 */
public class MensajeProducerService {

    // Nombre del binding de salida definido en application.yaml
    public static final String BINDING_NAME = "mensajes-output";

    private final StreamBridge streamBridge;

    public MensajeProducerService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public String sendMensaje(MensajeRequest mensaje) {
        // Enviar el mensaje al binding mensajes-output
        boolean sent = streamBridge.send(BINDING_NAME, mensaje);
        if (sent) {
            // Devolver un UUID como messageId, ya que obtener el offset real requiere configuración adicional
            return java.util.UUID.randomUUID().toString();
        } else {
            throw new RuntimeException("Failed to send message");
        }
    }
}
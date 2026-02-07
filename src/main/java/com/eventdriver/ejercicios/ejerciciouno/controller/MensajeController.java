package com.eventdriver.ejercicios.ejerciciouno.controller;

import com.eventdriver.ejercicios.ejerciciouno.model.MensajeRequest;
import com.eventdriver.ejercicios.ejerciciouno.service.MensajeProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    /**
     * Controlador REST que expone el endpoint para enviar mensajes al topic
     * configurado. Recibe objetos {@link com.eventdriver.ejercicios.ejerciciouno.model.MensajeRequest}
     * y delega el envío a {@link com.eventdriver.ejercicios.ejerciciouno.service.MensajeProducerService}.
     */

    private final MensajeProducerService producerService;

    public MensajeController(MensajeProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> enviarMensaje(@RequestBody MensajeRequest mensaje) {
        String messageId = producerService.sendMensaje(mensaje);
        return ResponseEntity.ok(Map.of("messageId", messageId));
    }
}
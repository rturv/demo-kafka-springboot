package com.eventdriver.ejercicios.ejerciciouno.model;

public class MensajeRequest {

    private String asunto;

    private String cuerpo;

    /**
     * Clase DTO que representa la carga de un mensaje entrante desde la API REST.
     * Contiene campos simples: `asunto` y `cuerpo`.
     */

    // getters and setters

    public String getAsunto() {

        return asunto;

    }

    public void setAsunto(String asunto) {

        this.asunto = asunto;

    }

    public String getCuerpo() {

        return cuerpo;

    }

    public void setCuerpo(String cuerpo) {

        this.cuerpo = cuerpo;

    }

}
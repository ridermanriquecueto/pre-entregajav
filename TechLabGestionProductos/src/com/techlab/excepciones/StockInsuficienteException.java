package com.techlab.excepciones;

// Excepción personalizada para manejar casos de stock insuficiente
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) {
        super(mensaje); // Llama al constructor de la clase Exception con el mensaje
    }
}
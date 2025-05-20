package com.techlab.excepciones;

import com.techlab.ColorConsole; // Importar la clase ColorConsole

public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) {
        super(ColorConsole.RED + ColorConsole.BOLD + "ERROR DE STOCK: " + mensaje + ColorConsole.RESET); // Mensaje de error en rojo y negrita
    }
}
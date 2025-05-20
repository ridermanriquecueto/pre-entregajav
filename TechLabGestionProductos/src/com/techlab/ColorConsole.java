package com.techlab;

public class ColorConsole {

    // Códigos para resetear el color a la normalidad
    public static final String RESET = "\u001B[0m";

    // Colores de Texto
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Fondos de Texto
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    // Otros estilos
    public static final String BOLD = "\u001B[1m";
    public static final String FAINT = "\u001B[2m"; // Poco brillo
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[5m"; // Parpadeo (no siempre soportado)
    public static final String REVERSED = "\u001B[7m"; // Invierte color de texto y fondo
    public static final String CONCEAL = "\u001B[8m"; // Oculta el texto
    public static final String CROSSED_OUT = "\u001B[9m"; // Tachado (no siempre soportado)
}
package com.techlab.productos;

import com.techlab.ColorConsole; // Importar la clase ColorConsole

public class Producto {
    private static int contadorId = 0;
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = ++contadorId; // ID autogenerado
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        // Validación básica para stock no negativo
        if (stock >= 0) {
            this.stock = stock;
        } else {
            System.err.println(ColorConsole.RED + "Advertencia: El stock no puede ser negativo. Se mantuvo el valor anterior." + ColorConsole.RESET);
        }
    }

    @Override
    public String toString() {
        // Formato para listar un producto individualmente
        return String.format("%sID: %s%-4d%s | %sNombre: %s%-20s%s | %sPrecio: %s$%-9.2f%s | %sStock: %s%-5d%s",
            ColorConsole.CYAN, ColorConsole.WHITE, id, ColorConsole.CYAN,
            ColorConsole.CYAN, ColorConsole.WHITE, nombre, ColorConsole.CYAN,
            ColorConsole.CYAN, ColorConsole.YELLOW, precio, ColorConsole.CYAN,
            ColorConsole.CYAN, (stock <= 5 && stock > 0 ? ColorConsole.YELLOW : (stock == 0 ? ColorConsole.RED : ColorConsole.GREEN)), stock, ColorConsole.RESET);
            // El stock cambia de color según la cantidad (verde, amarillo, rojo)
    }
}
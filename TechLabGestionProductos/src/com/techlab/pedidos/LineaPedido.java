package com.techlab.pedidos;

import com.techlab.productos.Producto;
import com.techlab.ColorConsole;

public class LineaPedido {
    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    // ********* NUEVO MÉTODO AÑADIDO PARA LA CORRECCIÓN *********
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    // *********************************************************

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        // Alineación y colores para cada línea de pedido
        return String.format("%s%-25s%s x%s%-3d%s = %s$%-9.2f%s",
            ColorConsole.WHITE, producto.getNombre(),
            ColorConsole.YELLOW, cantidad,
            ColorConsole.WHITE, ColorConsole.GREEN, getSubtotal(), ColorConsole.RESET);
    }
}
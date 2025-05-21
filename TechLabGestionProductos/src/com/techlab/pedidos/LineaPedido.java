package com.techlab.pedidos;

import com.techlab.productos.Producto;

public class LineaPedido {
    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters
    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    // Setter para la cantidad (útil si se ajusta una línea de pedido)
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Calcula el subtotal de esta línea de pedido
    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        // Formato para mostrar una línea de pedido
        return "  - " + producto.getNombre() + " (x" + cantidad + ") - Subtotal: $" + String.format("%.2f", calcularSubtotal());
    }
}
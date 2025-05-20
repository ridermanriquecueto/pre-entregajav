package com.techlab.pedidos;

import com.techlab.productos.Producto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Asegúrate de tener la clase ColorConsole en src/com/techlab/ColorConsole.java
import com.techlab.ColorConsole;

public class Pedido {
    private static int nextId = 1;
    private int id;
    private LocalDateTime fechaCreacion;
    private List<LineaPedido> lineas;

    public Pedido() {
        this.id = nextId++;
        this.fechaCreacion = LocalDateTime.now();
        this.lineas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public void agregarLinea(LineaPedido linea) {
        // Lógica para añadir o fusionar líneas de pedido si el producto ya existe
        boolean found = false;
        for (LineaPedido existingLine : lineas) {
            if (existingLine.getProducto().getId() == linea.getProducto().getId()) {
                existingLine.setCantidad(existingLine.getCantidad() + linea.getCantidad());
                found = true;
                break;
            }
        }
        if (!found) {
            this.lineas.add(linea);
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        sb.append(ColorConsole.BLUE + ColorConsole.BOLD + "═════════════════════════════════════════════════════════════════\n");
        sb.append(String.format("%sPEDIDO ID: %-5d | FECHA: %s%-20s | TOTAL: %s$%-10.2f%s\n",
                ColorConsole.CYAN, id,
                ColorConsole.WHITE, fechaCreacion.format(formatter),
                ColorConsole.GREEN, calcularTotal(),
                ColorConsole.BLUE));
        sb.append("╠═════════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("%s║ %-3s ║ %-25s ║ %-8s ║ %-8s ║ %-10s %s║\n",
                ColorConsole.CYAN, "ID", "Producto", "Cant.", "Precio", "Subtotal", ColorConsole.BLUE));
        sb.append("╠═════╬═════════════════════════╬══════════╬══════════╬════════════╣\n");

        for (LineaPedido lp : lineas) {
            sb.append(String.format("%s║ %-3d %s║ %s%-25s %s║ %s%-8d %s║ %s$%-7.2f %s║ %s$%-9.2f %s║\n",
                    ColorConsole.WHITE, lp.getProducto().getId(), ColorConsole.CYAN,
                    ColorConsole.WHITE, lp.getProducto().getNombre(), ColorConsole.CYAN,
                    ColorConsole.WHITE, lp.getCantidad(), ColorConsole.CYAN,
                    ColorConsole.YELLOW, lp.getProducto().getPrecio(), ColorConsole.CYAN,
                    ColorConsole.GREEN, lp.getSubtotal(), ColorConsole.BLUE));
        }
        sb.append("╚═════════════════════════════════════════════════════════════════╝" + ColorConsole.RESET);
        return sb.toString();
    }
}
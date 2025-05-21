package com.techlab.pedidos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale; 

public class Pedido {
    private static int nextId = 1; 
    private int id;
    private LocalDateTime fechaHora;
    private List<LineaPedido> lineas; 

    public Pedido() {
        this.id = nextId++; 
        this.fechaHora = LocalDateTime.now(); 
        this.lineas = new ArrayList<>();
    }

 
    public int getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public List<LineaPedido> getLineas() {
        
        return Collections.unmodifiableList(lineas);
    }

  
    public void agregarLinea(LineaPedido linea) {
        this.lineas.add(linea);
    }

 
    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.calcularSubtotal(); 
        }
        return total;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------------------\n");
        sb.append(String.format("PEDIDO #%d - Fecha: %s\n", id, fechaHora.format(formatter)));
        sb.append("---------------------------------------------------\n");
        sb.append(String.format("%-25s %-8s %-10s %-10s%n", "Producto", "Cant.", "P.Unit.", "Subtotal"));
        sb.append("---------------------------------------------------\n");
        for (LineaPedido lp : lineas) {
            // Usa Locale.US para el formato de los precios dentro del pedido
            sb.append(String.format(Locale.US, "%-25s %-8d $%-9.2f $%-9.2f%n",
                    lp.getProducto().getNombre(),
                    lp.getCantidad(),
                    lp.getProducto().getPrecio(),
                    lp.calcularSubtotal()));
        }
        sb.append("---------------------------------------------------\n");
        sb.append(String.format(Locale.US, "%-44s $%.2f%n", "TOTAL DEL PEDIDO:", calcularTotal()));
        sb.append("---------------------------------------------------");
        return sb.toString();
    }
}
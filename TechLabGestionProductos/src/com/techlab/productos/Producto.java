package com.techlab.productos;

public class Producto {
    private static int nextId = 1; 
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = nextId++; 
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    
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

   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
       
        return "ID: " + id + ", Nombre: " + nombre + ", Precio: $" + String.format("%.2f", precio) + ", Stock: " + stock;
    }
}
package com.techlab;

import com.techlab.productos.Producto;
import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.LineaPedido;
import com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;

public class Main {
    private static List<Producto> productos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    private static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
        }
    }

    public static void main(String[] args) {
        int opcion;
        do {
            clearConsole();
            mostrarMenu();
            opcion = leerEntero("Elija una opción: ");
            System.out.println();

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    buscarActualizarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    crearPedido();
                    break;
                case 6:
                    listarPedidos();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema de gestión TechLab. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
            if (opcion != 7) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        } while (opcion != 7);
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("----- SISTEMA DE GESTIÓN - TECHLAB -----");
        System.out.println("----------------------------------------");
        System.out.println("1) AGREGAR PRODUCTO");
        System.out.println("2) LISTAR PRODUCTOS");
        System.out.println("3) BUSCAR/ACTUALIZAR PRODUCTO");
        System.out.println("4) ELIMINAR PRODUCTO");
        System.out.println("5) CREAR UN PEDIDO");
        System.out.println("6) LISTAR PEDIDOS");
        System.out.println("----------------------------------------");
        System.out.println("7) SALIR");
        System.out.println("----------------------------------------");
    }

    private static void agregarProducto() {
        System.out.println("\n--- AGREGAR NUEVO PRODUCTO ---");
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println("El nombre del producto no puede estar vacío.");
            return;
        }

        double precio = leerDouble("Ingrese el precio (ej. 29.99): ");
        if (precio <= 0) {
            System.out.println("El precio debe ser un valor positivo.");
            return;
        }

        int stock = leerEntero("Ingrese la cantidad en stock: ");
        if (stock < 0) {
            System.out.println("La cantidad en stock no puede ser negativa.");
            return;
        }

        Producto nuevoProducto = new Producto(nombre, precio, stock);
        productos.add(nuevoProducto);
        System.out.println("Producto '" + nuevoProducto.getNombre() + "' (ID: " + nuevoProducto.getId() + ") agregado exitosamente.");
    }

    private static void listarProductos() {
        System.out.println("\n--- LISTADO DE PRODUCTOS DISPONIBLES ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados para mostrar. ¡Agregue algunos primero!");
            return;
        }
        System.out.printf("%-5s %-25s %-12s %-8s%n", "ID", "NOMBRE", "PRECIO", "STOCK");
        System.out.println("----- ------------------------- ------------ --------");
        for (Producto p : productos) {
            System.out.printf(Locale.US, "%-5d %-25s $%-11.2f %-8d%n",
                p.getId(), p.getNombre(), p.getPrecio(), p.getStock()
            );
        }
        System.out.println("----- ------------------------- ------------ --------");
    }

    private static void buscarActualizarProducto() {
        System.out.println("\n--- BUSCAR/ACTUALIZAR PRODUCTO ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados para buscar. Agregue algunos primero.");
            return;
        }

        listarProductos();

        int id = leerEntero("Ingrese el ID del producto a buscar: ");
        Producto producto = buscarProductoPorId(id);
        if (producto == null) {
            System.out.println("Producto con ID " + id + " no encontrado.");
            return;
        }

        System.out.println("Producto encontrado: " + producto.toString());
        System.out.print("¿Desea actualizar el producto? (s/n): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            System.out.println("\n--- ACTUALIZANDO PRODUCTO ---");
            System.out.print("Ingrese el nuevo nombre (actual: " + producto.getNombre() + ", dejar vacío para no cambiar): ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.trim().isEmpty()) {
                producto.setNombre(nuevoNombre.trim());
            }

            double nuevoPrecio = leerDouble("Ingrese el nuevo precio (actual: " + producto.getPrecio() + ", ingrese 0 o negativo para no cambiar): ");
            if (nuevoPrecio > 0) {
                producto.setPrecio(nuevoPrecio);
            } else {
                System.out.println("Precio inválido (debe ser > 0). Se mantuvo el precio anterior.");
            }

            int nuevoStock = leerEntero("Ingrese el nuevo stock (actual: " + producto.getStock() + ", ingrese negativo para no cambiar): ");
            if (nuevoStock >= 0) {
                producto.setStock(nuevoStock);
            } else {
                System.out.println("Stock inválido (debe ser >= 0). Se mantuvo el stock anterior.");
            }

            System.out.println("Producto actualizado exitosamente.");
            System.out.println("Nueva información: " + producto.toString());
        } else {
            System.out.println("Operación de actualización cancelada.");
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados para eliminar. Agregue algunos primero.");
            return;
        }

        listarProductos();

        int id = leerEntero("Ingrese el ID del producto a eliminar: ");
        Producto productoAEliminar = buscarProductoPorId(id);
        if (productoAEliminar == null) {
            System.out.println("Producto con ID " + id + " no encontrado.");
            return;
        }

        System.out.println("Va a eliminar el producto: " + productoAEliminar.toString());
        System.out.print("¿Está seguro de eliminar este producto? (s/n): ");
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("s")) {
            productos.remove(productoAEliminar);
            System.out.println("Producto eliminado exitosamente.");
        } else {
            System.out.println("Eliminación de producto cancelada.");
        }
    }

    private static void crearPedido() {
        System.out.println("\n--- CREAR NUEVO PEDIDO ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para crear un pedido. Agregue productos primero.");
            return;
        }

        Pedido nuevoPedido = new Pedido();
        boolean agregarMasProductos = true;

        while (agregarMasProductos) {
            listarProductos();

            int id = leerEntero("Ingrese el ID del producto a agregar al pedido (0 para finalizar): ");
            if (id == 0) {
                agregarMasProductos = false;
                continue;
            }

            Producto productoSeleccionado = buscarProductoPorId(id);
            if (productoSeleccionado == null) {
                System.out.println("Producto con ID " + id + " no encontrado. Intente de nuevo.");
                continue;
            }

            System.out.println("Producto seleccionado: " + productoSeleccionado.getNombre() + " (Stock disponible: " + productoSeleccionado.getStock() + ")");
            int cantidad = leerEntero("Cantidad a pedir: ");

            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser un valor positivo.");
                continue;
            }

            try {
                if (productoSeleccionado.getStock() < cantidad) {
                    throw new StockInsuficienteException("Para '" + productoSeleccionado.getNombre() + "'. Stock insuficiente. Solicitado: " + cantidad + ", Disponible: " + productoSeleccionado.getStock());
                }

                boolean productoYaEnPedido = false;
                for (LineaPedido lp : nuevoPedido.getLineas()) {
                    if (lp.getProducto().getId() == productoSeleccionado.getId()) {
                        lp.setCantidad(lp.getCantidad() + cantidad);
                        System.out.println("Cantidad actualizada para '" + productoSeleccionado.getNombre() + "' en el pedido. Nuevo total en pedido: " + lp.getCantidad());
                        productoYaEnPedido = true;
                        break;
                    }
                }

                if (!productoYaEnPedido) {
                    nuevoPedido.agregarLinea(new LineaPedido(productoSeleccionado, cantidad));
                }

                productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);
                System.out.println("Producto agregado al pedido.");
            } catch (StockInsuficienteException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (!nuevoPedido.getLineas().isEmpty()) {
            pedidos.add(nuevoPedido);
            System.out.println("Pedido creado exitosamente.");
            System.out.println(nuevoPedido.toString());
        } else {
            System.out.println("No se agregó ningún producto. Pedido cancelado.");
        }
    }

    private static void listarPedidos() {
        System.out.println("\n--- LISTADO DE PEDIDOS ---");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados para mostrar.");
            return;
        }

        for (Pedido p : pedidos) {
            System.out.println(p.toString());
            System.out.println("----------------------------------------");
        }
    }

    private static Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
            }
        }
    }
}

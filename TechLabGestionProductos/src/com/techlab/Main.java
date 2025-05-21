package com.techlab;

import com.techlab.productos.Producto;
import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.LineaPedido;
import com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale; // Importar Locale para formato numérico consistente

public class Main {
    private static List<Producto> productos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    // Método para limpiar la consola, adaptado para diferentes sistemas operativos
    private static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Códigos ANSI para limpiar la consola (comunes en Linux/macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // No hacer nada si la limpieza falla
        }
    }

    public static void main(String[] args) {
        int opcion;
        do {
            clearConsole();
            mostrarMenu();
            opcion = leerEntero("Elija una opción: ");
            System.out.println(); // Salto de línea para separar la entrada de la salida

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
                scanner.nextLine(); // Espera a que el usuario presione Enter
            }
        } while (opcion != 7);
        scanner.close(); // Cierra el scanner al salir del programa
    }

    // Muestra el menú principal de opciones
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

    // Permite al usuario agregar un nuevo producto al sistema
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

    // Muestra una lista de todos los productos disponibles en el sistema
    private static void listarProductos() {
        System.out.println("\n--- LISTADO DE PRODUCTOS DISPONIBLES ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados para mostrar. ¡Agregue algunos primero!");
            return;
        }
        // Encabezado de la tabla
        System.out.printf("%-5s %-25s %-12s %-8s%n", "ID", "NOMBRE", "PRECIO", "STOCK");
        System.out.println("----- ------------------------- ------------ --------"); // Línea de separación
        for (Producto p : productos) {
            // Formatea y muestra los detalles de cada producto
            // Usamos Locale.US para asegurar que el punto sea el separador decimal para el precio
            System.out.printf(Locale.US, "%-5d %-25s $%-11.2f %-8d%n",
                p.getId(), p.getNombre(), p.getPrecio(), p.getStock()
            );
        }
        System.out.println("----- ------------------------- ------------ --------"); // Línea de separación
    }

    // Permite al usuario buscar y actualizar la información de un producto existente
    private static void buscarActualizarProducto() {
        System.out.println("\n--- BUSCAR/ACTUALIZAR PRODUCTO ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados para buscar. Agregue algunos primero.");
            return;
        }

        listarProductos(); // Muestra la lista de productos para que el usuario elija

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

    // Permite al usuario eliminar un producto del sistema
    private static void eliminarProducto() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados para eliminar. Agregue algunos primero.");
            return;
        }

        listarProductos(); // Muestra la lista de productos para que el usuario elija

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

    // Permite al usuario crear un nuevo pedido, añadiendo productos al mismo
    private static void crearPedido() {
        System.out.println("\n--- CREAR NUEVO PEDIDO ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para crear un pedido. Agregue productos primero.");
            return;
        }

        Pedido nuevoPedido = new Pedido();
        boolean agregarMasProductos = true;

        while (agregarMasProductos) {
            listarProductos(); // Muestra los productos disponibles para facilitar la selección

            int id = leerEntero("Ingrese el ID del producto a agregar al pedido (0 para finalizar): ");
            if (id == 0) {
                agregarMasProductos = false; // El usuario quiere finalizar el pedido
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
                // Verifica si hay suficiente stock antes de añadir al pedido
                if (productoSeleccionado.getStock() < cantidad) {
                    throw new StockInsuficienteException("Para '" + productoSeleccionado.getNombre() + "'. Stock insuficiente. Solicitado: " + cantidad + ", Disponible: " + productoSeleccionado.getStock());
                }

                boolean productoYaEnPedido = false;
                for (LineaPedido lp : nuevoPedido.getLineas()) {
                    if (lp.getProducto().getId() == productoSeleccionado.getId()) {
                        lp.setCantidad(lp.getCantidad() + cantidad); // Incrementa la cantidad si el producto ya está en el pedido
                        System.out.println("Cantidad actualizada para '" + productoSeleccionado.getNombre() + "' en el pedido. Nuevo total en pedido: " + lp.getCantidad());
                        productoYaEnPedido = true;
                        break;
                    }
                }

                if (!productoYaEnPedido) {
                    // Si el producto no estaba en el pedido, agrega una nueva línea
                    nuevoPedido.agregarLinea(new LineaPedido(productoSeleccionado, cantidad));
                    System.out.println("Producto '" + productoSeleccionado.getNombre() + "' agregado al pedido.");
                }

                // Reduce el stock del producto en el inventario
                productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);

            } catch (StockInsuficienteException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Finalización y confirmación del pedido
        if (!nuevoPedido.getLineas().isEmpty()) {
            System.out.print("¿Confirmar este pedido? (s/n): ");
            String confirmacionPedido = scanner.nextLine();
            if (confirmacionPedido.equalsIgnoreCase("s")) {
                pedidos.add(nuevoPedido);
                System.out.println("Pedido " + nuevoPedido.getId() + " creado exitosamente con un total de $" + String.format(Locale.US, "%.2f", nuevoPedido.calcularTotal()));
            } else {
                // Si el pedido se cancela, se revierte el stock de los productos
                for (LineaPedido lp : nuevoPedido.getLineas()) {
                    lp.getProducto().setStock(lp.getProducto().getStock() + lp.getCantidad());
                }
                System.out.println("Pedido cancelado. El stock de los productos ha sido revertido.");
            }
        } else {
            System.out.println("No se agregaron productos al pedido. Pedido no creado.");
        }
    }

    // Muestra una lista detallada de todos los pedidos realizados
    private static void listarPedidos() {
        System.out.println("\n--- LISTADO DE PEDIDOS REALIZADOS ---");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados para mostrar.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p.toString()); // El toString de Pedido ya está formateado
            System.out.println(); // Espacio entre pedidos
        }
    }

    // Busca un producto por su ID en la lista de productos
    private static Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; // Retorna null si no se encuentra el producto
    }

    // Lee una entrada entera del usuario con manejo de errores
    // Permite "-1" como un valor especial si el mensaje indica "dejar vacío"
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine();
                if (input.trim().isEmpty() && mensaje.contains("dejar vacío")) {
                    return -1; // Valor especial para indicar que no se cambió
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero válido.");
            }
        }
    }

  
    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine();
                if (input.trim().isEmpty() && mensaje.contains("dejar vacío")) {
                    return -1.0; // Valor especial para indicar que no se cambió
                }
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número decimal válido (ej. 123.45).");
            }
        }
    }
}
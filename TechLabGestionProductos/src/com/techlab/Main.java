package com.techlab;

import com.techlab.productos.Producto;
import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.LineaPedido;
import com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    // La lista de productos ahora inicia VACÍA. Deberás agregar productos desde el menú.
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
            // No hacer nada si la limpieza falla
        }
    }

    public static void main(String[] args) {
        // *** SE HAN ELIMINADO LOS PRODUCTOS PRECARGADOS ***
        // Ahora deberás agregar los productos usando la opción 1 del menú.

        int opcion;
        do {
            clearConsole();
            mostrarMenu();
            opcion = leerEntero(ColorConsole.CYAN + "Elija una opción: " + ColorConsole.RESET);
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
                    System.out.println(ColorConsole.YELLOW + ColorConsole.BOLD + "Saliendo del sistema de gestión TechLab. ¡Hasta pronto!" + ColorConsole.RESET);
                    break;
                default:
                    System.out.println(ColorConsole.RED + ColorConsole.BOLD + "✖ Opción inválida. Por favor, intente de nuevo." + ColorConsole.RESET);
            }
            if (opcion != 7) {
                System.out.println("\n" + ColorConsole.PURPLE + ColorConsole.FAINT + "Presione ENTER para continuar..." + ColorConsole.RESET);
                scanner.nextLine();
            }
        } while (opcion != 7);
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println(ColorConsole.BLUE + ColorConsole.BOLD + "╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + ColorConsole.YELLOW + ColorConsole.BOLD + "       SISTEMA DE GESTIÓN - TECHLAB         " + ColorConsole.BLUE + ColorConsole.BOLD + " ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════╣");
        System.out.println("║ " + ColorConsole.GREEN + "1) AGREGAR PRODUCTO" + ColorConsole.BLUE + "                                         ║");
        System.out.println("║ " + ColorConsole.GREEN + "2) LISTAR PRODUCTOS" + ColorConsole.BLUE + "                                         ║");
        System.out.println("║ " + ColorConsole.GREEN + "3) BUSCAR/ACTUALIZAR PRODUCTO" + ColorConsole.BLUE + "                                     ║");
        System.out.println("║ " + ColorConsole.GREEN + "4) ELIMINAR PRODUCTO" + ColorConsole.BLUE + "                                         ║");
        System.out.println("║ " + ColorConsole.GREEN + "5) CREAR UN PEDIDO" + ColorConsole.BLUE + "                                           ║");
        System.out.println("║ " + ColorConsole.GREEN + "6) LISTAR PEDIDOS" + ColorConsole.BLUE + "                                            ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════╣");
        System.out.println("║ " + ColorConsole.RED + ColorConsole.BOLD + "7) SALIR" + ColorConsole.BLUE + "                                             ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝" + ColorConsole.RESET);
    }

    private static void agregarProducto() {
        System.out.println(ColorConsole.BLUE + ColorConsole.BOLD + "\n--- AGREGAR NUEVO PRODUCTO ---" + ColorConsole.RESET);
        System.out.print(ColorConsole.WHITE + "Ingrese el nombre del producto: " + ColorConsole.RESET);
        String nombre = scanner.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println(ColorConsole.RED + "✖ El nombre del producto no puede estar vacío." + ColorConsole.RESET);
            return;
        }

        double precio = leerDouble(ColorConsole.WHITE + "Ingrese el precio (ej. 29.99): " + ColorConsole.RESET);
        if (precio <= 0) {
            System.out.println(ColorConsole.RED + "✖ El precio debe ser un valor positivo." + ColorConsole.RESET);
            return;
        }

        int stock = leerEntero(ColorConsole.WHITE + "Ingrese la cantidad en stock: " + ColorConsole.RESET);
        if (stock < 0) {
            System.out.println(ColorConsole.RED + "✖ La cantidad en stock no puede ser negativa." + ColorConsole.RESET);
            return;
        }

        Producto nuevoProducto = new Producto(nombre, precio, stock);
        productos.add(nuevoProducto);
        System.out.println(ColorConsole.GREEN + "✔ Producto '" + nuevoProducto.getNombre() + "' (ID: " + nuevoProducto.getId() + ") agregado exitosamente." + ColorConsole.RESET);
    }

    private static void listarProductos() {
        System.out.println(ColorConsole.BLUE + ColorConsole.BOLD + "\n--- LISTADO DE PRODUCTOS DISPONIBLES ---" + ColorConsole.RESET);
        if (productos.isEmpty()) {
            System.out.println(ColorConsole.YELLOW + "ℹ No hay productos registrados para mostrar. ¡Agregue algunos primero!" + ColorConsole.RESET);
            return;
        }
        System.out.println(ColorConsole.CYAN + "╔═════╦═════════════════════════╦═══════════╦═══════╗");
        System.out.println("║ " + ColorConsole.BOLD + "ID   " + ColorConsole.CYAN + "║ " + ColorConsole.BOLD + "NOMBRE                     " + ColorConsole.CYAN + "║ " + ColorConsole.BOLD + "PRECIO     " + ColorConsole.CYAN + "║ " + ColorConsole.BOLD + "STOCK " + ColorConsole.CYAN + "║");
        System.out.println("╠═════╬═════════════════════════╬═══════════╬═══════╣" + ColorConsole.RESET);
        for (Producto p : productos) {
            String stockColor = (p.getStock() <= 5 && p.getStock() > 0) ? ColorConsole.YELLOW : (p.getStock() == 0 ? ColorConsole.RED : ColorConsole.GREEN);
            System.out.println(String.format("%s║ %-3d %s║ %s%-23s %s║ %s$%-8.2f %s║ %s%-5d %s║",
                ColorConsole.WHITE, p.getId(), ColorConsole.CYAN,
                ColorConsole.WHITE, p.getNombre(), ColorConsole.CYAN,
                ColorConsole.YELLOW, p.getPrecio(), ColorConsole.CYAN,
                stockColor, p.getStock(), ColorConsole.CYAN
            ));
        }
        System.out.println(ColorConsole.CYAN + "╚═════╩═════════════════════════╩═══════════╩═══════╝" + ColorConsole.RESET);
    }

    private static void buscarActualizarProducto() {
        System.out.println(ColorConsole.BLUE + ColorConsole.BOLD + "\n--- BUSCAR/ACTUALIZAR PRODUCTO ---" + ColorConsole.RESET);
        if (productos.isEmpty()) {
            System.out.println(ColorConsole.YELLOW + "ℹ No hay productos registrados para buscar. Agregue algunos primero." + ColorConsole.RESET);
            return;
        }

        // ¡Corrección aplicada: se muestra la lista de productos para facilitar la selección!
        listarProductos(); 

        int id = leerEntero(ColorConsole.WHITE + "Ingrese el ID del producto a buscar: " + ColorConsole.RESET);
        Producto producto = buscarProductoPorId(id);
        if (producto == null) {
            System.out.println(ColorConsole.RED + "✖ Producto con ID " + id + " no encontrado." + ColorConsole.RESET);
            return;
        }

        System.out.println(ColorConsole.GREEN + "✔ Producto encontrado: " + ColorConsole.RESET + producto.toString());
        System.out.print(ColorConsole.YELLOW + "¿Desea actualizar el producto? (s/n): " + ColorConsole.RESET);
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            System.out.println(ColorConsole.BLUE + "\n--- ACTUALIZANDO PRODUCTO ---" + ColorConsole.RESET);
            System.out.print(ColorConsole.WHITE + "Ingrese el nuevo nombre (actual: " + producto.getNombre() + ", dejar vacío para no cambiar): " + ColorConsole.RESET);
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.trim().isEmpty()) {
                producto.setNombre(nuevoNombre.trim());
            }

            double nuevoPrecio = leerDouble(ColorConsole.WHITE + "Ingrese el nuevo precio (actual: " + producto.getPrecio() + ", ingrese 0 o negativo para no cambiar): " + ColorConsole.RESET);
            if (nuevoPrecio > 0) {
                producto.setPrecio(nuevoPrecio);
            } else {
                System.out.println(ColorConsole.RED + "✖ Precio inválido (debe ser > 0). Se mantuvo el precio anterior." + ColorConsole.RESET);
            }

            int nuevoStock = leerEntero(ColorConsole.WHITE + "Ingrese el nuevo stock (actual: " + producto.getStock() + ", ingrese negativo para no cambiar): " + ColorConsole.RESET);
            if (nuevoStock >= 0) {
                producto.setStock(nuevoStock);
            } else {
                System.out.println(ColorConsole.RED + "✖ Stock inválido (debe ser >= 0). Se mantuvo el stock anterior." + ColorConsole.RESET);
            }

            System.out.println(ColorConsole.GREEN + "✔ Producto actualizado exitosamente." + ColorConsole.RESET);
            System.out.println(ColorConsole.GREEN + "Nueva información: " + ColorConsole.RESET + producto.toString());
        } else {
            System.out.println(ColorConsole.YELLOW + "ℹ Operación de actualización cancelada." + ColorConsole.RESET);
        }
    }

    private static void eliminarProducto() {
        System.out.println(ColorConsole.BLUE + ColorConsole.BOLD + "\n--- ELIMINAR PRODUCTO ---" + ColorConsole.RESET);
        if (productos.isEmpty()) {
            System.out.println(ColorConsole.YELLOW + "ℹ No hay productos registrados para eliminar. Agregue algunos primero." + ColorConsole.RESET);
            return;
        }

        listarProductos(); // Llama al método existente para mostrar la tabla de productos.

        int id = leerEntero(ColorConsole.WHITE + "Ingrese el ID del producto a eliminar: " + ColorConsole.RESET);
        Producto productoAEliminar = buscarProductoPorId(id);
        if (productoAEliminar == null) {
            System.out.println(ColorConsole.RED + "✖ Producto con ID " + id + " no encontrado." + ColorConsole.RESET);
            return;
        }

        System.out.println(ColorConsole.YELLOW + "⚠ Va a eliminar el producto: " + productoAEliminar.toString() + ColorConsole.RESET);
        System.out.print(ColorConsole.YELLOW + "¿Está seguro de eliminar este producto? (s/n): " + ColorConsole.RESET);
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("s")) {
            productos.remove(productoAEliminar);
            System.out.println(ColorConsole.GREEN + "✔ Producto eliminado exitosamente." + ColorConsole.RESET);
        } else {
            System.out.println(ColorConsole.YELLOW + "ℹ Eliminación de producto cancelada." + ColorConsole.RESET);
        }
    }

    private static void crearPedido() {
        System.out.println(ColorConsole.BLUE + ColorConsole.BOLD + "\n--- CREAR NUEVO PEDIDO ---" + ColorConsole.RESET);
        if (productos.isEmpty()) {
            System.out.println(ColorConsole.RED + "✖ No hay productos disponibles para crear un pedido. Agregue productos primero." + ColorConsole.RESET);
            return;
        }

        Pedido nuevoPedido = new Pedido();
        boolean agregarMasProductos = true;

        while (agregarMasProductos) {
            listarProductos(); // Muestra los productos disponibles para facilitar la selección

            int id = leerEntero(ColorConsole.WHITE + "Ingrese el ID del producto a agregar al pedido (0 para finalizar): " + ColorConsole.RESET);
            if (id == 0) {
                agregarMasProductos = false;
                continue;
            }

            Producto productoSeleccionado = buscarProductoPorId(id);
            if (productoSeleccionado == null) {
                System.out.println(ColorConsole.RED + "✖ Producto con ID " + id + " no encontrado. Intente de nuevo." + ColorConsole.RESET);
                continue;
            }

            System.out.println(ColorConsole.YELLOW + "Producto seleccionado: " + productoSeleccionado.getNombre() + " (Stock disponible: " + productoSeleccionado.getStock() + ")" + ColorConsole.RESET);
            int cantidad = leerEntero(ColorConsole.WHITE + "Cantidad a pedir: " + ColorConsole.RESET);

            if (cantidad <= 0) {
                System.out.println(ColorConsole.RED + "✖ La cantidad debe ser un valor positivo." + ColorConsole.RESET);
                continue;
            }

            try {
                if (productoSeleccionado.getStock() < cantidad) {
                    throw new StockInsuficienteException("Para '" + productoSeleccionado.getNombre() + "'. Stock insuficiente. Solicitado: " + cantidad + ", Disponible: " + productoSeleccionado.getStock());
                }

                boolean productoYaEnPedido = false;
                for (LineaPedido lp : nuevoPedido.getLineas()) {
                    if (lp.getProducto().getId() == productoSeleccionado.getId()) {
                        lp.setCantidad(lp.getCantidad() + cantidad); // Incrementa la cantidad en la línea existente
                        System.out.println(ColorConsole.GREEN + "✔ Cantidad actualizada para '" + productoSeleccionado.getNombre() + "' en el pedido. Nuevo total en pedido: " + lp.getCantidad() + ColorConsole.RESET);
                        productoYaEnPedido = true;
                        break;
                    }
                }

                if (!productoYaEnPedido) {
                    // Si el producto no estaba en el pedido, agrega una nueva línea
                    nuevoPedido.agregarLinea(new LineaPedido(productoSeleccionado, cantidad));
                    System.out.println(ColorConsole.GREEN + "✔ Producto '" + productoSeleccionado.getNombre() + "' agregado al pedido." + ColorConsole.RESET);
                }

                // REDUCIR EL STOCK DEL PRODUCTO *DESPUÉS* DE HABERLO AGREGADO O ACTUALIZADO EN EL PEDIDO.
                // Esto asegura que si se añade varias veces, el stock se reduce progresivamente.
                productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);

            } catch (StockInsuficienteException e) {
                System.out.println(ColorConsole.RED + "✖ Error: " + e.getMessage() + ColorConsole.RESET);
            }
        }

        if (!nuevoPedido.getLineas().isEmpty()) {
            System.out.print(ColorConsole.YELLOW + "¿Confirmar este pedido? (s/n): " + ColorConsole.RESET);
            String confirmacionPedido = scanner.nextLine();
            if (confirmacionPedido.equalsIgnoreCase("s")) {
                pedidos.add(nuevoPedido);
                System.out.println(ColorConsole.GREEN + ColorConsole.BOLD + "✔ Pedido " + nuevoPedido.getId() + " creado exitosamente con un total de $" + nuevoPedido.calcularTotal() + ColorConsole.RESET);
            } else {
                // Si el pedido no se confirma, revertir el stock de TODOS los productos en el pedido
                for (LineaPedido lp : nuevoPedido.getLineas()) {
                    lp.getProducto().setStock(lp.getProducto().getStock() + lp.getCantidad());
                }
                System.out.println(ColorConsole.YELLOW + "ℹ Pedido cancelado. El stock de los productos ha sido revertido." + ColorConsole.RESET);
            }
        } else {
            System.out.println(ColorConsole.YELLOW + "ℹ No se agregaron productos al pedido. Pedido no creado." + ColorConsole.RESET);
        }
    }

    private static void listarPedidos() {
        System.out.println(ColorConsole.BLUE + ColorConsole.BOLD + "\n--- LISTADO DE PEDIDOS REALIZADOS ---" + ColorConsole.RESET);
        if (pedidos.isEmpty()) {
            System.out.println(ColorConsole.YELLOW + "ℹ No hay pedidos registrados para mostrar." + ColorConsole.RESET);
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p.toString());
            System.out.println();
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
                String input = scanner.nextLine();
                if (input.trim().isEmpty() && mensaje.contains("dejar vacío")) {
                    return -1;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(ColorConsole.RED + "✖ Entrada inválida. Por favor, ingrese un número entero válido." + ColorConsole.RESET);
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine();
                if (input.trim().isEmpty() && mensaje.contains("dejar vacío")) {
                    return -1.0;
                }
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(ColorConsole.RED + "✖ Entrada inválida. Por favor, ingrese un número decimal válido (ej. 123.45)." + ColorConsole.RESET);
            }
        }
    }
}
Sistema de Gestión TechLab
Este proyecto implementa un sistema básico de gestión para productos y pedidos, diseñado para funcionar directamente en la consola. La aplicación permite a los usuarios interactuar a través de un menú sencillo para realizar diversas operaciones, facilitando el control de inventario y la gestión de transacciones.

Características Principales
Gestión de Productos
Agregar Productos: Añade nuevos productos al inventario, especificando su nombre, precio y cantidad en stock.
Listar Productos: Muestra un listado claro de todos los productos disponibles, incluyendo su ID, nombre, precio y stock actual.
Buscar y Actualizar Productos: Permite buscar productos por ID y modificar su nombre, precio o cantidad en stock.
Eliminar Productos: Remueve productos del inventario con una confirmación para evitar eliminaciones accidentales.
Gestión de Pedidos
Crear Pedidos: Permite construir nuevos pedidos seleccionando productos del inventario y definiendo cantidades. Incluye validación de stock para asegurar que no se vendan más productos de los disponibles. Puedes añadir múltiples ítems a un mismo pedido.
Manejo de Stock: El stock de los productos se actualiza automáticamente al crear y confirmar un pedido. Si un pedido es cancelado, el stock se revierte para mantener la coherencia del inventario.
Listar Pedidos: Muestra un resumen de todos los pedidos realizados, detallando los productos incluidos, cantidades y el total calculado para cada uno.
Tecnologías Utilizadas
Java: El lenguaje de programación principal del proyecto.
Programación Orientada a Objetos (POO): La estructura del código se basa en clases como Producto, Pedido y LineaPedido, promoviendo un diseño modular y fácil de mantener.
Manejo de Excepciones: Implementa excepciones personalizadas (StockInsuficienteException) para gestionar escenarios específicos, como la falta de stock durante la creación de pedidos.
Interfaz de Consola Simplificada: Ofrece una interfaz de usuario clara y funcional directamente en la terminal, priorizando la facilidad de uso y la legibilidad del texto sin elementos gráficos complejos.
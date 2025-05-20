Resumen del Proyecto: Sistema de Gestión TechLab
Este proyecto implementa un sistema básico de gestión para productos y pedidos, diseñado para funcionar en la consola. La aplicación permite a los usuarios interactuar con un menú intuitivo para realizar diversas operaciones, facilitando el control de inventario y la creación de transacciones.

Características Principales:

Gestión de Productos:
Agregar Productos: Permite añadir nuevos productos al inventario con su nombre, precio y cantidad en stock.
Listar Productos: Muestra un listado detallado de todos los productos disponibles, incluyendo su ID, nombre, precio y stock actual. El stock bajo se resalta para una fácil identificación.
Buscar y Actualizar Productos: Facilita la búsqueda de productos por ID y ofrece la opción de modificar su nombre, precio o cantidad en stock.
Eliminar Productos: Permite remover productos del inventario de forma segura, con una confirmación para evitar eliminaciones accidentales.
Gestión de Pedidos:
Crear Pedidos: Permite construir nuevos pedidos seleccionando productos del inventario y especificando cantidades. Incluye validación de stock para asegurar que no se vendan más productos de los disponibles y la posibilidad de agregar múltiples ítems al mismo pedido.
Manejo de Stock: El stock de los productos se actualiza automáticamente al crear y confirmar un pedido. Si un pedido es cancelado, el stock se revierte.
Listar Pedidos: Muestra un resumen de todos los pedidos realizados, detallando los productos incluidos, cantidades y el total calculado para cada uno.
Tecnologías Utilizadas:

Java: Lenguaje de programación principal.
Programación Orientada a Objetos (POO): Utiliza clases como Producto, Pedido, y LineaPedido para una estructura modular y mantenible.
Manejo de Excepciones: Implementa excepciones personalizadas (StockInsuficienteException) para gestionar escenarios específicos como la falta de stock.
Manejo de Consola y Colores ANSI: Proporciona una interfaz de usuario mejorada en la terminal con texto de colores para una mejor legibilidad y experiencia.
Este sistema es una base sólida para una aplicación de gestión, demostrando el uso de principios de POO, manejo de datos en memoria y una interfaz de usuario interactiva basada en consola.
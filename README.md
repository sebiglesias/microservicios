# Microservicios

**`Trabajo Practico para Sistemas Distribuidos 2017`**

**Primera consigna**

Implementar un servicio de manejo de listas de artículos de un usuario (por ejemplo
artículos deseados o carrito de compras). Hacerlo accesible en forma remota usando un
framework de RCP (ej: Avro RPC, GRPC o Thrift)

Nosotros elegimos `GRPC`

El servicio debe proveer la siguiente funcionalidad:
- Agregar un artículo a la lista de "items deseados" de un usuario.
- Recuperar la lista de un usuario.
- Eliminar artículos de la lista.

El servicio debe funcionar como un container de docker.
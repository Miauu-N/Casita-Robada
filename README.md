# Casita Robada 🃏

## Descripción

Implementación distribuida del juego de cartas Casita Robada utilizando Java RMI y arquitectura MVC.

Permite partidas multijugador de 2 a 4 participantes mediante una estructura cliente-servidor con interfaces gráfica y de consola.

Este proyecto fue desarrollado como trabajo práctico para la materia de **Programación Orientada a Objetos** (POO) en la Universidad Nacional de Luján (UNLu).

---

## 🛠️ Stack Tecnológico

- **Lenguaje**: Java SE (Compatible con JDK 17 y versiones superiores)
- **Tecnología de Red**: Java RMI (Remote Method Invocation)
- **Librería de RMI**: `LibreriaRMIMVC.jar` (Framework educativo para control del patrón MVC en red)
- **Diseño GUI**: Java Swing (construido con IntelliJ IDEA GUI Designer)
- **Persistencia**: Serialización de archivos en Java (Guardado de Ranking local)

---

## ⚙️ Guía de Uso y Configuración

### Requisitos Previos

- Tener instalado **Java JDK 17** o superior.
- Se recomienda el uso de un IDE como **IntelliJ IDEA** para compilar y ejecutar el proyecto.

### Paso 1: Iniciar el Servidor (`AppServidor`)

1. Ejecuta la clase `AppServidor.java`.
2. Selecciona la IP (ej. `127.0.0.1` para local) y el puerto (por defecto `8888`) en las ventanas emergentes.
3. El servidor quedará a la espera de que los clientes se conecten.

### Paso 2: Iniciar los Clientes (`AppCliente`)

Se admiten de **2 a 4 jugadores**. Para cada uno:

1. Ejecuta la clase `AppCliente.java`.
2. Configura los parámetros en las ventanas emergentes:
   - **Tipo de IU**: Elige `Consola` (Terminal) o `Grafica` (Ventana Swing).
   - **IP / Puerto Cliente**: IP local y puerto en desuso (ej. `9999`, `9998`).
   - **IP / Puerto Servidor**: IP del servidor (ej. `127.0.0.1`) y puerto (`8888`).
3. Ingresa tu nombre y presiona **Listo/Ready** para esperar al resto de jugadores.
4. (Opcional) Si son 4 jugadores, el Host puede elegir si jugar de forma individual o en parejas.

---

## 📂 Estructura del Proyecto

- **`src/AppServidor.java`**: Punto de entrada del servidor.
- **`src/AppCliente.java`**: Punto de entrada del cliente.
- **`src/Modelo/`**: Lógica de negocio (Cartas, Mazo, Jugador, Mesa, Equipos, Ranking y Serialización).
- **`src/Controlador/`**: Controlador remoto que vincula la vista del cliente con el modelo del servidor.
- **`src/Vista/`**: Interfaces de usuario (Consola y Gráfica con Swing).

Requerimientos Mínimos del Sistema

Software:
1. Java: JDK 11 o superior.
2. JavaFX: SDK para JavaFX compatible con la versión de Java instalada.
3. IDE: Se recomienda IntelliJ IDEA o Eclipse con soporte para JavaFX.
4. Base de Datos: MySQL Server 8.0 o superior.

Hardware:
1. Procesador: 1 GHz o superior.
2. Memoria RAM: 2 GB o más.
3. Espacio en Disco: Al menos 200 MB de espacio libre.
4. Conexión a Internet: Requerida para la descarga de dependencias.

Configuración de la Base de Datos
Antes de ejecutar la aplicación, asegúrate de tener una base de datos MySQL en funcionamiento. Utiliza el script SQL incluido en el repositorio para crear la base de datos y las tablas necesarias:

Cómo Correr la Aplicación
1. Clona el repositorio: Obtén el código fuente desde el repositorio de Git.
2. Abre el proyecto en tu IDE: Importa el proyecto en IntelliJ IDEA o Eclipse.
3. Configura JavaFX: Asegúrate de configurar el SDK de JavaFX en tu IDE.
4. Configura la conexión a la base de datos: Ajusta las credenciales de la base de datos en la clase DatabaseConnection para que coincidan con tu configuración local.
5. Ejecuta el proyecto: Utiliza la clase MainUI para lanzar la aplicación.

Librerías Externas

Este proyecto requiere las siguientes librerías, que se deben incluir en el classpath del proyecto:
1. MySQL JDBC Driver: Para la conexión a la base de datos MySQL.
2. JavaFX: Para la interfaz gráfica de usuario.

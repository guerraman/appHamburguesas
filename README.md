# LABActividad Corte 3: Monitoreo de Eventos y Gestión de Servidores en Android

## Descripción General
Esta es una aplicación Android desarrollada en **Android Studio** que implementa el manejo de **bases de datos locales (SQLite)**, el procesamiento de ficheros y el **consumo de servicios REST**. La aplicación se compone de funcionalidades clave que incluyen el monitoreo de eventos de seguridad, la gestión de servidores y la carga de datos desde una API externa. Un menú de navegación permite una experiencia de usuario fluida e intuitiva.

## Funcionalidades Principales

### 1. Monitoreo de Eventos
- **Lectura del fichero `LogSecure.txt`**: La app busca y analiza el fichero `LogSecure.txt` para detectar tipos de ataques, mostrando cuántas veces aparecen.
- **Visualización en `RecyclerView`**: Se muestran los tipos de ataques y sus frecuencias.
- **Extracción de IPs**: La app extrae y muestra las IPs relacionadas con los ataques.
- **Ejecución en segundo plano con `AsyncTask`**: Asegura que la interfaz de usuario permanezca activa mientras se procesan las tareas.

### 2. Gestión de Servidores
- **Base de Datos SQLite**:
  - **Tabla**: `Servidores`
  - **Campos**:
    - `id`: Entero auto-generado.
    - `Nombre`: Ejemplo: "Servidor Web".
    - `Descripción`: Ejemplo: "Servidor dedicado a la página Web".
- **Operaciones CRUD**:
  - **Agregar**: Permite registrar servidores, con confirmaciones por `Toast`.
  - **Listar**: Muestra los registros almacenados en un `RecyclerView`.
  - **Modificar y eliminar**: Se pueden actualizar o borrar registros.
- **Mensajes `Toast`**: Informan al usuario sobre el progreso de las operaciones.

### 3. Consumo de API y Guardado de Datos
- **URL de la API**: [Puestos de vacunación en Colombia](https://www.datos.gov.co/resource/46yq-tz63.json)
- **Visualización y selección de datos**:
  - Los datos JSON se muestran en un `RecyclerView` o `ListView` donde el elemento principal es `sistema_operativo + nombre_de_activo`.
  - Se pueden seleccionar registros para guardarlos en la base de datos SQLite.
- **Guardado de datos**: Se almacenan solo los campos necesarios de cada registro.
- **Activity de visualización**: Permite ver los registros guardados en la base de datos.

### 4. Menú de Navegación
Incluye opciones para acceder a todas las funcionalidades de la aplicación: Monitoreo y Servidores.

## Temas Implementados
- **Bases de datos SQLite**: Operaciones CRUD.
- **Lectura de ficheros**: Análisis de archivos de texto.
- **Consumo de API REST**: Procesamiento de datos JSON.
- **`AsyncTask`**: Ejecución de procesos en segundo plano.
- **RecyclerView y ListView**: Visualización de listas de datos.
- **Notificaciones `Toast`**: Para mejorar la interacción con el usuario.

## Instrucciones de Uso
1. **Monitoreo de Eventos**:
   - Cargue `LogSecure.txt` y acceda a la opción de Monitoreo para ver los tipos de ataques y las IPs en el `RecyclerView`.

2. **Gestión de Servidores**:
   - Registre, liste, modifique o elimine servidores. Reciba confirmaciones por `Toast`.

3. **Consumo de API y Guardado de Datos**:
   - Cargue datos de la API y seleccione registros para guardar en la base de datos SQLite. Verifique los registros guardados en la actividad de visualización.

## Consideraciones Finales
- La carga de `LogSecure.txt` se puede realizar mediante un selector de archivos o manualmente en el sistema de directorios del dispositivo.
- La aplicación requiere permisos de acceso a almacenamiento y red.

## Tecnologías y Herramientas
- **Android Studio**
- **SQLite**
- **`AsyncTask` para procesos en segundo plano**
- **RecyclerView y ListView para la visualización de datos**

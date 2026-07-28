# Ruta_Academica
## Descripción
Sistema que permite determinar el número mínimo de semestres necesarios para que un estudiante pueda terminar su plan de estudios. La aplicación permite modelar planes de estudio universitarios (materias, créditos y prerrequisitos) y calcular la cantidad mínima de semestres necesarios para completar el plan, respetando un número máximo de materias por semestre. Para ello se usa un algoritmo basado en ordenamiento topológico (Kahn) y búsqueda en amplitud (BFS) sobre un grafo de prerrequisitos.
## Integrantes
Grupo (Nombres y nicknames de GitHub):

* Rafael Ricardo Uribe Perdomo: @ZiidTri
* Jorge Eduardo Piratoba Tocarruncho: @jpiratobat

## Tecnologías y lenguajes utilizados

* Backend: Java (Spring Boot, Maven)
* Frontend: JavaScript (React + Vite)
* Base de datos: SQL (scripts en la carpeta `sql/`)

## Estructura del proyecto

```
MDI_2026_Proyecto_Ruta_Academica/
├── backend/                 # API REST en Spring Boot
│   ├── src/
│   │   └── main/
│   │       ├── java/        # Código fuente del backend
│   │       └── resources/   # Configuración y recursos
│   ├── pom.xml              # Configuración Maven
│   └── ...                  # Wrappers de Maven, etc.
├── frontend/                # Aplicación web en React + Vite
│   ├── src/                 # Componentes, páginas y rutas
│   ├── public/
│   ├── package.json         # Dependencias del frontend
│   └── vite.config.js       # Configuración de Vite
├── sql/                     # Scripts y artefactos de base de datos
│   ├── ScriptCreacionTablas.sql
│   ├── ScriptInsercionDatos.sql
│   ├── ModeloRelacional.png
│   └── FormatoPlanes.txt
└── README.md                # Documentación del proyecto
└── DiagramaComponentesEstructuras.png
└── DiagramaDeFlujoProyecto.png
└── Imagenes/
    └── BannerPrincipal.png
```

## Requisitos previos
Antes de ejecutar el proyecto asegúrate de tener instalado:

* Java 17 o superior
* Maven 3.8+
* Node.js 18+ y npm
* Un gestor de base de datos SQL (por ejemplo MySQL o MariaDB)

## Instalación y ejecución
1. Clonar el repositorio

```
git clonehttps://github.com/jpiratobat/MDI_2026_Proyecto_Ruta_Academica.git
cd MDI_2026_Proyecto_Ruta_Academica
```

2. Base de datos

1. Crear una base de datos vacía (por ejemplo `graduacion`).
2. Ejecutar los scripts en este orden:
   * `sql/ScriptCreacionTablas.sql`
   * `sql/ScriptInsercionDatos.sql`
3. Configurar la conexión a la base de datos en el backend, editando el archivo:
   * `backend/src/main/resources/application.properties`

3. Backend (Spring Boot)
Desde la carpeta raíz del proyecto:

```
cd backend
# compilar el proyecto
./mvnw clean package      # Linux / macOS
# o en Windows:
# mvnw.cmd clean package

# ejecutar la API
./mvnw spring-boot:run    # Linux / macOS
# o:
# mvnw.cmd spring-boot:run
```

Por defecto la API quedará disponible en:
`http://localhost:8088`

4. Frontend (React + Vite)
En otra terminal, desde la carpeta raíz del proyecto:

```
cd frontend
npm install        # instala las dependencias
npm run dev        # levanta el servidor de desarrollo
```

Por defecto la aplicación web se sirve en:
`http://localhost:5173`

5. Uso general

1. Abrir el navegador en `http://localhost:5173`.
2. Seleccionar o crear un plan de estudio.
3. Registrar materias y sus prerrequisitos.
4. Configurar el número máximo de materias por semestre.
5. Ejecutar la simulación para obtener el plan de semestres sugerido y la cantidad mínima de semestres para graduarse. listo que faltaria agregar al readme

## Advertencia
  El archivo `backend/src/main/resources/application.properties` incluido en el repositorio trae un valor de ejemplo en `spring.datasource.password`. Reemplázalo 

## Programa

1. Con el backend corriendo, importa el plan de ejemplo desde `sql/ScriptInsercionDatos.sql`
   (plan "Ingeniería de Sistemas y Computación", 38 materias).
2. Desde la interfaz, entra al plan, ve a la pestaña Simulación y calcula con `L=4`.
   Deberías obtener 10 semestres.
3. Para validar la detección de ciclos, agrega en Prerrequisitos una relación que cierre
   un ciclo (por ejemplo, A depende de B y B depende de A) y vuelve a calcular: el sistema
   debe responder con un error de plan inconsistente en lugar de un resultado.
   
## Estado actual del proyecto

El sistema implementa el flujo completo: registro de planes, materias y prerrequisitos,
y cálculo del plan de semestres mediante el algoritmo de Kahn, incluyendo detección de
ciclos y de referencias inválidas.

Limitaciones conocidas:
- El límite de materias por semestre se envía en cada consulta y no se persiste como
  configuración del plan.
- El cálculo de créditos totales es informativo; no se usa como restricción dentro del
  algoritmo de planificación.
  

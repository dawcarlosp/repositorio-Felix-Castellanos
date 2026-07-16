# GymBear - Gestión de Ejercicios de Gimnasio

> **TFG de Félix Castellanos Gómez** — Aplicación web para la gestión y visualización de ejercicios de gimnasio.

---

## Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| **Frontend** | Angular 17.3, PrimeNG 17, Bootstrap 5.3, RxJS |
| **Backend** | Spring Boot 3.2.4, Java 17, Spring Security, JPA/Hibernate |
| **Base de datos** | MySQL 8 |
| **Autenticación** | JWT (JSON Web Tokens) |
| **Despliegue** | Docker Compose |

---

## Capturas

*(Añadir capturas de pantalla aquí)*

---

## Requisitos

- **Java 17+** y **Maven 3.9+** (para ejecución sin Docker)
- **Node.js 20+** y **Angular CLI 17** (para el frontend)
- **Docker** y **Docker Compose** (para ejecución con Docker)
- **MySQL 8** (solo si se ejecuta sin Docker)

---

## Instalación y ejecución

### Con Docker (recomendado)

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tuusuario/repositorio-Felix-Castellanos.git
   cd repositorio-Felix-Castellanos
   ```

2. Copia y configura las variables de entorno:
   ```bash
   cp .env.example .env
   # Edita .env con tus credenciales
   ```

3. Levanta los servicios:
   ```bash
   docker compose up -d
   ```

4. El backend estará disponible en `http://localhost:8000`.

5. Para el frontend, ejecuta:
   ```bash
   cd Frontend
   npm install
   ng serve
   ```
   Navega a `http://localhost:4200`.

### Sin Docker

#### Backend

1. Asegúrate de tener MySQL 8 ejecutándose y crea la base de datos:
   ```sql
   CREATE DATABASE API_SPRING_DB;
   ```

2. Configura las credenciales en `Backend/src/main/resources/application.properties`.

3. Compila y ejecuta:
   ```bash
   cd Backend
   ./mvnw spring-boot:run
   ```

#### Frontend

```bash
cd Frontend
npm install
ng serve
```

Navega a `http://localhost:4200`.

---

## Estructura del proyecto

```
repositorio-Felix-Castellanos/
├── Backend/                          # API REST Spring Boot
│   ├── src/main/java/com/prueba/api/
│   │   ├── auth/                     # Autenticación (login, registro)
│   │   ├── authorized/               # Controladores protegidos
│   │   ├── Config/                   # Seguridad y configuración
│   │   ├── entity/                   # Entidades JPA
│   │   │   └── dto/                  # DTOs
│   │   ├── ExceptionHandler/         # Manejo global de excepciones
│   │   ├── fileUpload/               # Subida de archivos
│   │   ├── jwt/                      # Servicio JWT
│   │   ├── repository/               # Repositorios JPA
│   │   └── service/                  # Lógica de negocio
│   │       └── impl/                 # Implementaciones
│   └── src/main/resources/
│       └── application.properties    # Configuración
├── Frontend/                         # SPA Angular
│   └── src/app/
│       ├── auth/                     # Login y registro
│       ├── common/                   # Header y footer
│       ├── modelos/                  # Interfaces TypeScript
│       ├── pages/                    # Páginas de la aplicación
│       │   └── componentes/          # Componentes reutilizables
│       └── services/                 # Servicios HTTP
├── docker-compose.yml                # Orquestación Docker
├── .env.example                      # Variables de entorno
└── README.md
```

---

## API Endpoints

### Autenticación

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/auth/login` | Iniciar sesión |
| POST | `/auth/register` | Registrar nuevo usuario |
| PUT | `/auth/update` | Actualizar perfil de usuario |

### Usuarios (requiere autenticación)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/v1/user/{id}` | Obtener usuario por ID |
| DELETE | `/api/v1/user/{username}` | Eliminar usuario |

### Ejercicios (requiere autenticación)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/v1/ejercicio` | Listar todos los ejercicios |
| GET | `/api/v1/ejercicio/{id}` | Obtener ejercicio por ID |
| POST | `/api/v1/ejercicio` | Crear nuevo ejercicio |
| PUT | `/api/v1/ejercicio` | Actualizar ejercicio |
| DELETE | `/api/v1/ejercicio/{id}` | Eliminar ejercicio |
| GET | `/api/v1/ejercicio/user/{idUser}` | Ejercicios de un usuario |
| GET | `/api/v1/ejercicio/count/{idUser}` | Contar ejercicios de un usuario |

### Dificultades (requiere autenticación)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/v1/dificultad` | Listar todas las dificultades |

---

## Contacto

**Félix Castellanos Gómez** — [felisucojunior@gmail.com](mailto:felisucojunior@gmail.com)

---

## Licencia

Proyecto académico — TFG.

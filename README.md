# Prueba Técnica - Christian Ramirez

Repositorio: [prueba-christian-ramirez-atenciones-medicas](https://github.com/latecnologiaavanza/prueba-christian-ramirez-atenciones-medicas/tree/main)

Proyecto realizado para la empresa **InSalud**, simula un sistema de gestión de atenciones médicas, implementado en **Spring Boot** con arquitectura **MVC** y seguridad con **Spring Security y JWT**.

---

## Tecnologías utilizadas

* **Java 17**
* **Spring Boot 3.5.6**
* **Spring Data JPA**
* **PostgreSQL**
* **Spring Security + JWT**
* **MapStruct** (para mapeo de DTOs)
* **Lombok**
* **Spring Validation**
* **Postman** (para pruebas de endpoints)
* **Maven** (gestión de dependencias)
* **Springdoc OpenAPI** (documentación de API, aunque Swagger no es funcional con JWT)

---

## Estructura del proyecto

* **Modelo (Entities)**: `Persona`, `Usuario`, `Paciente`, `Empleado`, `Especialidad`, `MedicoEspecialidad`, `Atencion`.
* **Repositorio (Repository)**: Interfaces JPA para acceder a la base de datos.
* **Servicio (Service)**: Lógica de negocio, desacoplada de controladores.
* **Controlador (Controller)**: Manejo de endpoints REST.
* **DTOs**: Para manejar la comunicación entre la capa de servicio y controlador.
* **Seguridad**: Spring Security con JWT, roles: `PACIENTE`, `MEDICO`, `ADMIN`.

---

## Configuración de la base de datos

En el archivo `application.properties` se debe configurar:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/insalud_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASENA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Nota:** Cambiar `TU_USUARIO` y `TU_CONTRASENA` según tu configuración local.

---

## Datos iniciales de prueba

Antes de registrar usuarios, se deben insertar las **personas, pacientes, empleados y especialidades**:

```sql
-- Personas
INSERT INTO persona (id, email, estado, nombre) VALUES
(1, 'juan.perez@example.com', 'ACTIVO', 'Juan Pérez'),
(2, 'ana.torres@example.com', 'ACTIVO', 'Ana Torres'),
(3, 'carlos.lopez@example.com', 'ACTIVO', 'Carlos López'),
(4, 'maria.gomez@example.com', 'ACTIVO', 'María Gómez'),
(5, 'luis.martinez@example.com', 'ACTIVO', 'Luis Martínez'),
(6, 'sofia.ramirez@example.com', 'ACTIVO', 'Sofía Ramírez'),
(7, 'pedro.garcia@example.com', 'ACTIVO', 'Pedro García'),
(8, 'laura.vargas@example.com', 'ACTIVO', 'Laura Vargas'),
(9, 'andres.soto@example.com', 'ACTIVO', 'Andrés Soto'),
(10, 'camila.flores@example.com', 'ACTIVO', 'Camila Flores');

-- Pacientes
INSERT INTO paciente (id, persona_id, rol, estado) VALUES
(1, 1, 'PACIENTE', 'ACTIVO'),
(2, 3, 'PACIENTE', 'ACTIVO'),
(3, 5, 'PACIENTE', 'ACTIVO'),
(4, 7, 'PACIENTE', 'ACTIVO'),
(5, 9, 'PACIENTE', 'ACTIVO');

-- Empleados
INSERT INTO empleado (id, persona_id, rol, estado) VALUES
(1, 2, 'MEDICO', 'ACTIVO'),
(2, 4, 'MEDICO', 'ACTIVO'),
(3, 6, 'ADMIN', 'ACTIVO'),
(4, 8, 'MEDICO', 'ACTIVO'),
(5, 10, 'MEDICO', 'ACTIVO');

-- Especialidades
INSERT INTO especialidad (id, nombre, estado) VALUES
(1, 'Cardiología', 'ACTIVO'),
(2, 'Pediatría', 'ACTIVO'),
(3, 'Dermatología', 'ACTIVO'),
(4, 'Neurología', 'ACTIVO'),
(5, 'Ginecología', 'ACTIVO');
```

> **Nota:** Los usuarios no se insertan directamente en la base, se crean desde el endpoint `/api/auth/register`.

---

## Endpoints principales

### 1️⃣ Registrar usuarios

**POST** `/api/auth/register`

Ejemplo JSON:

```json
{
  "usuario": "juanp",
  "contrasena": "123456",
  "rol": "PACIENTE",
  "personaId": 1
}
```

Otro ejemplo para un empleado (médico):

```json
{
  "usuario": "anatorres",
  "contrasena": "123456",
  "rol": "MEDICO",
  "personaId": 2
}
```

---

### 2️⃣ Iniciar sesión

**POST** `/api/auth/login`

Ejemplo JSON:

```json
{
  "usuario": "juanp",
  "contrasena": "123456"
}
```

> Esto retorna un **JWT** para usar en los headers de Postman en los demás endpoints protegidos.

---

### 3️⃣ Gestión de atenciones

* **GET** `/api/atenciones` → Listado de todas las atenciones (ADMIN/MEDICO/PACIENTE).
* **GET** `/api/atenciones/mias` → Listado de atenciones del paciente autenticado (PACIENTE).
* **POST** `/api/atenciones` → Crear una nueva atención (ADMIN/MEDICO).
* **PUT** `/api/atenciones/{id}` → Actualizar atención existente (ADMIN/MEDICO).
* **DELETE** `/api/atenciones/{id}` → Eliminar atención (ADMIN).

> **Importante:** Todos los endpoints requieren autenticación con JWT, excepto `/auth/register` y `/auth/login`.

---

## Pruebas y Postman

Debido a que **Spring Security con JWT** está implementado, **Swagger UI no es funcional** para probar los endpoints.
Se recomienda usar **Postman** siguiendo este flujo:

1. Insertar los datos iniciales en la base (`persona`, `paciente`, `empleado`, `especialidad`).
2. Registrar los usuarios con `/api/auth/register`.
3. Iniciar sesión con `/api/auth/login` para obtener el JWT.
4. Agregar el JWT en los headers:

```http
Authorization: Bearer <TOKEN>
```

5. Probar los endpoints protegidos (`/api/atenciones`, `/api/atenciones/mias`, etc.).

---

## Estructura del proyecto en GitHub

```
com.insalud.atencionesmedicas
│
├─ controller/      # Endpoints REST (MVC Controller)
├─ dto/             # Data Transfer Objects
│   ├─ request/     # DTOs de entrada
│   └─ response/    # DTOs de salida
├─ exception/       # Manejo de excepciones personalizadas
├─ mapper/          # MapStruct para convertir entre Entities y DTOs
├─ model/           # Entidades JPA
├─ repository/      # Interfaces JPA para acceso a BD
├─ security/        # Configuración de Spring Security y JWT
└─ service/         # Lógica de negocio (Services)
```

---

## Cómo ejecutar el proyecto

1. Clonar el repositorio:

```bash
git clone https://github.com/latecnologiaavanza/prueba-christian-ramirez-atenciones-medicas.git
```

2. Configurar `application.properties` con tu conexión a PostgreSQL.

3. Ejecutar el proyecto con Maven:

```bash
mvn spring-boot:run
```

4. Insertar los datos iniciales en PostgreSQL usando los `INSERT` proporcionados.

5. Registrar usuarios y probar los endpoints en Postman.

---

## Notas finales

* Este proyecto utiliza **roles y JWT** para proteger los endpoints.
* La inserción de usuarios se hace únicamente vía **registro**, no directamente en la base.
* Swagger no funciona debido a JWT; se debe probar con Postman.
* Se recomienda respetar el orden de inserción de los datos iniciales: **Persona → Paciente/Empleado → Usuarios**.

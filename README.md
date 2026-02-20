# Backend Inventarios

Backend de un sistema de inventarios desarrollado con **Spring Boot**, enfocado en una empresa pequeña/mediana, con control de usuarios, roles y autenticación segura mediante **JWT**.

El proyecto cuenta actualmente con un **módulo de autenticación y autorización completamente funcional**, sirviendo como base sólida para la construcción de los módulos de inventario.

---

## 📦 Descripción general

Este backend provee una API REST segura para:
- Gestión de usuarios
- Autenticación basada en tokens
- Autorización por roles
- Control de acceso a recursos protegidos

Está diseñado con una arquitectura escalable, buenas prácticas de seguridad y separación clara de responsabilidades.

---

## 🧱 Arquitectura

- API REST
- Arquitectura en capas:
  - Controller
  - Service
  - Repository
- DTOs para evitar exposición de entidades sensibles
- Manejo global de excepciones
- Seguridad centralizada con Spring Security

---

## 🧩 Tecnologías

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- BCrypt
- MySQL
- Maven

---

## 🔐 Seguridad (Auth OK)

### Autenticación
- Login contra base de datos
- Contraseñas encriptadas con **BCrypt**
- Generación de JWT
- Validación de tokens en cada request

### Autorización
- Control de acceso basado en roles
- Endpoints protegidos por configuración de seguridad
- Respuestas correctas:
  - `401 Unauthorized`
  - `403 Forbidden`

### Roles disponibles
- `ADMIN`
- `EMPLOYEE`

---

## 📌 Endpoints principales

### 🔑 Login
POST /api/auth/login

**Request body**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response**
```json
{
  "token": "jwt-token",
  "type": "Bearer"
}
```

---

### 👤 Usuario autenticado
GET /api/users/me

**Headers**
Authorization: Bearer <token>

**Response**
```json
{
  "id": 1,
  "username": "admin",
  "roles": ["ADMIN"]
}
```

---

## 🗄 Base de Datos

Base de datos relacional (MySQL).

### Configuración de ejemplo (`application.properties`)
```properties
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

jwt.secret=secret_key
jwt.expiration=86400000
```

---

## 🚀 Ejecución local

### Requisitos
- Java 21
- Maven
- MySQL

### Pasos
1. Clonar el repositorio
2. Entrar al proyecto
3. Ejecutar: mvn spring-boot:run

La API estará disponible en:
http://localhost:8080

---

## 🛣 Roadmap

- CRUD de inventarios
- Objetos simples y objetos contenedores (cajas)
- Gestión de estados de inventario
- Historial de movimientos
- Notificaciones por email a administradores
- Panel administrativo
- Documentación con Swagger / OpenAPI

---

## 📄 Estado del proyecto

- 🟢 Autenticación: estable
- 🟡 Inventarios: en desarrollo
- 🧱 Base técnica: lista para escalar

---

## ✍ Autor

Alan Lopera  
Backend Developer – Java & Spring Boot

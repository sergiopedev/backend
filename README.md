# 🏍️ MotoGarage Backend

**Proyecto de final de grado de DAM (Desarrollo de Aplicaciones Multiplataforma)**

Backend REST API para la gestión de motos y sus modificaciones. Permite a los usuarios registrar sus motocicletas y llevar un control de las piezas y modificaciones realizadas.

---

## 📋 Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Características Principales](#características-principales)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Endpoints de la API](#endpoints-de-la-api)
- [Modelos de Datos](#modelos-de-datos)
- [Configuración de Base de Datos](#configuración-de-base-de-datos)
- [Cómo Ejecutar](#cómo-ejecutar)
- [Validaciones](#validaciones)
- [Manejo de Excepciones](#manejo-de-excepciones)
- [CORS](#cors)
- [Autor](#autor)

---

## 📝 Descripción del Proyecto

MotoGarage es una aplicación backend que proporciona una API REST para la gestión completa de motocicletas y sus modificaciones. Los usuarios pueden:

- Registrarse e iniciar sesión
- Crear y gestionar sus perfiles
- Registrar sus motocicletas
- Agregar modificaciones (mods) a sus motos
- Mantener un registro de las piezas utilizadas

---

## ⭐ Características Principales

✅ **Gestión de Usuarios**
- Crear, leer, actualizar y eliminar usuarios
- Validación de datos de usuario
- Autenticación básica

✅ **Gestión de Motocicletas**
- CRUD completo de motocicletas
- Asociación de motos a usuarios
- Validación de datos técnicos
- Descripción de características

✅ **Gestión de Modificaciones**
- Registro de piezas y modificaciones
- Enlace a tiendas de compra
- Asociación automática a motocicletas
- Marcas de piezas certificadas

✅ **Arquitectura Limpia**
- Patrón MVC con separación de responsabilidades
- Use de DTOs para transferencia de datos
- Validación en capas
- Mapeo de entidades

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| **Java** | 21 | Lenguaje principal |
| **Spring Boot** | 4.0.2 | Framework principal |
| **Spring Data JPA** | - | Acceso a datos |
| **PostgreSQL** | - | Base de datos |
| **Lombok** | - | Reducción de código boilerplate |
| **Jakarta Validation** | - | Validaciones |
| **Maven** | - | Gestor de dependencias |

---

## 📦 Requisitos Previos

Asegúrate de tener instalado:

- **JDK 21** o superior
- **PostgreSQL 12** o superior
- **Maven 3.6.0** o superior (o usar mvnw incluido)
- **Git** (opcional, para clonar el repositorio)

---

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/backend_pruebas.git
cd backend_pruebas
```

### 2. Crear la Base de Datos

Conéctate a PostgreSQL y ejecuta:

```sql
CREATE DATABASE motogarage;
```

### 3. Configurar application.properties

Edita el archivo `src/main/resources/application.properties`:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/motogarage
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Server Configuration
server.port=8081

# Payloads grandes (fotos en base64)
server.tomcat.max-swallow-size=50MB
spring.jackson.parser.max-string-length=50000000
```

**Nota:** `ddl-auto=create-drop` recrea las tablas en cada reinicio (los datos se pierden). Para persistencia entre reinicios, cambia a `update`. Ajusta la URL, puerto y credenciales según tu configuración local.

### 4. Instalar Dependencias

```bash
mvn clean install
```

---

## 📂 Estructura del Proyecto

```
backend_pruebas/
├── src/
│   ├── main/
│   │   ├── java/com/motogarage/backend/
│   │   │   ├── BackendApplication.java          # Clase principal
│   │   │   ├── controller/                       # Controladores REST
│   │   │   │   ├── UserController.java
│   │   │   │   ├── MotorcycleController.java
│   │   │   │   └── ModController.java
│   │   │   ├── service/                          # Lógica de negocio
│   │   │   │   ├── UserService.java
│   │   │   │   ├── MotorcycleService.java
│   │   │   │   └── ModService.java
│   │   │   ├── repository/                       # Acceso a datos
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── MotorcycleRepository.java
│   │   │   │   └── ModRepository.java
│   │   │   ├── model/                            # Entidades JPA
│   │   │   │   ├── User.java
│   │   │   │   ├── Motorcycle.java
│   │   │   │   └── Mod.java
│   │   │   ├── dto/                              # DTOs
│   │   │   │   ├── UserDTO.java
│   │   │   │   ├── MotorcycleDTO.java
│   │   │   │   └── ModDTO.java
│   │   │   ├── mapper/                           # Mapeo de entidades
│   │   │   │   └── EntityMapper.java
│   │   │   └── exception/                        # Manejo de excepciones
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties             # Configuración
│   └── test/
│       └── java/com/motogarage/backend/
│           └── BackendApplicationTests.java
├── pom.xml                                        # Configuración Maven
└── README.md                                      # Este archivo
```

---

## 🔌 Endpoints de la API

### Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/users` | Obtener todos los usuarios |
| `GET` | `/api/users/{id}` | Obtener usuario por ID |
| `POST` | `/api/users` | Registrar nuevo usuario |
| `POST` | `/api/users/login` | Iniciar sesión (email + password) |
| `PUT` | `/api/users/{id}` | Actualizar usuario |
| `DELETE` | `/api/users/{id}` | Eliminar usuario (cascada → motos → mods) |

#### Ejemplo de Solicitud POST:

```bash
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "juan_moto",
    "email": "juan@example.com",
    "password": "segura123"
  }'
```

### Motocicletas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/motorcycles` | Obtener todas las motocicletas |
| `GET` | `/api/motorcycles/{id}` | Obtener motocicleta por ID |
| `POST` | `/api/motorcycles` | Crear nueva motocicleta |
| `PUT` | `/api/motorcycles/{id}` | Actualizar motocicleta |
| `DELETE` | `/api/motorcycles/{id}` | Eliminar motocicleta |

#### Ejemplo de Solicitud POST:

```bash
curl -X POST http://localhost:8081/api/motorcycles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Honda",
    "model": "CB500F",
    "year": 2023,
    "description": "Moto deportiva de media cilindrada"
  }'
```

### Modificaciones (Mods)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/mods` | Obtener todas las modificaciones |
| `GET` | `/api/mods/{id}` | Obtener modificación por ID |
| `POST` | `/api/mods` | Crear nueva modificación |
| `PUT` | `/api/mods/{id}` | Actualizar modificación |
| `DELETE` | `/api/mods/{id}` | Eliminar modificación |

#### Ejemplo de Solicitud POST:

```bash
curl -X POST http://localhost:8081/api/mods \
  -H "Content-Type: application/json" \
  -d '{
    "namePiece": "Escape Akrapovic",
    "brandPiece": "Akrapovic",
    "urlShop": "https://www.akrapovic.com",
    "motorcycle": {
      "id": 1
    }
  }'
```

---

## 📊 Modelos de Datos

### User (Usuario)

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // ID único
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;                           // Nombre de usuario
    
    @NotBlank
    @Email
    private String email;                              // Correo electrónico
    
    @NotBlank
    @Size(min = 6)
    private String password;                           // Contraseña
    
    @Column(columnDefinition = "TEXT")
    private String photoUrl;                           // Foto de perfil (base64)
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Motorcycle> myMotorcycles;            // Motos del usuario
}
```

### Motorcycle (Motocicleta)

```java
@Entity
@Table(name = "motorcycles")
public class Motorcycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // ID único
    
    @NotBlank
    private String brand;                              // Marca
    
    @NotBlank
    private String model;                              // Modelo
    
    @NotNull
    @Min(1900)
    @Max(2026)
    private Integer year;                              // Año de fabricación
    
    @Size(max = 255)
    private String description;                        // Descripción
    
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String photoUrl;                           // Foto de la moto (base64)
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;                                 // Usuario propietario
    
    @OneToMany(mappedBy = "motorcycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mod> mods;                            // Modificaciones
}
```

### Mod (Modificación)

```java
@Entity
@Table(name = "mods")
public class Mod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // ID único
    
    @NotBlank
    private String namePiece;                          // Nombre de la pieza
    
    @NotBlank
    private String brandPiece;                         // Marca de la pieza
    
    @URL
    private String urlShop;                            // URL de la tienda (opcional)
    
    @ManyToOne
    @JoinColumn(name = "motorcycle_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Motorcycle motorcycle;                     // Motocicleta asociada
}
```

---

## 🗄️ Configuración de Base de Datos

### Propiedades de Hibernate

El proyecto utiliza Hibernate para mapeo objeto-relacional con las siguientes configuraciones:

```properties
# Recrear esquema automáticamente al iniciar (datos se pierden)
spring.jpa.hibernate.ddl-auto=create-drop

# Mostrar sentencias SQL ejecutadas
spring.jpa.show-sql=true

# El dialecto de PostgreSQL se detecta automáticamente
```

> **Nota:** Para conservar datos entre reinicios, cambiar `create-drop` por `update`.

### Diagramas de Relaciones

```
User (1) -------- (*) Motorcycle
  |                     |
  |                     |
  |                     | (1) ------- (*) Mod
  |                     |
  └─────────────────────┘
```

- Un usuario puede tener múltiples motocicletas
- Una motocicleta pertenece a un usuario
- Una motocicleta puede tener múltiples modificaciones
- Una modificación pertenece a una motocicleta

---

## ▶️ Cómo Ejecutar

### Opción 1: Desde la Terminal

```bash
# Compilar el proyecto
mvn clean package

# Ejecutar la aplicación
mvn spring-boot:run
```

### Opción 2: Usando Maven Wrapper (Windows)

```bash
mvnw.cmd spring-boot:run
```

### Opción 3: Desde IDE (IntelliJ, Eclipse, VS Code)

1. Abre el proyecto en tu IDE
2. Localiza la clase `BackendApplication.java`
3. Haz clic derecho → Run 'BackendApplication'

### Verificar que está corriendo

```bash
curl http://localhost:8081/api/users
```

Deberías recibir un JSON vacío `[]` o la lista de usuarios existentes.

---

## ✔️ Validaciones

El proyecto implementa validaciones exhaustivas en todos los modelos:

### Validaciones de Usuario

- ✓ Username: Obligatorio, entre 3 y 50 caracteres
- ✓ Email: Obligatorio, formato de email válido
- ✓ Password: Obligatorio, mínimo 6 caracteres

### Validaciones de Motocicleta

- ✓ Brand: Obligatorio, cadena de texto
- ✓ Model: Obligatorio, cadena de texto
- ✓ Year: Obligatorio, entre 1900 y 2026
- ✓ Description: Opcional, máximo 255 caracteres

### Validaciones de Modificación

- ✓ Name Piece: Obligatorio, cadena de texto
- ✓ Brand Piece: Obligatorio, cadena de texto
- ✓ URL Shop: Opcional; si se proporciona, debe ser una URL válida

**Nota:** Las validaciones se aplican mediante Jakarta Validation (Jakarta Bean Validation).

---

## ⚠️ Manejo de Excepciones

El proyecto implementa un manejador global de excepciones (`GlobalExceptionHandler`) que captura y procesa todos los errores de manera centralizada.

### Códigos de Estado HTTP

- `200 OK`: Solicitud exitosa
- `201 CREATED`: Recurso creado exitosamente
- `204 NO CONTENT`: Solicitud exitosa sin contenido (DELETE)
- `400 BAD REQUEST`: Datos inválidos o error de validación
- `404 NOT FOUND`: Recurso no encontrado
- `500 INTERNAL SERVER ERROR`: Error del servidor

### Ejemplo de Respuesta de Error

```json
{
  "timestamp": "2026-02-03T10:30:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": {
    "email": "Invalid email format"
  }
}
```

---

## 🌐 CORS

El proyecto tiene habilitado CORS para permitir solicitudes desde el frontend Angular (puerto 4200):

```java
@CrossOrigin(origins = "http://localhost:4200")
```

**Para cambiar los orígenes permitidos:**

Edita los controladores y modifica el parámetro `origins` en la anotación `@CrossOrigin`.

---

## 🔐 Seguridad

**Nota Importante:** Este es un proyecto de aprendizaje. Para producción, implementa:

- ✓ Autenticación OAuth2 o JWT
- ✓ Encriptación de contraseñas (BCrypt)
- ✓ HTTPS
- ✓ Rate limiting
- ✓ Validaciones de autorización más robustas

---

## 📧 Contacto y Autor

**Proyecto de:** Sergio (Desarrollador)
**Institución:** DAM (Desarrollo de Aplicaciones Multiplataforma)
**Año:** 2026

---

## 📄 Licencia

Este proyecto está disponible bajo licencia MIT. Ver archivo LICENSE para más detalles.

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📚 Recursos Adicionales

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Jakarta Validation](https://jakarta.ee/specifications/bean-validation/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Lombok Documentation](https://projectlombok.org/)

---

**Última actualización:** julio de 2025

---

*¡Gracias por revisar MotoGarage! Si tienes preguntas, abre un issue en el repositorio.*
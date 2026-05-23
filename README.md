# RIU Hotels - Hotel Search Challenge

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-green)
![CI](https://github.com/leogil02/RIU-Backend-Leonardo-Gil/actions/workflows/ci.yml/badge.svg)
[![codecov](https://codecov.io/gh/leogil02/RIU-Backend-Leonardo-Gil/branch/main/graph/badge.svg)](https://codecov.io/gh/leogil02/RIU-Backend-Leonardo-Gil)

API REST para registrar búsquedas de hoteles y consultar la cantidad de coincidencias de búsquedas.

Las búsquedas se publican como eventos a Kafka y se persisten asincrónicamente en Oracle.

---

## Desafío

### Objetivo

Desarrollar una API REST con Spring Boot que exponga dos endpoints:

- `POST /search`: recibe los criterios de búsqueda de un hotel, los publica en Kafka y devuelve un searchId único.
- `GET /count`: se envía por parámetro (llamado "searchId") y se devuelve los criterios de la búsqueda y la cantidad total de búsquedas realizadas con esos mismos criterios.

---

### Requisitos principales

- Arquitectura hexagonal estricta
- Kafka como sistema de mensajería asíncrona
- Persistencia en Oracle
- Tests con JaCoCo al 80% mínimo en branch, líneas, sentencias y métodos
- Dockerización completa
- Virtual Threads para guardado en base de datos
- Swagger para documentación
- Objetos inmutables
- Usar últimas versiones y features de las tecnologías utilizadas
- Validaciones de requests


---


## Tecnologías

| Tecnología | Versión      | Uso |
|---|--------------|---|
| Java | 25           | Lenguaje principal + Virtual Threads |
| Spring Boot | 4.0.6        | Framework principal |
| Apache Kafka | 3.8.0        | Mensajería asincrónica (con KRaft) |
| Oracle Free | 23-slim      | Persistencia |
| Hibernate ORM | 7.2.12.Final | ORM |
| Spring Data JPA | 4.0.5        | Repositorios |
| Springdoc OpenAPI | 3.0.2        | Documentación Swagger |
| JUnit Jupiter | 6.0.3        | Tests unitarios y de integración |
| Mockito | 5.20.0       | Mocks en tests |
| Testcontainers | 2.0.5        | Tests de integración con Oracle y Kafka |
| JaCoCo | -            | Cobertura de código |
| Grafana k6 | 2.0.0-rc1    | Tests de performance |
| Docker / Docker Compose | -            | Dockerización del proyecto |


---

## Arquitectura

El sistema sigue una arquitectura hexagonal estricta.

- Domain no conoce detalles de capas superiores
- Application sólo se conoce a sí mismo y a domain
- Infrastructure conoce detalles de todas las capas

| Capa | Contenido |
|---|---|
| Domain | Records, eventos de dominio, exceptions de dominio |
| Application | Use cases y services |
| Infrastructure | Adaptador REST, Kafka producer y consumer, adaptador de persistencia JPA con Oracle |

---


## Pre - requisitos

- Docker instalado y corriendo.
- Puertos libres:
  - `8080` → App
  - `1521` → Oracle
  - `9092` → Kafka
  - `8090` → Kafka UI

---

## Cómo levantar

**1. Clonar el repositorio:**

```bash
git clone https://github.com/leogil02/RIU-Backend-Leonardo-Gil
cd RIU-Backend-Leonardo-Gil
```

**2. Copiar las variables de entorno:**

```bash
cp .env.example .env
```

El archivo `.env.example` ya tiene valores por defecto que funcionan sin modificación. Se pueden cambiar por los valores que se deseen pero esas variables deben tener valores obligatoriamente.

**3. Levantar todos los servicios:**

```bash
docker-compose up --build
```

Luego de realizar ese comando, esperar a que todos los servicios estén levantados y con estado "healthy".

---

## Servicios


| Servicio | URL | Descripción |
|---|---|---|
| Swagger UI | http://localhost:8080/swagger-ui/index.html | Documentación interactiva de los endpoints |
| API docs (JSON) | http://localhost:8080/v3/api-docs | Especificación OpenAPI en formato JSON |
| Kafka UI | http://localhost:8090 | Interfaz visual para ver el topic "hotel_availability_searches" |

---

## Endpoints

### POST /api/v1/hotels/search

Registra una nueva búsqueda de hotel. Publica un evento a Kafka que se consume asincrónicamente para persistir la búsqueda en Oracle.

---

### GET /api/v1/hotels/count?searchId={searchId}

Se envía por parámetro un `searchId`, devuelve los criterios de esa búsqueda y la cantidad total de búsquedas registradas con los mismos criterios. 

Cabe aclarar que el orden de los ages importa. Por ejemplo, `[30, 29, 1, 3]` y `[1, 3, 29, 30]` se consideran búsquedas distintas.


---

## Tests

### Correr los tests

#### Solo tests unitarios

```bash
mvn test
```

#### Tests unitarios, de integración y reporte de cobertura (requiere Docker)
```bash
mvn verify
```

### Cobertura

| Métrica | Cobertura |
|---|---|
| Instructions | 93% |
| Branches | 97% |
| Lines | 92% |
| Methods | 94% |

El reporte completo se genera en `target/site/jacoco/index.html`.

![Jacoco Coverage](docs/images/jacoco-coverage-report.png)

---

## Performance

Tests de carga ejecutados con Grafana k6 (`k6/hotel-search-load-test.js`).

### Escenario

- 50 usuarios virtuales concurrentes.
- Ramp-up de 0 a 50 usuarios en 10 segundos.
- Carga sostenida de 50 usuarios durante 30 segundos.
- Ramp-down de 50 a 0 usuarios en 10 segundos.
- Cada iteración ejecuta el flujo completo: `POST /search` → polling `GET /count` hasta confirmar persistencia.

### Resultados

| Métrica | Resultado | Threshold |
|---|---|---|
| p(95) response time | 6.62ms | < 500ms ✅ |
| Error rate | 0.00% | < 1% ✅ |
| Throughput | 157 req/s | - |
| Checks exitosos | 100% (11898/11898) | - |
| Iteraciones completas | 3966 | - |

![k6 Results](docs/images/k6-results.png)

### Correr el test de performance

```bash
# Requiere que el docker-compose esté corriendo (app, Oracle y Kafka)
k6 run k6/hotel-search-load-test.js
```

---

## Decisiones de diseño

### Separación `HotelSearchedEvent` y `PersistedHotelSearch`

`HotelSearchedEvent` es el evento de dominio que viaja por Kafka, mientras que `PersistedHotelSearch` es la entidad que se persiste en Oracle.

Ambas entidades tienen los mismos datos, pero son conceptos distintos. En un futuro podrían evolucionar de forma independiente y diferir en estos datos, por lo que lo mejor sería separar estas entidades.

Esta separación desacopla el modelo de mensajería con el de persistencia.


### `AgeConverter` con serialización por coma

JPA mapea `List<Integer>` con `@ElementCollection`, lo que genera una tabla secundaria y complica la comparación por orden en el `GET /count`. Habría que reconstruir el array en SQL y compararlo posición por posición.

La solución fue serializar las edades, ya que comparar un String con las edades separadas por comas es mucho más simple porque el orden queda preservado de forma más natural.

### Query nativa en `countMatching`

Spring Data JPQL no aplica `AttributeConverter` en las cláusulas `WHERE`. Al usar `countByHotelIdAndCheckInAndCheckOutAndAges(...)` con JPQL, Hibernate no serializa la lista de ages con el converter antes de comparar. La solución es usar `nativeQuery = true` para que la query llegue directamente a Oracle con el string ya serializado.

### Tests de integración con Testcontainers

Los tests IT usan Testcontainers para levantar Oracle y Kafka reales en Docker. La limpieza entre tests se hace con `TRUNCATE TABLE` via `JdbcTemplate` (más rápido y determinístico que `deleteAll()`). Awaitility maneja la asincronía de Kafka (evitando de esta forma `Thread.sleep`).

### Dockerfile multi-stage (3 etapas)

| Stage | Funcionalidad | Descripción |
|---|---|---|
| 1 | Dependencias | Descarga y cachea las dependencias de Maven |
| 2 | Builder | Compila el código fuente |
| 3 | Runtime | Imagen final sólo con JRE |

De esta forma, se aprovecha el caché de Docker. Si sólo se cambia código fuente (y no se modifica el pom.xml), Docker reusa la capa de dependencias y el build es mucha más rápido.

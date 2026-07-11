# 🏙️ StreetDom (Spring Boot + Kotlin Frontend)

# 🗂️ Roadmap de Ingeniería de Software para un Proyecto de Portafolio Senior

> **Objetivo:** Construir StreetDom como una aplicación multijugador masiva basada en geolocalización donde los jugadores conquistan territorios reales de su ciudad. El proyecto evolucionará como un producto real: empezando por una base sólida y creciendo progresivamente según aparezcan nuevas necesidades.

---

# 🧱 FASE 0 — Base Sólida & Entorno de Desarrollo

**⏱️ Duración estimada:** 4–6 días

## 🎯 Objetivo

Construir una arquitectura limpia, autenticación segura y un entorno reproducible desde el primer día.

## ❓ Problema

Antes de añadir funcionalidades complejas necesitamos una base mantenible y una forma sencilla de levantar el proyecto completo.

## 💡 Decisiones de arquitectura

### Backend

- Spring Boot
- Arquitectura por capas:
  - Controller
  - Service
  - Repository
  - DTO
  - Entity
- Spring Security
- JWT:
  - Access Token
  - Refresh Token

### Base de datos

PostgreSQL

Tabla `users`

| Campo | Tipo |
|---------|------|
| id | UUID |
| email | Unique |
| password_hash | BCrypt |
| coins_balance | Decimal |
| role | USER / ADMIN |
| created_at | Timestamp |

### Frontend Kotlin

Arquitectura:

- Compose
- MVVM
- StateFlow
- Corrutinas
- Ktor Client
- Navegación
- Gestión centralizada de estado

Funcionalidades:

- Login
- Registro
- Persistencia de sesión
- Refresh automático de tokens

### Docker

Al finalizar esta fase:

```bash
docker compose up
```

deberá levantar:

```text
Frontend
Backend
PostgreSQL
```

## API inicial

```http
POST /auth/register
POST /auth/login
POST /auth/refresh
```

---

# 🗺️ FASE 1 — Dominio Geoespacial & Modelo del Juego

**⏱️ Duración estimada:** 1 semana

## 🎯 Objetivo

Transformar el mundo real en un tablero conquistable.

## ❓ Problema

Trabajar directamente con coordenadas GPS y cálculos geométricos complejos dificulta la escalabilidad.

## 💡 Decisión

Utilizar Uber H3 para convertir posiciones GPS en celdas hexagonales.

## ⚖️ Trade-offs

### ¿Por qué H3?

Ventajas:

- Muy rápido
- Vecinos calculados fácilmente
- Escalable
- Utilizado en sistemas reales

Alternativas:

- PostGIS
- Geohash
- Polígonos manuales

## Dominio inicial

Entidades:

```text
User
Zone
Shield
Conquest
Faction (futuro)
```

## Funcionalidades

- GPS → H3
- Obtener celda actual
- Renderizar mapa
- Mostrar propietario

## API

```http
POST /api/zones/locate
GET /api/zones/grid
```

---

# 🔥 FASE 2 — Sistema de Conquista Concurrente

**⏱️ Duración estimada:** 1–2 semanas

## 🎯 Objetivo

Garantizar consistencia cuando varios jugadores intenten conquistar una misma zona.

## ❓ Problema

Las condiciones de carrera pueden provocar estados inconsistentes.

## 💡 Decisión

Redis + Redisson para implementar bloqueos distribuidos.

## ⚖️ Trade-offs

### ¿Por qué Redis?

Los bloqueos tradicionales de Java:

```java
synchronized
```

solo funcionan dentro de una única instancia.

Redis protege el sistema incluso cuando existan múltiples servidores.

## Flujo

```text
Jugador pulsa "Conquistar"

↓

Backend recibe coordenadas

↓

GPS → H3

↓

Lock Redis

↓

Validar:

- monedas
- escudos
- reglas

↓

Actualizar zona

↓

Guardar cambios

↓

Liberar lock
```

## Docker

Añadimos:

```text
Redis
```

Nuevo entorno:

```text
Frontend
Backend
PostgreSQL
Redis
```

---

# ⚡ FASE 3 — Tiempo Real

**⏱️ Duración estimada:** 1 semana

## 🎯 Objetivo

Actualizar el mapa de jugadores cercanos instantáneamente.

## ❓ Problema

No queremos hacer polling continuo.

## 💡 Decisión

Utilizar WebSockets.

## Flujo

```text
Conquista realizada

↓

Evento generado

↓

Usuarios cercanos detectados

↓

Push WebSocket

↓

Mapa actualizado
```

## Frontend Kotlin

Implementación:

- Ktor WebSockets
- Corrutinas
- StateFlow

Funcionalidades:

- Actualización en tiempo real
- Presencia de usuarios
- Estado reactivo

---

# 📢 FASE 4 — Arquitectura Dirigida por Eventos

**⏱️ Duración estimada:** 1 semana

## 🎯 Objetivo

Desacoplar tareas secundarias.

## ❓ Problema

Una conquista puede generar:

- estadísticas
- auditoría
- notificaciones
- histórico

No queremos bloquear respuestas HTTP.

## 💡 Decisión

Kafka actuará como Event Bus.

## Eventos iniciales

```text
ZoneConqueredEvent
ShieldTriggeredEvent
```

## Docker

Añadimos:

```text
Kafka
```

Nuevo entorno:

```text
Frontend
Backend
PostgreSQL
Redis
Kafka
```

---

# 🎮 FASE 5 — Gameplay & Mecánicas

**⏱️ Duración estimada:** 1 semana

## 🎯 Objetivo

Añadir profundidad estratégica al juego.

## Funcionalidades

- Escudos
- Historial de conquistas
- Rankings
- Recompensas
- Estadísticas

Opcional:

- Facciones
- Eventos temporales
- Zonas especiales

---

# 🧪 FASE 6 — Testing Profesional

**⏱️ Duración estimada:** 1 semana

## 🎯 Objetivo

Validar estabilidad bajo condiciones reales.

## Implementación

- Unit Tests
- Integration Tests
- Testcontainers
- Gatling o Locust

Escenarios:

- 1000 jugadores simultáneos
- estrés de Redis
- múltiples conquistas simultáneas

---

# 💳 FASE 7 — Economía & Monetización

**⏱️ Duración estimada:** 1 semana

## 🎯 Objetivo

Implementar sistema económico.

## Funcionalidades

Monedas obtenidas:

- conquistas
- recompensas
- eventos

Monedas compradas:

- Stripe
- paquetes de monedas

---

# 🚀 FASE 8 — Optimización & Caché

**⏱️ Duración estimada:** 1 semana

## 🎯 Objetivo

Reducir carga sobre PostgreSQL.

## Redis almacenará

- estado del mapa
- zonas populares
- información frecuente

Patrones:

- Cache Aside
- Write Through

---

# 🛡️ FASE 9 — Seguridad & Anti-Cheat

**⏱️ Duración estimada:** 3–5 días

## 🎯 Objetivo

Evitar trampas y usos indebidos.

## Funcionalidades

- Validación de proximidad
- Detección GPS falso
- Control de velocidad
- Autorización contextual

---

# 📊 FASE 10 — Observabilidad

**⏱️ Duración estimada:** 3–5 días

## 🎯 Objetivo

Monitorizar y depurar el sistema.

## Tecnologías

- Spring Boot Actuator
- Prometheus
- Grafana
- Trace ID
- Correlation ID

---

# 🏗️ FASE 11 — Evolución a Microservicios

**⏱️ Duración estimada:** 1–2 semanas

## 🎯 Objetivo

Comprender cómo escalar el sistema.

## Servicios

- Auth Service
- User Service
- Conquest Service
- Live Service

---

# ☁️ FASE 12 — Cloud & AWS

**⏱️ Duración estimada:** 1–2 semanas

## 🎯 Objetivo

Desplegar StreetDom en un entorno similar a producción.

## AWS

- EC2
- RDS PostgreSQL
- S3
- IAM
- CloudWatch

## CI/CD

GitHub Actions:

- tests automáticos
- builds automáticos
- despliegue automático

---

# 🎯 Filosofía del proyecto

> StreetDom evolucionará exactamente igual que un producto real. Cada tecnología aparecerá únicamente cuando resuelva un problema concreto. El objetivo no es aprender herramientas aisladas, sino comprender cuándo utilizarlas, qué ventajas aportan y qué compromisos implican dentro de una arquitectura profesional.
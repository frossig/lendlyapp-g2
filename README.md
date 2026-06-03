# LendlyApp — TP3 Parcial Domiciliario


## Integrantes

- Fausto Rossi 
- Nicolas Cuchero 

## Descripción

App Android nativa para gestión de préstamos y servicios fintech, construida en Kotlin con Jetpack Compose siguiendo el spec y el diseño de Figma provistos. Integra la API mock indicada por la cátedra y cubre todos los flujos requeridos: autenticación, navegación principal con Bottom Navigation Bar, préstamos, shop, historial y manage.

## Stack técnico

- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Arquitectura:** MVVM con `ViewModΩel` + `StateFlow`
- **Inyección de dependencias:** Hilt (sobre Dagger)
- **Networking:** Retrofit + OkHttp + Gson
- **Imágenes:** Coil
- **Navegación:** Navigation Compose
- **Persistencia:** Room (entidades listas) + DataStore (token de sesión)
- **Coroutines** para todo el trabajo asíncrono

## Funcionalidades implementadas

| Sección | Descripción |
|---|---|
| **Splash** | Decide entre Onboarding / Login / Main según haya token o usuario guardado |
| **Onboarding** | 3 steps con HorizontalPager + indicadores animados |
| **Login** | Card con usuario pre-cargado (welcome back) + edición + integración con `POST /auth/login` |
| **Register** | Flujo de 9 steps (phone, SMS, face, ID, verified, profile form, signature, password, done) + `POST /auth/create` |
| **Home** | Balance + Unpaid Loans + Recommended For You con data real (`/users/{id}`, `/loans`, `/products`) |
| **Cash-In** | Flujo de 5 pantallas (Options → Online/OTC → Amount → Success) |
| **Notifications** | Lista de notificaciones + modal de calendario para filtrar |
| **Loans** | Info → Form → Success → Active. Aplica con `POST /loans/apply` y lista con `GET /loans` |
| **Shop** | Search + Filter modal + Promo carousel + Categories + Brands + Recommended + Best Sellers, con `GET /products` |
| **Product detail** | Pantalla larga scrolleable con acordeones de Features y Specs |
| **History** | Lista de transacciones (`GET /transactions`) + Recent Loans, con filtros y detalle de cada transaction |
| **Manage** | Cuenta + sección General + Edit Account Details con form prefilled desde `GET /users/{id}` |

## Integración con la API

Base URL del mock provisto por la cátedra:
```
https://6d710e79-f4ca-4651-909f-7dd13bd29968.mock.pstmn.io
```

Todas las llamadas incluyen el header `x-api-key: 123456789` mediante un `AuthInterceptor` de OkHttp. Una vez autenticado el usuario, el interceptor también agrega `Authorization: Bearer <token>`.

Endpoints consumidos:
- `POST /auth/login`
- `POST /auth/create`
- `GET /users/{id}`
- `GET /loans`
- `POST /loans/apply`
- `GET /transactions`
- `GET /products`

## Cómo correr

1. Abrir el proyecto en Android Studio (Iguana o superior).
2. **File → Sync Project with Gradle Files**.
3. Run en emulador o dispositivo con API ≥ 26.
4. Para hacer login podés tipear cualquier teléfono y cualquier password de 4+ caracteres — el mock responde 200 a cualquier credencial y devuelve un usuario `John Doe`.

## Uso de IA generativa

Durante el desarrollo se utilizó **Claude Code** (Anthropic) como asistente de programación para:

- Scaffold inicial del proyecto (estructura de paquetes, Hilt, Retrofit, Room, DataStore).
- Generación de boilerplate (DTOs, módulos de DI, repositories).
- Maquetación inicial como estructural de pantallas tomando como input las capturas del Figma y los tokens del design system.
- Refactorización de componentes (extracción de `PersonalDetailsForm`, `OptionRow`, `MainTabHeader`, etc.).
- Documentación e ideas de arquitectura.

Las decisiones de UX, la integración con el design system del Figma, la revisión de cada cambio y el ajuste fino de cada pantalla fueron trabajo del equipo. El código generado por la IA fue revisado, modificado y aprobado por los integrantes antes de cada commit.